package br.pucpr.mealplanner.ui.recipes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pucpr.mealplanner.data.local.Recipe
import br.pucpr.mealplanner.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RecipeFormState(
    val name: String = "",
    val ingredients: String = "",
    val instructions: String = "",
    val prepTime: String = "",
    val nameError: Boolean = false,
    // NOVO: erros dos demais campos e controle de "salvando" (evita duplo clique)
    val ingredientsError: Boolean = false,
    val instructionsError: Boolean = false,
    val prepTimeError: Boolean = false,
    val isSaving: Boolean = false
)

@HiltViewModel
class RecipeFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: RecipeRepository
) : ViewModel() {

    private val recipeId: Long = savedStateHandle.get<Long>("recipeId") ?: -1L
    val isEditing: Boolean = recipeId != -1L

    private val _state = MutableStateFlow(RecipeFormState())
    val state: StateFlow<RecipeFormState> = _state.asStateFlow()

    init {
        if (isEditing) viewModelScope.launch {
            repository.getById(recipeId)?.let { r ->
                _state.value = RecipeFormState(r.name, r.ingredients, r.instructions, r.prepTimeMinutes.toString())
            }
        }
    }

    fun onNameChange(v: String) = _state.update { it.copy(name = v, nameError = false) }
    fun onIngredientsChange(v: String) = _state.update { it.copy(ingredients = v, ingredientsError = false) }
    fun onInstructionsChange(v: String) = _state.update { it.copy(instructions = v, instructionsError = false) }
    fun onPrepTimeChange(v: String) =
        _state.update { it.copy(prepTime = v.filter(Char::isDigit).take(4), prepTimeError = false) }

    fun save(onSaved: () -> Unit) {
        val s = _state.value
        if (s.isSaving) return

        // NOVO: valida todos os campos (antes só validava o nome)
        val minutes = s.prepTime.toIntOrNull()
        val nameError = s.name.isBlank()
        val ingredientsError = s.ingredients.isBlank()
        val instructionsError = s.instructions.isBlank()
        val prepTimeError = minutes == null || minutes <= 0

        if (nameError || ingredientsError || instructionsError || prepTimeError) {
            _state.update {
                it.copy(
                    nameError = nameError,
                    ingredientsError = ingredientsError,
                    instructionsError = instructionsError,
                    prepTimeError = prepTimeError
                )
            }
            return
        }

        _state.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            repository.save(
                Recipe(
                    id = if (isEditing) recipeId else 0,
                    name = s.name.trim(),
                    // NOVO: remove linhas vazias (não gera itens em branco nas compras)
                    ingredients = s.ingredients.lines()
                        .map { it.trim() }
                        .filter { it.isNotEmpty() }
                        .joinToString("\n"),
                    instructions = s.instructions.trim(),
                    prepTimeMinutes = minutes
                )
            )
            onSaved()
        }
    }
}
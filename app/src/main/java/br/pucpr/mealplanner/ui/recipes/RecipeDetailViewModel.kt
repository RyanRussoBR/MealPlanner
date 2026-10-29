package br.pucpr.mealplanner.ui.recipes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pucpr.mealplanner.data.local.MealType
import br.pucpr.mealplanner.data.local.Recipe
import br.pucpr.mealplanner.data.repository.MealPlanRepository
import br.pucpr.mealplanner.data.repository.RecipeRepository
import br.pucpr.mealplanner.data.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository,
    private val mealPlanRepository: MealPlanRepository,
    private val shoppingRepository: ShoppingRepository
) : ViewModel() {

    private val recipeId: Long = checkNotNull(savedStateHandle.get<Long>("recipeId"))

    val recipe: StateFlow<Recipe?> = recipeRepository.observeById(recipeId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun delete(onDone: () -> Unit) {
        val r = recipe.value ?: return
        viewModelScope.launch { recipeRepository.delete(r); onDone() }
    }

    /** dayOfWeek: 1 = Segunda ... 7 = Domingo */
    fun schedule(dayOfWeek: Int, type: MealType) {
        viewModelScope.launch { mealPlanRepository.schedule(recipeId, dayOfWeek, type) }
    }

    fun addIngredientsToShoppingList() {
        val r = recipe.value ?: return
        viewModelScope.launch { shoppingRepository.addAll(r.ingredients.lines()) }
    }
}

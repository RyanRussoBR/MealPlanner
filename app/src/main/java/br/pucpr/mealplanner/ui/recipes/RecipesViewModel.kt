package br.pucpr.mealplanner.ui.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pucpr.mealplanner.data.local.Recipe
import br.pucpr.mealplanner.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    val recipes: StateFlow<List<Recipe>> = repository.recipes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun delete(recipe: Recipe) {
        viewModelScope.launch { repository.delete(recipe) }
    }
}

package br.pucpr.mealplanner.ui.planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pucpr.mealplanner.data.local.PlannedMealWithRecipe
import br.pucpr.mealplanner.data.repository.MealPlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlannerViewModel @Inject constructor(
    private val repository: MealPlanRepository
) : ViewModel() {

    val meals: StateFlow<List<PlannedMealWithRecipe>> = repository.plannedMeals
        .map { list -> list.sortedWith(compareBy({ it.meal.dayOfWeek }, { it.meal.mealType.ordinal })) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun remove(item: PlannedMealWithRecipe) {
        viewModelScope.launch { repository.remove(item.meal) }
    }
}

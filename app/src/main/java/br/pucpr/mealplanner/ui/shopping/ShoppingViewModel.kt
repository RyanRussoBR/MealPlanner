package br.pucpr.mealplanner.ui.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pucpr.mealplanner.data.local.ShoppingItem
import br.pucpr.mealplanner.data.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: ShoppingRepository
) : ViewModel() {

    val items: StateFlow<List<ShoppingItem>> = repository.items
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun add(name: String) { if (name.isNotBlank()) viewModelScope.launch { repository.add(name) } }
    fun toggle(item: ShoppingItem) { viewModelScope.launch { repository.toggle(item) } }
    fun delete(item: ShoppingItem) { viewModelScope.launch { repository.delete(item) } }
    fun clearChecked() { viewModelScope.launch { repository.clearChecked() } }
}

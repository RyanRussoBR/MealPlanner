package br.pucpr.mealplanner.data.repository

import br.pucpr.mealplanner.data.local.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(private val dao: RecipeDao) {
    val recipes: Flow<List<Recipe>> = dao.observeAll()
    fun observeById(id: Long): Flow<Recipe?> = dao.observeById(id)
    suspend fun getById(id: Long): Recipe? = dao.getById(id)
    suspend fun save(recipe: Recipe) { dao.upsert(recipe) }      // Create + Update
    suspend fun delete(recipe: Recipe) = dao.delete(recipe)
}

@Singleton
class MealPlanRepository @Inject constructor(private val dao: PlannedMealDao) {
    val plannedMeals: Flow<List<PlannedMealWithRecipe>> = dao.observeAll()
    suspend fun schedule(recipeId: Long, dayOfWeek: Int, type: MealType) =
        dao.insert(PlannedMeal(recipeId = recipeId, dayOfWeek = dayOfWeek, mealType = type))
    suspend fun remove(meal: PlannedMeal) = dao.delete(meal)
}

@Singleton
class ShoppingRepository @Inject constructor(private val dao: ShoppingDao) {
    val items: Flow<List<ShoppingItem>> = dao.observeAll()
    suspend fun add(name: String) = dao.upsert(ShoppingItem(name = name.trim()))
    suspend fun addAll(names: List<String>) =
        dao.insertAll(names.filter { it.isNotBlank() }.map { ShoppingItem(name = it.trim()) })
    suspend fun toggle(item: ShoppingItem) = dao.upsert(item.copy(checked = !item.checked))
    suspend fun delete(item: ShoppingItem) = dao.delete(item)
    suspend fun clearChecked() = dao.clearChecked()
}

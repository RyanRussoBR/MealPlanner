package br.pucpr.mealplanner.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY name")
    fun observeAll(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun observeById(id: Long): Flow<Recipe?>

    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getById(id: Long): Recipe?

    @Upsert suspend fun upsert(recipe: Recipe): Long
    @Delete suspend fun delete(recipe: Recipe)
}

@Dao
interface PlannedMealDao {
    @Transaction
    @Query("SELECT * FROM planned_meals ORDER BY dayOfWeek")
    fun observeAll(): Flow<List<PlannedMealWithRecipe>>

    @Insert suspend fun insert(meal: PlannedMeal)
    @Delete suspend fun delete(meal: PlannedMeal)
}

@Dao
interface ShoppingDao {
    @Query("SELECT * FROM shopping_items ORDER BY checked, name")
    fun observeAll(): Flow<List<ShoppingItem>>

    @Upsert suspend fun upsert(item: ShoppingItem)
    @Insert suspend fun insertAll(items: List<ShoppingItem>)
    @Delete suspend fun delete(item: ShoppingItem)

    @Query("DELETE FROM shopping_items WHERE checked = 1")
    suspend fun clearChecked()
}

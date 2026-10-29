package br.pucpr.mealplanner.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Recipe::class, PlannedMeal::class, ShoppingItem::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun plannedMealDao(): PlannedMealDao
    abstract fun shoppingDao(): ShoppingDao
}

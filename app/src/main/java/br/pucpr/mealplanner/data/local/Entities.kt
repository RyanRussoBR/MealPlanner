package br.pucpr.mealplanner.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

enum class MealType(val label: String) {
    CAFE("Café da manhã"), ALMOCO("Almoço"), LANCHE("Lanche"), JANTAR("Jantar")
}

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val ingredients: String,      // um ingrediente por linha
    val instructions: String,
    val prepTimeMinutes: Int
)

@Entity(
    tableName = "planned_meals",
    foreignKeys = [ForeignKey(
        entity = Recipe::class,
        parentColumns = ["id"],
        childColumns = ["recipeId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("recipeId")]
)
data class PlannedMeal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val recipeId: Long,
    val dayOfWeek: Int,           // 1 = Segunda ... 7 = Domingo
    val mealType: MealType
)

@Entity(tableName = "shopping_items")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val checked: Boolean = false
)

data class PlannedMealWithRecipe(
    @Embedded val meal: PlannedMeal,
    @Relation(parentColumn = "recipeId", entityColumn = "id") val recipe: Recipe
)

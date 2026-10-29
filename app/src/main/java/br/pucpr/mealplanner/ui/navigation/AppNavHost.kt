package br.pucpr.mealplanner.ui.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.pucpr.mealplanner.ui.planner.PlannerScreen
import br.pucpr.mealplanner.ui.recipes.RecipeDetailScreen
import br.pucpr.mealplanner.ui.recipes.RecipeFormScreen
import br.pucpr.mealplanner.ui.recipes.RecipesScreen
import br.pucpr.mealplanner.ui.shopping.ShoppingScreen

object Routes {
    const val PLANNER = "planner"
    const val RECIPES = "recipes"
    const val SHOPPING = "shopping"
    const val RECIPE_FORM = "recipe_form?recipeId={recipeId}"
    const val RECIPE_DETAIL = "recipe_detail/{recipeId}"

    fun recipeForm(id: Long? = null) = "recipe_form?recipeId=${id ?: -1L}"
    fun recipeDetail(id: Long) = "recipe_detail/$id"
}

private data class BottomItem(val route: String, val label: String, val icon: ImageVector)

private val bottomItems = listOf(
    BottomItem(Routes.PLANNER, "Planejador", Icons.Default.Home),
    BottomItem(Routes.RECIPES, "Receitas", Icons.AutoMirrored.Filled.List),
    BottomItem(Routes.SHOPPING, "Compras", Icons.Default.ShoppingCart),
)

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = bottomItems.any { it.route == currentRoute }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        }
    ) { inner ->
        NavHost(
            navController = navController,
            startDestination = Routes.PLANNER,
            modifier = Modifier.padding(bottom = inner.calculateBottomPadding())
        ) {
            composable(Routes.PLANNER) {
                PlannerScreen(onRecipeClick = { navController.navigate(Routes.recipeDetail(it)) })
            }
            composable(Routes.RECIPES) {
                RecipesScreen(
                    onAddRecipe = { navController.navigate(Routes.recipeForm()) },
                    onRecipeClick = { navController.navigate(Routes.recipeDetail(it)) }
                )
            }
            composable(Routes.SHOPPING) { ShoppingScreen() }
            composable(
                route = Routes.RECIPE_FORM,
                arguments = listOf(navArgument("recipeId") { type = NavType.LongType; defaultValue = -1L })
            ) {
                RecipeFormScreen(onBack = { navController.popBackStack() })
            }
            composable(
                route = Routes.RECIPE_DETAIL,
                arguments = listOf(navArgument("recipeId") { type = NavType.LongType })
            ) { entry ->
                val id = entry.arguments!!.getLong("recipeId")
                RecipeDetailScreen(
                    onBack = { navController.popBackStack() },
                    onEdit = { navController.navigate(Routes.recipeForm(id)) }
                )
            }
        }
    }
}

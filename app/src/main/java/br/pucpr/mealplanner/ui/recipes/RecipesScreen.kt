package br.pucpr.mealplanner.ui.recipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * TELA 2 - Lista de receitas (Integrante 2)
 */
@Composable
fun RecipesScreen(
    onAddRecipe: () -> Unit,
    onRecipeClick: (Long) -> Unit,
    viewModel: RecipesViewModel = hiltViewModel()
) {
    val recipes by viewModel.recipes.collectAsStateWithLifecycle()

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Lista de receitas")

        Button(onClick = onAddRecipe) {
            Text("Adicionar receita")
        }

        LazyColumn {
            items(recipes) { recipe ->
                Text(
                    text = recipe.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onRecipeClick(recipe.id) }
                        .padding(12.dp)
                )
            }
        }
    }
}
package br.pucpr.mealplanner.ui.recipes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * TELA 4 - Detalhes da receita (Integrante 4)
 */
@Composable
fun RecipeDetailScreen(
    onBack: () -> Unit,
    onEdit: () -> Unit,
    viewModel: RecipeDetailViewModel = hiltViewModel()
) {
    val recipe by viewModel.recipe.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text("Detalhes da receita")

        recipe?.let { r ->

            Text("Nome: ${r.name}")

            Text("Ingredientes: ${r.ingredients}")

            Text("Modo de preparo: ${r.instructions}")

            Text("Tempo: ${r.prepTimeMinutes} minutos")

            Button(onClick = onEdit) {
                Text("Editar")
            }

            Button(
                onClick = viewModel::addIngredientsToShoppingList
            ) {
                Text("Adicionar à lista de compras")
            }
        }

        Button(onClick = onBack) {
            Text("Voltar")
        }
    }
}
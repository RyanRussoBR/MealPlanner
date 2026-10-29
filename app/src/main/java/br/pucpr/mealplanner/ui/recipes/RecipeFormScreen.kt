package br.pucpr.mealplanner.ui.recipes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * TELA 3 - Cadastro e edição de receitas (Integrante 3)
 */
@Composable
fun RecipeFormScreen(
    onBack: () -> Unit,
    viewModel: RecipeFormViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text("Cadastro de receita")

        OutlinedTextField(
            value = state.name,
            onValueChange = viewModel::onNameChange,
            label = { Text("Nome") }
        )

        OutlinedTextField(
            value = state.ingredients,
            onValueChange = viewModel::onIngredientsChange,
            label = { Text("Ingredientes") }
        )

        OutlinedTextField(
            value = state.instructions,
            onValueChange = viewModel::onInstructionsChange,
            label = { Text("Modo de preparo") }
        )

        OutlinedTextField(
            value = state.prepTime,
            onValueChange = viewModel::onPrepTimeChange,
            label = { Text("Tempo de preparo") }
        )

        Button(
            onClick = { viewModel.save(onSaved = onBack) }
        ) {
            Text("Salvar")
        }

        Button(onClick = onBack) {
            Text("Voltar")
        }
    }
}
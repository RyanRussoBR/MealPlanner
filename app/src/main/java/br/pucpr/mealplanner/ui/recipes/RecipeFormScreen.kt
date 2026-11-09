package br.pucpr.mealplanner.ui.recipes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
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

        // NOVO: título muda conforme o modo (criar x editar)
        Text(
            text = if (viewModel.isEditing) "Editar receita" else "Cadastro de receita",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = state.name,
            onValueChange = viewModel::onNameChange,
            label = { Text("Nome") },
            singleLine = true,
            isError = state.nameError,
            supportingText = if (state.nameError) {
                { Text("Informe o nome da receita") }
            } else null,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.ingredients,
            onValueChange = viewModel::onIngredientsChange,
            label = { Text("Ingredientes") },
            minLines = 4,
            isError = state.ingredientsError,
            supportingText = {
                Text(if (state.ingredientsError) "Informe ao menos um ingrediente" else "Um ingrediente por linha")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.instructions,
            onValueChange = viewModel::onInstructionsChange,
            label = { Text("Modo de preparo") },
            minLines = 5,
            isError = state.instructionsError,
            supportingText = if (state.instructionsError) {
                { Text("Informe o modo de preparo") }
            } else null,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.prepTime,
            onValueChange = viewModel::onPrepTimeChange,
            label = { Text("Tempo de preparo (min)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = state.prepTimeError,
            supportingText = if (state.prepTimeError) {
                { Text("Informe o tempo em minutos") }
            } else null,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.save(onSaved = onBack) },
            enabled = !state.isSaving,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (viewModel.isEditing) "Salvar alterações" else "Salvar")
        }

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Voltar")
        }
    }
}
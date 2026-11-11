package br.pucpr.mealplanner.ui.shopping

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * TELA 5 - Lista de compras (Integrante 5)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingScreen(
    viewModel: ShoppingViewModel = hiltViewModel()
) {
    val items by viewModel.items.collectAsStateWithLifecycle()

    var newItem by rememberSaveable { mutableStateOf("") }
    val checkedCount = items.count { it.checked }
    val addItem = {
        if (newItem.isNotBlank()) {
            viewModel.add(newItem.trim())
            newItem = ""
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Lista de compras") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .imePadding()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = newItem,
                onValueChange = { newItem = it },
                label = { Text("Novo item") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { addItem() }),
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = addItem,
                enabled = newItem.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Adicionar")
            }
            OutlinedButton(
                onClick = viewModel::clearChecked,
                enabled = checkedCount > 0,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Limpar comprados ($checkedCount)")
            }

            if (items.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Sua lista de compras está vazia.\nAdicione um item ou envie os ingredientes de uma receita.",
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Text(
                    text = "$checkedCount de ${items.size} itens comprados",
                    style = MaterialTheme.typography.bodyMedium
                )
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items, key = { it.id }) { item ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            ListItem(
                                headlineContent = {
                                    Text(
                                        text = item.name,
                                        textDecoration = if (item.checked) TextDecoration.LineThrough else TextDecoration.None,
                                        color = if (item.checked) MaterialTheme.colorScheme.onSurfaceVariant
                                            else MaterialTheme.colorScheme.onSurface
                                    )
                                },
                                leadingContent = {
                                    Checkbox(
                                        checked = item.checked,
                                        onCheckedChange = { viewModel.toggle(item) },
                                        modifier = Modifier.semantics {
                                            contentDescription = "Comprado: ${item.name}"
                                        }
                                    )
                                },
                                trailingContent = {
                                    IconButton(onClick = { viewModel.delete(item) }) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Excluir ${item.name}"
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

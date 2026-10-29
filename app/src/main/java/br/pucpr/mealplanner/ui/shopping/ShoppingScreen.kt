package br.pucpr.mealplanner.ui.shopping

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * TELA 5 - Lista de compras (Integrante 5)
 */
@Composable
fun ShoppingScreen(
    viewModel: ShoppingViewModel = hiltViewModel()
) {
    val items by viewModel.items.collectAsStateWithLifecycle()

    var newItem by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text("Lista de compras")

        OutlinedTextField(
            value = newItem,
            onValueChange = { newItem = it },
            label = { Text("Novo item") }
        )

        Button(
            onClick = {
                viewModel.add(newItem)
                newItem = ""
            }
        ) {
            Text("Adicionar")
        }

        LazyColumn {
            items(items) { item ->

                Row {
                    Checkbox(
                        checked = item.checked,
                        onCheckedChange = {
                            viewModel.toggle(item)
                        }
                    )

                    Text(
                        text = item.name,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}
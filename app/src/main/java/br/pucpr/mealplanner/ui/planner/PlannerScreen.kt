package br.pucpr.mealplanner.ui.planner

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.pucpr.mealplanner.ui.common.DAY_NAMES

/**
 * TELA 1 - Planejador semanal (Integrante 1)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlannerScreen(
    onRecipeClick: (Long) -> Unit,
    viewModel: PlannerViewModel = hiltViewModel()
) {
    val meals by viewModel.meals.collectAsStateWithLifecycle()

    Scaffold(topBar = { TopAppBar(title = { Text("Planejador semanal") }) }) { padding ->
        if (meals.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text(
                    "Nenhuma refeição planejada.\nAbra uma receita e toque em \"Agendar\".",
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                meals.groupBy { it.meal.dayOfWeek }.forEach { (day, list) ->
                    item(key = "day$day") {
                        Text(DAY_NAMES[day - 1], style = MaterialTheme.typography.titleMedium)
                    }
                    items(list, key = { it.meal.id }) { item ->
                        Card(Modifier.fillMaxWidth().clickable { onRecipeClick(item.recipe.id) }) {
                            ListItem(
                                headlineContent = { Text(item.recipe.name) },
                                supportingContent = { Text(item.meal.mealType.label) },
                                trailingContent = {
                                    IconButton(onClick = { viewModel.remove(item) }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Remover")
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

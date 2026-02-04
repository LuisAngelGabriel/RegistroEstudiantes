package edu.ucne.registroestudiantes.presentation.tipopenalidad.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registroestudiantes.domain.tipopenalidad.model.TipoPenalidad

@Composable
fun TipoPenalidadListScreen(
    onDrawer: () -> Unit,
    goToTipoPenalidad: (Int) -> Unit,
    createTipoPenalidad: () -> Unit,
    viewModel: ListTipoPenalidadViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    TipoPenalidadListBody(
        state = state,
        onDrawer = onDrawer,
        onEvent = { event ->
            when (event) {
                is ListTipoPenalidadUiEvent.Edit -> goToTipoPenalidad(event.id)
                is ListTipoPenalidadUiEvent.CreateNew -> createTipoPenalidad()
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TipoPenalidadListBody(
    state: ListTipoPenalidadUiState,
    onDrawer: () -> Unit,
    onEvent: (ListTipoPenalidadUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Listado de Penalidades") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(ListTipoPenalidadUiEvent.CreateNew) }) {
                Text("+")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(state.tiposPenalidades) { penalidad ->
                    TipoPenalidadCard(
                        penalidad = penalidad,
                        onClick = { onEvent(ListTipoPenalidadUiEvent.Edit(penalidad.tipoId)) },
                        onDelete = { onEvent(ListTipoPenalidadUiEvent.Delete(penalidad.tipoId)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TipoPenalidadCard(
    penalidad: TipoPenalidad,
    onClick: () -> Unit,
    onDelete: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(penalidad.nombre, style = MaterialTheme.typography.titleMedium)
                Text(penalidad.descripcion, style = MaterialTheme.typography.bodySmall)
                Text("Puntos: ${penalidad.puntosDescuento}", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = { onDelete(penalidad.tipoId) }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TipoPenalidadListPreview() {
    MaterialTheme {
        TipoPenalidadListBody(
            state = ListTipoPenalidadUiState(
                tiposPenalidades = listOf(
                    TipoPenalidad(1, "Atraso", "10 min tarde", 5),
                    TipoPenalidad(2, "Inasistencia", "No vino", 10)
                )
            ),
            onDrawer = {},
            onEvent = {}
        )
    }
}
package edu.ucne.registroestudiantes.presentation.asignatura.list

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
import edu.ucne.registroestudiantes.domain.asignaturas.model.Asignaturas

@Composable
fun AsignaturaListScreen(
    onDrawer: () -> Unit,
    goToAsignatura: (Int) -> Unit,
    createAsignatura: () -> Unit,
    viewModel: ListAsignaturaViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    AsignaturaListBody(
        state = state,
        onDrawer = onDrawer,
        onEvent = { event ->
            when (event) {
                is ListAsignaturaUiEvent.Edit -> goToAsignatura(event.id)
                is ListAsignaturaUiEvent.CreateNew -> createAsignatura()
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AsignaturaListBody(
    state: ListAsignaturaUiState,
    onDrawer: () -> Unit,
    onEvent: (ListAsignaturaUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Listado de Asignaturas") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(ListAsignaturaUiEvent.CreateNew) }) {
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
                items(state.asignaturas) { asignatura ->
                    AsignaturaCard(
                        asignatura = asignatura,
                        onClick = { onEvent(ListAsignaturaUiEvent.Edit(asignatura.asignaturaId)) },
                        onDelete = { onEvent(ListAsignaturaUiEvent.Delete(asignatura.asignaturaId)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun AsignaturaCard(
    asignatura: Asignaturas,
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
                Text(asignatura.nombre, style = MaterialTheme.typography.titleMedium)
                Text("Código: ${asignatura.codigo}", style = MaterialTheme.typography.bodySmall)
                Text("Aula: ${asignatura.aula} | Créditos: ${asignatura.creditos}", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = { onDelete(asignatura.asignaturaId) }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AsignaturaListPreview() {
    MaterialTheme {
        AsignaturaListBody(
            state = ListAsignaturaUiState(
                asignaturas = listOf(
                    Asignaturas(1, 101, "Programación I", 101, 3),
                    Asignaturas(2, 202, "Base de Datos", 202, 4)
                )
            ),
            onDrawer = {},
            onEvent = {}
        )
    }
}
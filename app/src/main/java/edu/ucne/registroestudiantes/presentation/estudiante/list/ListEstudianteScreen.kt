package edu.ucne.registroestudiantes.presentation.estudiante.list

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
import edu.ucne.registroestudiantes.domain.estudiantes.model.Estudiante

@Composable
fun EstudianteListScreen(
    onDrawer: () -> Unit,
    goToEstudiante: (Int) -> Unit,
    createEstudiante: () -> Unit,
    viewModel: ListEstudianteViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    EstudianteListBody(
        state = state,
        onDrawer = onDrawer,
        onEvent = { event ->
            when (event) {
                is ListEstudianteUiEvent.Edit -> goToEstudiante(event.id)
                is ListEstudianteUiEvent.CreateNew -> createEstudiante()
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EstudianteListBody(
    state: ListEstudianteUiState,
    onDrawer: () -> Unit,
    onEvent: (ListEstudianteUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Listado de Estudiantes") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(ListEstudianteUiEvent.CreateNew) }) {
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
                items(state.estudiantes) { estudiante ->
                    EstudianteCard(
                        estudiante = estudiante,
                        onClick = { onEvent(ListEstudianteUiEvent.Edit(estudiante.estudianteId)) },
                        onDelete = { onEvent(ListEstudianteUiEvent.Delete(estudiante.estudianteId)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun EstudianteCard(
    estudiante: Estudiante,
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
                Text(estudiante.nombres, style = MaterialTheme.typography.titleMedium)
                Text(estudiante.email, style = MaterialTheme.typography.bodySmall)
                Text("Edad: ${estudiante.edad}", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = { onDelete(estudiante.estudianteId) }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EstudianteListPreview() {
    MaterialTheme {
        EstudianteListBody(
            state = ListEstudianteUiState(
                estudiantes = listOf(
                    Estudiante(1, "Jose Lopez", "jose@test.com", 22),
                    Estudiante(2, "Maria Garcia", "maria@test.com", 21)
                )
            ),
            onDrawer = {},
            onEvent = {}
        )
    }
}
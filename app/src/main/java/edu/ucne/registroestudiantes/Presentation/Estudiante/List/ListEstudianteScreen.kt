package edu.ucne.registroestudiantes.Presentation.Estudiante.List

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registroestudiantes.Domain.Model.Estudiante

@Composable
fun EstudianteListScreen(
    onNavigateToEdit: (Int) -> Unit,
    onNavigateToCreate: () -> Unit,
    viewModel: ListEstudianteViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EstudianteListBody(
        state = state,
        onEvent = { event ->
            when (event) {
                is ListEstudianteUiEvent.Edit -> onNavigateToEdit(event.id)
                is ListEstudianteUiEvent.CreateNew -> onNavigateToCreate()
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@Composable
private fun EstudianteListBody(
    state: ListEstudianteUiState,
    onEvent: (ListEstudianteUiEvent) -> Unit
) {
    Scaffold(
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
private fun EstudianteListBodyPreview() {
    MaterialTheme {
        val state = ListEstudianteUiState(
            estudiantes = listOf(
                Estudiante(estudianteId = 1, nombres = "Jose Duarte", email = "jose@email.com", edad = 20),
                Estudiante(estudianteId = 2, nombres = "Juana Castro", email = "juana@email.com", edad = 22)
            )
        )
        EstudianteListBody(state) {}
    }
}
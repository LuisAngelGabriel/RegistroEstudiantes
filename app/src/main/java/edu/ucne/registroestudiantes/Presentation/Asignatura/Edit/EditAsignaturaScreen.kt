package edu.ucne.registroestudiantes.Presentation.Asignatura.Edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignaturaScreen(
    asignaturaId: Int?,
    goBack: () -> Unit,
    onDrawer: () -> Unit,
    viewModel: AsignaturaViewModel = hiltViewModel()
) {
    LaunchedEffect(asignaturaId) {
        viewModel.onEvent(AsignaturaUiEvent.Load(asignaturaId))
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.saved, state.deleted) {
        if (state.saved || state.deleted) {
            goBack()
        }
    }

    AsignaturaBody(
        state = state,
        onEvent = viewModel::onEvent,
        goBack = goBack,
        onDrawer = onDrawer
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AsignaturaBody(
    state: EditAsignaturaUiState,
    onEvent: (AsignaturaUiEvent) -> Unit,
    goBack: () -> Unit,
    onDrawer: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (state.isNew) "Nueva Asignatura" else "Editar Asignatura") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = goBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.nombre,
                onValueChange = { onEvent(AsignaturaUiEvent.NombreChanged(it)) },
                label = { Text("Nombre de la Asignatura") },
                isError = state.nombreError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.nombreError?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.codigo?.toString() ?: "",
                onValueChange = { onEvent(AsignaturaUiEvent.CodigoChanged(it)) },
                label = { Text("Código") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.codigoError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.codigoError?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.aula?.toString() ?: "",
                onValueChange = { onEvent(AsignaturaUiEvent.AulaChanged(it)) },
                label = { Text("Aula") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.aulaError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.aulaError?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.creditos?.toString() ?: "",
                onValueChange = { onEvent(AsignaturaUiEvent.CreditosChanged(it)) },
                label = { Text("Créditos") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.creditosError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.creditosError?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { onEvent(AsignaturaUiEvent.Save) },
                    enabled = !state.isSaving,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Guardar")
                }

                if (!state.isNew) {
                    Spacer(Modifier.width(8.dp))
                    OutlinedButton(
                        onClick = { onEvent(AsignaturaUiEvent.Delete) },
                        enabled = !state.isDeleting,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Eliminar")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AsignaturaBodyPreview() {
    val state = EditAsignaturaUiState(
        nombre = "Programación Móvil",
        codigo = 202,
        aula = 405,
        creditos = 4
    )
    MaterialTheme {
        AsignaturaBody(state = state, onEvent = {}, goBack = {}, onDrawer = {})
    }
}
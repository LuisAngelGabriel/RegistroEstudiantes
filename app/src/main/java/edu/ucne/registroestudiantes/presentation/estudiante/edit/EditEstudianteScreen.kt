package edu.ucne.registroestudiantes.presentation.estudiante.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEstudianteScreen(
    estudianteId: Int?,
    onNavigateBack: () -> Unit,
    onDrawer: () -> Unit,
    viewModel: EditEstudianteViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    remember(estudianteId) {
        viewModel.onEvent(EditEstudianteUiEvent.Load(estudianteId))
        true
    }

    if (state.saved || state.deleted) {
        SideEffect {
            onNavigateBack()
        }
    }

    EditEstudianteBody(
        state = state,
        onEvent = viewModel::onEvent,
        onNavigateBack = onNavigateBack,
        onDrawer = onDrawer
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditEstudianteBody(
    state: EditEstudianteUiState,
    onEvent: (EditEstudianteUiEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onDrawer: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (state.isNew) "Nuevo Estudiante" else "Editar Estudiante") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(8.dp)
        ) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    OutlinedTextField(
                        value = state.nombres,
                        onValueChange = { onEvent(EditEstudianteUiEvent.NombresChanged(it)) },
                        label = { Text("Nombres") },
                        isError = state.nombresError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.nombresError?.let { Text(text = it, color = Color.Red) }

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = state.email,
                        onValueChange = { onEvent(EditEstudianteUiEvent.EmailChanged(it)) },
                        label = { Text("Email") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = state.emailError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.emailError?.let { Text(text = it, color = Color.Red) }

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = state.edad?.toString() ?: "",
                        onValueChange = { onEvent(EditEstudianteUiEvent.EdadChanged(it)) },
                        label = { Text("Edad") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = state.edadError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.edadError?.let { Text(text = it, color = Color.Red) }

                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(
                            onClick = { onEvent(EditEstudianteUiEvent.Save) },
                            enabled = !state.isSaving
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Guardar")
                            Text("Guardar")
                        }

                        if (!state.isNew) {
                            OutlinedButton(
                                onClick = { onEvent(EditEstudianteUiEvent.Delete) },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}
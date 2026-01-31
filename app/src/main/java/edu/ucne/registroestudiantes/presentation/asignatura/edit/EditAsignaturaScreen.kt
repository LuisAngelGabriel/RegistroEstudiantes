package edu.ucne.registroestudiantes.presentation.asignatura.edit

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
fun AsignaturaScreen(
    asignaturaId: Int?,
    viewModel: AsignaturaViewModel = hiltViewModel(),
    goBack: () -> Unit,
    onDrawer: () -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    remember(asignaturaId) {
        viewModel.onEvent(AsignaturaUiEvent.Load(asignaturaId))
        true
    }

    if (uiState.saved || uiState.deleted) {
        SideEffect {
            goBack()
        }
    }

    AsignaturaBodyScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        goBack = goBack,
        onDrawer = onDrawer
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignaturaBodyScreen(
    uiState: EditAsignaturaUiState,
    onEvent: (AsignaturaUiEvent) -> Unit,
    goBack: () -> Unit,
    onDrawer: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (uiState.isNew) "Nueva Asignatura" else "Editar Asignatura") },
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    OutlinedTextField(
                        label = { Text("Nombre de la Asignatura") },
                        value = uiState.nombre,
                        onValueChange = { onEvent(AsignaturaUiEvent.NombreChanged(it)) },
                        isError = uiState.nombreError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    uiState.nombreError?.let {
                        Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        label = { Text("Código") },
                        value = uiState.codigo?.toString() ?: "",
                        onValueChange = { onEvent(AsignaturaUiEvent.CodigoChanged(it)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = uiState.codigoError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    uiState.codigoError?.let {
                        Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        label = { Text("Aula") },
                        value = uiState.aula?.toString() ?: "",
                        onValueChange = { onEvent(AsignaturaUiEvent.AulaChanged(it)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = uiState.aulaError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    uiState.aulaError?.let {
                        Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        label = { Text("Créditos") },
                        value = uiState.creditos?.toString() ?: "",
                        onValueChange = { onEvent(AsignaturaUiEvent.CreditosChanged(it)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = uiState.creditosError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    uiState.creditosError?.let {
                        Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(
                            onClick = { onEvent(AsignaturaUiEvent.Save) },
                            enabled = !uiState.isSaving
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Guardar")
                            Text("Guardar")
                        }

                        if (!uiState.isNew) {
                            OutlinedButton(
                                onClick = { onEvent(AsignaturaUiEvent.Delete) },
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
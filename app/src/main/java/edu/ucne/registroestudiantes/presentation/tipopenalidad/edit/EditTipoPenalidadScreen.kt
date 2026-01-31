package edu.ucne.registroestudiantes.presentation.tipopenalidad.edit

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
fun TipoPenalidadScreen(
    tipoId: Int?,
    viewModel: TipoPenalidadViewModel = hiltViewModel(),
    goBack: () -> Unit,
    onDrawer: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    remember(tipoId) {
        viewModel.onEvent(TipoPenalidadUiEvent.Load(tipoId))
        true
    }

    if (state.saved || state.deleted) {
        SideEffect {
            goBack()
        }
    }

    TipoPenalidadBody(
        state = state,
        onEvent = viewModel::onEvent,
        goBack = goBack,
        onDrawer = onDrawer
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TipoPenalidadBody(
    state: EditTipoPenalidadUiState,
    onEvent: (TipoPenalidadUiEvent) -> Unit,
    goBack: () -> Unit,
    onDrawer: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (state.isNew) "Nueva Penalidad" else "Editar Penalidad") },
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
                .fillMaxSize()
                .padding(padding)
                .padding(8.dp)
        ) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    OutlinedTextField(
                        value = state.nombre,
                        onValueChange = { onEvent(TipoPenalidadUiEvent.NombreChanged(it)) },
                        label = { Text("Nombre") },
                        isError = state.nombreError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.nombreError?.let { Text(text = it, color = Color.Red) }

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = state.descripcion,
                        onValueChange = { onEvent(TipoPenalidadUiEvent.DescripcionChanged(it)) },
                        label = { Text("Descripción") },
                        isError = state.descripcionError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.descripcionError?.let { Text(text = it, color = Color.Red) }

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = state.puntosDescuento?.toString() ?: "",
                        onValueChange = { onEvent(TipoPenalidadUiEvent.PuntosDescuentoChanged(it)) },
                        label = { Text("Puntos Descuento") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = state.puntosDescuentoError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.puntosDescuentoError?.let { Text(text = it, color = Color.Red) }

                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(
                            onClick = { onEvent(TipoPenalidadUiEvent.Save) },
                            enabled = !state.isSaving
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Guardar")
                            Text("Guardar")
                        }

                        if (!state.isNew) {
                            OutlinedButton(
                                onClick = { onEvent(TipoPenalidadUiEvent.Delete) },
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
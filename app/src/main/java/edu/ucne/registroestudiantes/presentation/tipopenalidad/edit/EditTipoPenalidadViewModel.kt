package edu.ucne.registroestudiantes.presentation.tipopenalidad.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroestudiantes.domain.tipopenalidad.model.TipoPenalidad
import edu.ucne.registroestudiantes.domain.tipopenalidad.usecase.DeleteTipoPenalidadUseCase
import edu.ucne.registroestudiantes.domain.tipopenalidad.usecase.GetTipoPenalidadUseCase
import edu.ucne.registroestudiantes.domain.tipopenalidad.usecase.UpsertTipoPenalidadUseCase
import edu.ucne.registroestudiantes.domain.tipopenalidad.usecase.ValidateTipoPenalidadUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TipoPenalidadViewModel @Inject constructor(
    private val getTipoPenalidadUseCase: GetTipoPenalidadUseCase,
    private val upsertTipoPenalidadUseCase: UpsertTipoPenalidadUseCase,
    private val deleteTipoPenalidadUseCase: DeleteTipoPenalidadUseCase,
    private val validateTipoPenalidadUseCase: ValidateTipoPenalidadUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditTipoPenalidadUiState())
    val state: StateFlow<EditTipoPenalidadUiState> = _state.asStateFlow()

    private fun validateNombre(nombre: String) {
        viewModelScope.launch {
            val result = validateTipoPenalidadUseCase(
                nombre = nombre,
                descripcion = _state.value.descripcion,
                puntosDescuento = _state.value.puntosDescuento,
                currentTipoId = _state.value.tipoId
            )
            _state.update { it.copy(nombreError = result.nombreError) }
        }
    }

    private fun validateDescripcion(descripcion: String) {
        viewModelScope.launch {
            val result = validateTipoPenalidadUseCase(
                nombre = _state.value.nombre,
                descripcion = descripcion,
                puntosDescuento = _state.value.puntosDescuento,
                currentTipoId = _state.value.tipoId
            )
            _state.update { it.copy(descripcionError = result.descripcionError) }
        }
    }

    private fun validatePuntos(puntos: Int?) {
        viewModelScope.launch {
            val result = validateTipoPenalidadUseCase(
                nombre = _state.value.nombre,
                descripcion = _state.value.descripcion,
                puntosDescuento = puntos,
                currentTipoId = _state.value.tipoId
            )
            _state.update { it.copy(puntosDescuentoError = result.puntosDescuentoError) }
        }
    }

    fun onEvent(event: TipoPenalidadUiEvent) {
        when (event) {
            is TipoPenalidadUiEvent.Load -> onLoad(event.id)
            is TipoPenalidadUiEvent.NombreChanged -> {
                _state.update { it.copy(nombre = event.value, nombreError = null) }
                validateNombre(event.value)
            }
            is TipoPenalidadUiEvent.DescripcionChanged -> {
                _state.update { it.copy(descripcion = event.value, descripcionError = null) }
                validateDescripcion(event.value)
            }
            is TipoPenalidadUiEvent.PuntosDescuentoChanged -> {
                val puntosInt = event.value.toIntOrNull()
                _state.update { it.copy(puntosDescuento = puntosInt, puntosDescuentoError = null) }
                validatePuntos(puntosInt)
            }
            TipoPenalidadUiEvent.Save -> onSave()
            TipoPenalidadUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, tipoId = null) }
            return
        }
        viewModelScope.launch {
            val penalidad = getTipoPenalidadUseCase(id)
            penalidad?.let { item ->
                _state.update {
                    it.copy(
                        isNew = false,
                        tipoId = item.tipoId,
                        nombre = item.nombre,
                        descripcion = item.descripcion,
                        puntosDescuento = item.puntosDescuento
                    )
                }
            }
        }
    }

    private fun onSave() {
        viewModelScope.launch {
            val validation = validateTipoPenalidadUseCase(
                nombre = _state.value.nombre,
                descripcion = _state.value.descripcion,
                puntosDescuento = _state.value.puntosDescuento,
                currentTipoId = _state.value.tipoId
            )

            if (!validation.isValid) {
                _state.update {
                    it.copy(
                        nombreError = validation.nombreError,
                        descripcionError = validation.descripcionError,
                        puntosDescuentoError = validation.puntosDescuentoError
                    )
                }
                return@launch
            }

            _state.update { it.copy(isSaving = true) }
            try {
                val penalidad = TipoPenalidad(
                    tipoId = _state.value.tipoId ?: 0,
                    nombre = _state.value.nombre,
                    descripcion = _state.value.descripcion,
                    puntosDescuento = _state.value.puntosDescuento ?: 0
                )
                upsertTipoPenalidadUseCase(penalidad)
                _state.update { it.copy(isSaving = false, saved = true) }
            } catch (e: Exception) {
                _state.update { it.copy(isSaving = false, nombreError = e.message) }
            }
        }
    }

    private fun onDelete() {
        val id = _state.value.tipoId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            try {
                deleteTipoPenalidadUseCase(id)
                _state.update { it.copy(isDeleting = false, deleted = true) }
            } catch (e: Exception) {
                _state.update { it.copy(isDeleting = false, nombreError = e.message) }
            }
        }
    }
}
package edu.ucne.registroestudiantes.presentation.asignatura.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroestudiantes.domain.asignaturas.model.Asignaturas
import edu.ucne.registroestudiantes.domain.asignaturas.usecase.DeleteAsignaturaUseCase
import edu.ucne.registroestudiantes.domain.asignaturas.usecase.GetAsignaturaUseCase
import edu.ucne.registroestudiantes.domain.asignaturas.usecase.UpsertAsignaturaUseCase
import edu.ucne.registroestudiantes.domain.asignaturas.usecase.ValidateAsignaturaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AsignaturaViewModel @Inject constructor(
    private val getAsignaturaUseCase: GetAsignaturaUseCase,
    private val upsertAsignaturaUseCase: UpsertAsignaturaUseCase,
    private val deleteAsignaturaUseCase: DeleteAsignaturaUseCase,
    private val validateAsignaturaUseCase: ValidateAsignaturaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditAsignaturaUiState())
    val state: StateFlow<EditAsignaturaUiState> = _state.asStateFlow()

    private fun validateNombre(nombre: String) {
        viewModelScope.launch {
            val result = validateAsignaturaUseCase(
                nombre = nombre,
                codigo = _state.value.codigo,
                aula = _state.value.aula,
                creditos = _state.value.creditos,
                currentAsignaturaId = _state.value.asignaturaId
            )
            _state.update { it.copy(nombreError = result.nombreError) }
        }
    }

    private fun validateCodigo(codigo: Int?) {
        viewModelScope.launch {
            val result = validateAsignaturaUseCase(
                nombre = _state.value.nombre,
                codigo = codigo,
                aula = _state.value.aula,
                creditos = _state.value.creditos,
                currentAsignaturaId = _state.value.asignaturaId
            )
            _state.update { it.copy(codigoError = result.codigoError) }
        }
    }

    private fun validateAula(aula: Int?) {
        viewModelScope.launch {
            val result = validateAsignaturaUseCase(
                nombre = _state.value.nombre,
                codigo = _state.value.codigo,
                aula = aula,
                creditos = _state.value.creditos,
                currentAsignaturaId = _state.value.asignaturaId
            )
            _state.update { it.copy(aulaError = result.aulaError) }
        }
    }

    private fun validateCreditos(creditos: Int?) {
        viewModelScope.launch {
            val result = validateAsignaturaUseCase(
                nombre = _state.value.nombre,
                codigo = _state.value.codigo,
                aula = _state.value.aula,
                creditos = creditos,
                currentAsignaturaId = _state.value.asignaturaId
            )
            _state.update { it.copy(creditosError = result.creditosError) }
        }
    }

    fun onEvent(event: AsignaturaUiEvent) {
        when (event) {
            is AsignaturaUiEvent.Load -> onLoad(event.id)
            is AsignaturaUiEvent.NombreChanged -> {
                _state.update { it.copy(nombre = event.value, nombreError = null) }
                validateNombre(event.value)
            }
            is AsignaturaUiEvent.CodigoChanged -> {
                val codigoInt = event.value.toIntOrNull()
                _state.update { it.copy(codigo = codigoInt, codigoError = null) }
                validateCodigo(codigoInt)
            }
            is AsignaturaUiEvent.AulaChanged -> {
                val aulaInt = event.value.toIntOrNull()
                _state.update { it.copy(aula = aulaInt, aulaError = null) }
                validateAula(aulaInt)
            }
            is AsignaturaUiEvent.CreditosChanged -> {
                val creditosInt = event.value.toIntOrNull()
                _state.update { it.copy(creditos = creditosInt, creditosError = null) }
                validateCreditos(creditosInt)
            }
            AsignaturaUiEvent.Save -> onSave()
            AsignaturaUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, asignaturaId = null) }
            return
        }
        viewModelScope.launch {
            val asignatura = getAsignaturaUseCase(id)
            asignatura?.let { item ->
                _state.update {
                    it.copy(
                        isNew = false,
                        asignaturaId = item.asignaturaId,
                        codigo = item.codigo,
                        nombre = item.nombre,
                        aula = item.aula,
                        creditos = item.creditos
                    )
                }
            }
        }
    }

    private fun onSave() {
        viewModelScope.launch {
            val validation = validateAsignaturaUseCase(
                nombre = _state.value.nombre,
                codigo = _state.value.codigo,
                aula = _state.value.aula,
                creditos = _state.value.creditos,
                currentAsignaturaId = _state.value.asignaturaId
            )

            if (!validation.isValid) {
                _state.update {
                    it.copy(
                        nombreError = validation.nombreError,
                        codigoError = validation.codigoError,
                        aulaError = validation.aulaError,
                        creditosError = validation.creditosError
                    )
                }
                return@launch
            }

            _state.update { it.copy(isSaving = true) }
            try {
                val asignatura = Asignaturas(
                    asignaturaId = _state.value.asignaturaId ?: 0,
                    codigo = _state.value.codigo ?: 0,
                    nombre = _state.value.nombre,
                    aula = _state.value.aula ?: 0,
                    creditos = _state.value.creditos ?: 0
                )
                upsertAsignaturaUseCase(asignatura)
                _state.update { it.copy(isSaving = false, saved = true) }
            } catch (e: Exception) {
                _state.update { it.copy(isSaving = false, nombreError = e.message) }
            }
        }
    }

    private fun onDelete() {
        val id = _state.value.asignaturaId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            try {
                deleteAsignaturaUseCase(id)
                _state.update { it.copy(isDeleting = false, deleted = true) }
            } catch (e: Exception) {
                _state.update { it.copy(isDeleting = false, nombreError = e.message) }
            }
        }
    }
}
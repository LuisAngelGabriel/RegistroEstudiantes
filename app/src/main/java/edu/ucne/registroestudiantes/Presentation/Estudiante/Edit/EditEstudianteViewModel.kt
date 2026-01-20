package edu.ucne.registroestudiantes.Presentation.Estudiante.Edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroestudiantes.Domain.Model.Estudiante
import edu.ucne.registroestudiantes.Domain.UseCase.DeleteEstudianteUseCase
import edu.ucne.registroestudiantes.Domain.UseCase.GetEstudianteUseCase
import edu.ucne.registroestudiantes.Domain.UseCase.UpsertEstudianteUseCase
import edu.ucne.registroestudiantes.Domain.UseCase.ValidateEstudianteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditEstudianteViewModel @Inject constructor(
    private val getEstudianteUseCase: GetEstudianteUseCase,
    private val upsertEstudianteUseCase: UpsertEstudianteUseCase,
    private val deleteEstudianteUseCase: DeleteEstudianteUseCase,
    private val validateEstudianteUseCase: ValidateEstudianteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditEstudianteUiState())
    val state: StateFlow<EditEstudianteUiState> = _state.asStateFlow()

    private fun validateNombre(nombre: String) {
        viewModelScope.launch {
            val result = validateEstudianteUseCase(
                nombre = nombre,
                email = _state.value.email,
                edad = _state.value.edad,
                currentEstudianteId = _state.value.estudianteId
            )
            _state.update { it.copy(nombresError = result.nombreError) }
        }
    }

    private fun validateEmail(email: String) {
        viewModelScope.launch {
            val result = validateEstudianteUseCase(
                nombre = _state.value.nombres,
                email = email,
                edad = _state.value.edad,
                currentEstudianteId = _state.value.estudianteId
            )
            _state.update { it.copy(emailError = result.emailError) }
        }
    }

    private fun validateEdad(edad: Int?) {
        viewModelScope.launch {
            val result = validateEstudianteUseCase(
                nombre = _state.value.nombres,
                email = _state.value.email,
                edad = edad,
                currentEstudianteId = _state.value.estudianteId
            )
            _state.update { it.copy(edadError = result.edadError) }
        }
    }

    fun onEvent(event: EditEstudianteUiEvent) {
        when (event) {
            is EditEstudianteUiEvent.Load -> onLoad(event.id)
            is EditEstudianteUiEvent.NombresChanged -> {
                _state.update { it.copy(nombres = event.value, nombresError = null) }
                validateNombre(event.value)
            }
            is EditEstudianteUiEvent.EmailChanged -> {
                _state.update { it.copy(email = event.value, emailError = null) }
                validateEmail(event.value)
            }
            is EditEstudianteUiEvent.EdadChanged -> {
                val edadInt = event.value.toIntOrNull()
                _state.update { it.copy(edad = edadInt, edadError = null) }
                validateEdad(edadInt)
            }
            EditEstudianteUiEvent.Save -> onSave()
            EditEstudianteUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, estudianteId = null) }
            return
        }
        viewModelScope.launch {
            val estudiante = getEstudianteUseCase(id)
            estudiante?.let { item ->
                _state.update {
                    it.copy(
                        isNew = false,
                        estudianteId = item.estudianteId,
                        nombres = item.nombres,
                        email = item.email,
                        edad = item.edad
                    )
                }
            }
        }
    }

    private fun onSave() {
        viewModelScope.launch {
            val validation = validateEstudianteUseCase(
                nombre = _state.value.nombres,
                email = _state.value.email,
                edad = _state.value.edad,
                currentEstudianteId = _state.value.estudianteId
            )

            if (!validation.isValid) {
                _state.update {
                    it.copy(
                        nombresError = validation.nombreError,
                        emailError = validation.emailError,
                        edadError = validation.edadError
                    )
                }
                return@launch
            }

            _state.update { it.copy(isSaving = true) }
            try {
                val estudiante = Estudiante(
                    estudianteId = _state.value.estudianteId ?: 0,
                    nombres = _state.value.nombres,
                    email = _state.value.email,
                    edad = _state.value.edad ?: 0
                )
                upsertEstudianteUseCase(estudiante)
                _state.update { it.copy(isSaving = false, saved = true) }
            } catch (e: Exception) {
                _state.update { it.copy(isSaving = false, nombresError = e.message) }
            }
        }
    }

    private fun onDelete() {
        val id = _state.value.estudianteId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            try {
                deleteEstudianteUseCase(id)
                _state.update { it.copy(isDeleting = false, deleted = true) }
            } catch (e: Exception) {
                _state.update { it.copy(isDeleting = false, nombresError = e.message) }
            }
        }
    }
}
package edu.ucne.registroestudiantes.presentation.estudiante.list
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroestudiantes.domain.UseCase.DeleteEstudianteUseCase
import edu.ucne.registroestudiantes.domain.UseCase.ObserveEstudianteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListEstudianteViewModel @Inject constructor(
    private val observeEstudianteUseCase: ObserveEstudianteUseCase,
    private val deleteEstudianteUseCase: DeleteEstudianteUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ListEstudianteUiState(isLoading = true))
    val state: StateFlow<ListEstudianteUiState> = _state.asStateFlow()

    init {
        onEvent(ListEstudianteUiEvent.Load)
    }

    fun onEvent(event: ListEstudianteUiEvent) {
        when (event) {
            ListEstudianteUiEvent.Load -> observeEstudiantes()
            is ListEstudianteUiEvent.Delete -> onDelete(event.id)
            ListEstudianteUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ListEstudianteUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
            is ListEstudianteUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
        }
    }

    private fun observeEstudiantes() {
        viewModelScope.launch {
            observeEstudianteUseCase().collectLatest { estudiantesList ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        estudiantes = estudiantesList,
                        message = null
                    )
                }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            try {
                deleteEstudianteUseCase(id)
                onEvent(ListEstudianteUiEvent.ShowMessage("Estudiante eliminado"))
            } catch (e: Exception) {
                onEvent(ListEstudianteUiEvent.ShowMessage("Error al eliminar: ${e.message}"))
            }
        }
    }

    fun onNavigationHandled() {
        _state.update {
            it.copy(
                navigateToCreate = false,
                navigateToEditId = null,
                message = null
            )
        }
    }
}
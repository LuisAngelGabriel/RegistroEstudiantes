package edu.ucne.registroestudiantes.presentation.asignatura.list
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroestudiantes.domain.asignaturas.usecase.DeleteAsignaturaUseCase
import edu.ucne.registroestudiantes.domain.asignaturas.usecase.ObserveAsignaturaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListAsignaturaViewModel @Inject constructor(
    private val observeAsignaturaUseCase: ObserveAsignaturaUseCase,
    private val deleteAsignaturaUseCase: DeleteAsignaturaUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ListAsignaturaUiState(isLoading = true))
    val state: StateFlow<ListAsignaturaUiState> = _state.asStateFlow()

    init {
        onEvent(ListAsignaturaUiEvent.Load)
    }

    fun onEvent(event: ListAsignaturaUiEvent) {
        when (event) {
            ListAsignaturaUiEvent.Load -> observeAsignaturas()
            is ListAsignaturaUiEvent.Delete -> onDelete(event.id)
            ListAsignaturaUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ListAsignaturaUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
            is ListAsignaturaUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
        }
    }

    private fun observeAsignaturas() {
        viewModelScope.launch {
            observeAsignaturaUseCase().collectLatest { asignaturasList ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        asignaturas = asignaturasList,
                        message = null
                    )
                }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            try {
                deleteAsignaturaUseCase(id)
                onEvent(ListAsignaturaUiEvent.ShowMessage("Asignatura eliminada"))
            } catch (e: Exception) {
                onEvent(ListAsignaturaUiEvent.ShowMessage("Error al eliminar: ${e.message}"))
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
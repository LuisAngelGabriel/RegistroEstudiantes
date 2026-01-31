package edu.ucne.registroestudiantes.presentation.tipopenalidad.list
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroestudiantes.domain.tipopenalidad.usecase.DeleteTipoPenalidadUseCase
import edu.ucne.registroestudiantes.domain.tipopenalidad.usecase.ObserveTipoPenalidadUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListTipoPenalidadViewModel @Inject constructor(
    private val observeTipoPenalidadUseCase: ObserveTipoPenalidadUseCase,
    private val deleteTipoPenalidadUseCase: DeleteTipoPenalidadUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ListTipoPenalidadUiState(isLoading = true))
    val state: StateFlow<ListTipoPenalidadUiState> = _state.asStateFlow()

    init {
        onEvent(ListTipoPenalidadUiEvent.Load)
    }

    fun onEvent(event: ListTipoPenalidadUiEvent) {
        when (event) {
            ListTipoPenalidadUiEvent.Load -> observeTiposPenalidades()
            is ListTipoPenalidadUiEvent.Delete -> onDelete(event.id)
            ListTipoPenalidadUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ListTipoPenalidadUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
            is ListTipoPenalidadUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
        }
    }

    private fun observeTiposPenalidades() {
        viewModelScope.launch {
            observeTipoPenalidadUseCase().collectLatest { penalidadList ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        tiposPenalidades = penalidadList,
                        message = null
                    )
                }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            try {
                deleteTipoPenalidadUseCase(id)
                onEvent(ListTipoPenalidadUiEvent.ShowMessage("Tipo de penalidad eliminado"))
            } catch (e: Exception) {
                onEvent(ListTipoPenalidadUiEvent.ShowMessage("Error al eliminar: ${e.message}"))
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
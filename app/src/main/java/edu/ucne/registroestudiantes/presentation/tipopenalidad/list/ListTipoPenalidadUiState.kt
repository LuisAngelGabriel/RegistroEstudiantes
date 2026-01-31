package edu.ucne.registroestudiantes.presentation.tipopenalidad.list
import edu.ucne.registroestudiantes.domain.tipopenalidad.model.TipoPenalidad

data class ListTipoPenalidadUiState(
    val isLoading: Boolean = false,
    val tiposPenalidades: List<TipoPenalidad> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)
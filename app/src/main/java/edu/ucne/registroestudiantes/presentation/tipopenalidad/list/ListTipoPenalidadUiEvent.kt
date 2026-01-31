package edu.ucne.registroestudiantes.presentation.tipopenalidad.list

sealed interface ListTipoPenalidadUiEvent {
    data object Load : ListTipoPenalidadUiEvent
    data class Delete(val id: Int) : ListTipoPenalidadUiEvent
    data object CreateNew : ListTipoPenalidadUiEvent
    data class Edit(val id: Int) : ListTipoPenalidadUiEvent
    data class ShowMessage(val message: String) : ListTipoPenalidadUiEvent
}
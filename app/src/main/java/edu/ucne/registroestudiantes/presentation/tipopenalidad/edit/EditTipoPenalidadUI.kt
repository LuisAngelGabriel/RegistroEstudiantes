package edu.ucne.registroestudiantes.presentation.tipopenalidad.edit

sealed interface TipoPenalidadUiEvent {
    data class Load(val id: Int?) : TipoPenalidadUiEvent
    data class NombreChanged(val value: String) : TipoPenalidadUiEvent
    data class DescripcionChanged(val value: String) : TipoPenalidadUiEvent
    data class PuntosDescuentoChanged(val value: String) : TipoPenalidadUiEvent
    data object Save : TipoPenalidadUiEvent
    data object Delete : TipoPenalidadUiEvent
}
package edu.ucne.registroestudiantes.Presentation.Asignatura.Edit

sealed interface AsignaturaUiEvent {
    data class Load(val id: Int?) : AsignaturaUiEvent
    data class CodigoChanged(val value: String) : AsignaturaUiEvent
    data class NombreChanged(val value: String) : AsignaturaUiEvent
    data class AulaChanged(val value: String) : AsignaturaUiEvent
    data class CreditosChanged(val value: String) : AsignaturaUiEvent
    data object Save : AsignaturaUiEvent
    data object Delete : AsignaturaUiEvent
}
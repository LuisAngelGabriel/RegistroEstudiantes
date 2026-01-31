package edu.ucne.registroestudiantes.presentation.estudiante.list

sealed interface ListEstudianteUiEvent {
    data object Load : ListEstudianteUiEvent
    data class Delete(val id: Int) : ListEstudianteUiEvent
    data object CreateNew : ListEstudianteUiEvent
    data class Edit(val id: Int) : ListEstudianteUiEvent
    data class ShowMessage(val message: String) : ListEstudianteUiEvent
}
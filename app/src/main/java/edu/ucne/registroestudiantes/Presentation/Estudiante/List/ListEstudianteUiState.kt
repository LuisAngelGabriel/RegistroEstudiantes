package edu.ucne.registroestudiantes.Presentation.Estudiante.List
import edu.ucne.registroestudiantes.Domain.Model.Estudiante

data class ListEstudianteUiState(
    val isLoading: Boolean = false,
    val estudiantes: List<Estudiante> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)
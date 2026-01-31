package edu.ucne.registroestudiantes.presentation.estudiante.list
import edu.ucne.registroestudiantes.domain.estudiantes.model.Estudiante

data class ListEstudianteUiState(
    val isLoading: Boolean = false,
    val estudiantes: List<Estudiante> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)
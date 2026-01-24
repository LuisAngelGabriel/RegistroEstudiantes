    package edu.ucne.registroestudiantes.Presentation.Asignatura.List

    import edu.ucne.registroestudiantes.Domain.Asignaturas.Model.Asignaturas

    data class ListAsignaturaUiState(
        val isLoading: Boolean = false,
        val asignaturas: List<Asignaturas> = emptyList(),
        val message: String? = null,
        val navigateToCreate: Boolean = false,
        val navigateToEditId: Int? = null
    )
    package edu.ucne.registroestudiantes.presentation.asignatura.list

    import edu.ucne.registroestudiantes.domain.asignaturas.model.Asignaturas

    data class ListAsignaturaUiState(
        val isLoading: Boolean = false,
        val asignaturas: List<Asignaturas> = emptyList(),
        val message: String? = null,
        val navigateToCreate: Boolean = false,
        val navigateToEditId: Int? = null
    )
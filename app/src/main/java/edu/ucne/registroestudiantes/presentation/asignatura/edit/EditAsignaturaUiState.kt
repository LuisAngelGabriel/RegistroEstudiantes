package edu.ucne.registroestudiantes.presentation.asignatura.edit

data class EditAsignaturaUiState(
    val asignaturaId: Int? = null,
    val codigo: Int? = null,
    val nombre: String = "",
    val aula: Int? = null,
    val creditos: Int? = null,
    val nombreError: String? = null,
    val codigoError: String? = null,
    val aulaError: String? = null,
    val creditosError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false
)
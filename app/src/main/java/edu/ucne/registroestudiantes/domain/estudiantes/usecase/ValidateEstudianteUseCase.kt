package edu.ucne.registroestudiantes.domain.UseCase

import edu.ucne.registroestudiantes.domain.estudiantes.repository.EstudianteRepository
import javax.inject.Inject

class ValidateEstudianteUseCase @Inject constructor(
    private val estudianteRepository: EstudianteRepository
) {
    data class ValidationResult(
        val isValid: Boolean,
        val nombreError: String? = null,
        val emailError: String? = null,
        val edadError: String? = null
    )

    suspend operator fun invoke(
        nombre: String,
        email: String,
        edad: Int?,
        currentEstudianteId: Int? = null
    ): ValidationResult {

        val nombreError = when {
            nombre.isBlank() -> "El nombre es requerido"
            else -> {
                val existingEstudiantes = estudianteRepository.getEstudiantesByNombre(nombre)
                val isDuplicate = if (currentEstudianteId != null) {
                    existingEstudiantes.any { it.estudianteId != currentEstudianteId }
                } else {
                    existingEstudiantes.isNotEmpty()
                }
                if (isDuplicate) "Nombre ya existe" else null
            }
        }

        val emailError = when {
            email.isBlank() -> "El email es requerido"
            !email.contains("@") -> "Email no válido"
            else -> null
        }

        val edadError = when {
            edad == null -> "La edad es requerida"
            edad <= 0 -> "La edad debe ser mayor a 0"
            else -> null
        }

        return ValidationResult(
            isValid = nombreError == null && emailError == null && edadError == null,
            nombreError = nombreError,
            emailError = emailError,
            edadError = edadError
        )
    }
}
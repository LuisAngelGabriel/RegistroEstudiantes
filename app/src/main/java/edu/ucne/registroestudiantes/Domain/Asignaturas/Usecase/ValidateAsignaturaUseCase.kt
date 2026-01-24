package edu.ucne.registroestudiantes.Domain.Asignaturas.Usecase

import edu.ucne.registroestudiantes.Domain.Asignaturas.Repository.AsignaturaRepository
import javax.inject.Inject

class ValidateAsignaturaUseCase @Inject constructor(
    private val asignaturaRepository: AsignaturaRepository
) {
    data class ValidationResult(
        val isValid: Boolean,
        val nombreError: String? = null,
        val codigoError: String? = null,
        val aulaError: String? = null,
        val creditosError: String? = null
    )

    suspend operator fun invoke(
        nombre: String,
        codigo: Int?,
        aula: Int?,
        creditos: Int?,
        currentAsignaturaId: Int? = null
    ): ValidationResult {

        val nombreError = when {
            nombre.isBlank() -> "El nombre es requerido"
            else -> {
                val existingAsignaturas = asignaturaRepository.getAsignaturasByNombre(nombre)
                val isDuplicate = if (currentAsignaturaId != null) {
                    existingAsignaturas.any { it.asignaturaId != currentAsignaturaId }
                } else {
                    existingAsignaturas.isNotEmpty()
                }
                if (isDuplicate) "Ya existe una asignatura registrada con este nombre" else null
            }
        }

        val codigoError = when {
            codigo == null || codigo <= 0 -> "El código es requerido y debe ser mayor a 0"
            else -> null
        }

        val aulaError = when {
            aula == null || aula <= 0 -> "El aula es requerida y debe ser mayor a 0"
            else -> null
        }

        val creditosError = when {
            creditos == null || creditos <= 0 -> "Los créditos son requeridos y deben ser mayores a 0"
            else -> null
        }

        return ValidationResult(
            isValid = nombreError == null && codigoError == null &&
                    aulaError == null && creditosError == null,
            nombreError = nombreError,
            codigoError = codigoError,
            aulaError = aulaError,
            creditosError = creditosError
        )
    }
}
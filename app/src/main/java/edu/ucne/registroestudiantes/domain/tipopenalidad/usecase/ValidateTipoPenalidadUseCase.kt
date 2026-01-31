package edu.ucne.registroestudiantes.domain.tipopenalidad.usecase

import edu.ucne.registroestudiantes.domain.tipopenalidad.repository.TipoPenalidadRepository
import javax.inject.Inject

class ValidateTipoPenalidadUseCase @Inject constructor(
    private val tipoPenalidadRepository: TipoPenalidadRepository
) {
    data class ValidationResult(
        val isValid: Boolean,
        val nombreError: String? = null,
        val descripcionError: String? = null,
        val puntosDescuentoError: String? = null
    )

    suspend operator fun invoke(
        nombre: String,
        descripcion: String,
        puntosDescuento: Int?,
        currentTipoId: Int? = null
    ): ValidationResult {

        val nombreError = when {
            nombre.isBlank() -> "El nombre es requerido"
            else -> {
                val existingPenalidades = tipoPenalidadRepository.getTipoPenalidadByNombre(nombre)
                val isDuplicate = if (currentTipoId != null) {
                    existingPenalidades.any { it.tipoId != currentTipoId }
                } else {
                    existingPenalidades.isNotEmpty()
                }
                if (isDuplicate) "Ya existe un tipo de penalidad registrado con este nombre" else null
            }
        }

        val descripcionError = when {
            descripcion.isBlank() -> "La descripción es requerida"
            else -> null
        }

        val puntosDescuentoError = when {
            puntosDescuento == null || puntosDescuento <= 0 -> "El campo PuntosDescuento debe ser un valor mayor a cero"
            else -> null
        }

        return ValidationResult(
            isValid = nombreError == null && descripcionError == null &&
                    puntosDescuentoError == null,
            nombreError = nombreError,
            descripcionError = descripcionError,
            puntosDescuentoError = puntosDescuentoError
        )
    }
}
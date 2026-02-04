package edu.ucne.registroestudiantes.domain.tipopenalidad.usecase
import edu.ucne.registroestudiantes.domain.tipopenalidad.model.TipoPenalidad
import edu.ucne.registroestudiantes.domain.tipopenalidad.repository.TipoPenalidadRepository
import javax.inject.Inject

class UpsertTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository,
    private val validateTipoPenalidadUseCase: ValidateTipoPenalidadUseCase
) {
    suspend operator fun invoke(tipoPenalidad: TipoPenalidad): Result<Int> {
        return try {
            val validation = validateTipoPenalidadUseCase(
                nombre = tipoPenalidad.nombre,
                descripcion = tipoPenalidad.descripcion,
                puntosDescuento = tipoPenalidad.puntosDescuento,
                currentTipoId = if (tipoPenalidad.tipoId != 0) tipoPenalidad.tipoId else null
            )

            if (!validation.isValid) {
                val errorMsg = validation.nombreError ?: validation.descripcionError ?: validation.puntosDescuentoError ?: "Error de validación"
                Result.failure(IllegalArgumentException(errorMsg))
            } else {
                val id = repository.upsert(tipoPenalidad)
                Result.success(id)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
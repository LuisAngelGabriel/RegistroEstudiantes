package edu.ucne.registroestudiantes.domain.tipopenalidad.usecase
import edu.ucne.registroestudiantes.domain.tipopenalidad.model.TipoPenalidad
import edu.ucne.registroestudiantes.domain.tipopenalidad.repository.TipoPenalidadRepository
import javax.inject.Inject

class UpsertTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(tipoPenalidad: TipoPenalidad): Result<Int> {

        if (tipoPenalidad.nombre.isBlank()) {
            return Result.failure(IllegalArgumentException("El nombre es requerido"))
        }

        if (tipoPenalidad.descripcion.isBlank()) {
            return Result.failure(IllegalArgumentException("La descripción es requerida"))
        }

        if (tipoPenalidad.puntosDescuento <= 0) {
            return Result.failure(IllegalArgumentException("Los puntos deben ser mayor a cero"))
        }

        return runCatching {
            repository.upsert(tipoPenalidad)
        }
    }
}
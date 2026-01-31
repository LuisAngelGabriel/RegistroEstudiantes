package edu.ucne.registroestudiantes.domain.tipopenalidad.usecase
import edu.ucne.registroestudiantes.domain.tipopenalidad.model.TipoPenalidad
import edu.ucne.registroestudiantes.domain.tipopenalidad.repository.TipoPenalidadRepository
import javax.inject.Inject

class GetTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(id: Int): TipoPenalidad? {
        if (id <= 0) throw IllegalArgumentException("El id debe ser mayor que 0")
        return repository.getTipoPenalidad(id)
    }
}
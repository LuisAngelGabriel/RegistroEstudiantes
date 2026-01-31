package edu.ucne.registroestudiantes.domain.tipopenalidad.usecase

import edu.ucne.registroestudiantes.domain.tipopenalidad.repository.TipoPenalidadRepository
import javax.inject.Inject

class DeleteTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(id: Int) {
        if (id <= 0) throw IllegalArgumentException("El ID debe ser mayor que 0")
        repository.delete(id)
    }
}
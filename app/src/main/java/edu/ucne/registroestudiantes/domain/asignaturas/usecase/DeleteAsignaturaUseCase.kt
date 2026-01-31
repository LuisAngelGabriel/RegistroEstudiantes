package edu.ucne.registroestudiantes.domain.asignaturas.usecase

import edu.ucne.registroestudiantes.domain.asignaturas.repository.AsignaturaRepository
import javax.inject.Inject

class DeleteAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(id: Int) {
        if (id <= 0) throw IllegalArgumentException("El ID debe ser mayor que 0")
        repository.delete(id)
    }
}
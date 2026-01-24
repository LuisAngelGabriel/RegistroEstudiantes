package edu.ucne.registroestudiantes.Domain.Asignaturas.Usecase

import edu.ucne.registroestudiantes.Domain.Asignaturas.Repository.AsignaturaRepository
import javax.inject.Inject

class DeleteAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(id: Int) {
        if (id <= 0) throw IllegalArgumentException("El ID debe ser mayor que 0")
        repository.delete(id)
    }
}
package edu.ucne.registroestudiantes.domain.UseCase

import edu.ucne.registroestudiantes.domain.estudiantes.repository.EstudianteRepository
import javax.inject.Inject

class DeleteEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int) {
        if (id <= 0) throw IllegalArgumentException("El ID debe ser mayor que 0")
        repository.delete(id)
    }
}
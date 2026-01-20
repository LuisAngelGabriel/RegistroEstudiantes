package edu.ucne.registroestudiantes.Domain.Usecase

import edu.ucne.registroestudiantes.Domain.Model.Estudiante
import edu.ucne.registroestudiantes.Domain.Repository.EstudianteRepository
import javax.inject.Inject

class GetEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int): Estudiante? {
        if (id <= 0) throw IllegalArgumentException("El id debe ser mayor que 0")
        return repository.getEstudiante(id)
    }
}
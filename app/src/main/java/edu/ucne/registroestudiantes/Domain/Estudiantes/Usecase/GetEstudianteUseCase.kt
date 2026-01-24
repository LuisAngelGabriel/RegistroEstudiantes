package edu.ucne.registroestudiantes.Domain.UseCase

import edu.ucne.registroestudiantes.Domain.Estudiantes.Model.Estudiante
import edu.ucne.registroestudiantes.Domain.Estudiantes.Repository.EstudianteRepository
import javax.inject.Inject

class GetEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int): Estudiante? {
        if (id <= 0) throw IllegalArgumentException("El id debe ser mayor que 0")
        return repository.getEstudiante(id)
    }
}
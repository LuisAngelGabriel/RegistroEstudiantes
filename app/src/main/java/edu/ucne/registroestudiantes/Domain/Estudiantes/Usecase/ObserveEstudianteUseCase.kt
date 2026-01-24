package edu.ucne.registroestudiantes.Domain.UseCase
import edu.ucne.registroestudiantes.Domain.Estudiantes.Model.Estudiante
import edu.ucne.registroestudiantes.Domain.Estudiantes.Repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    operator fun invoke(): Flow<List<Estudiante>> {
        return repository.observeEstudiante()
    }
}
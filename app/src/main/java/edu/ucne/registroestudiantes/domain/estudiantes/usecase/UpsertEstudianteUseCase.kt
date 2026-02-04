package edu.ucne.registroestudiantes.domain.UseCase
import edu.ucne.registroestudiantes.domain.estudiantes.model.Estudiante
import edu.ucne.registroestudiantes.domain.estudiantes.repository.EstudianteRepository
import javax.inject.Inject

class UpsertEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository,
    private val validateEstudianteUseCase: ValidateEstudianteUseCase
) {
    suspend operator fun invoke(estudiante: Estudiante): Result<Int> {
        return try {
            val validation = validateEstudianteUseCase(
                nombre = estudiante.nombres,
                email = estudiante.email,
                edad = estudiante.edad,
                currentEstudianteId = if (estudiante.estudianteId != 0) estudiante.estudianteId else null
            )

            if (!validation.isValid) {
                val errorMsg = validation.nombreError ?: validation.emailError ?: validation.edadError ?: "Error de validación"
                Result.failure(IllegalArgumentException(errorMsg))
            } else {
                val id = repository.upsert(estudiante)
                Result.success(id)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
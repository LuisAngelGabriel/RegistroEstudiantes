package edu.ucne.registroestudiantes.Domain.Usecase
import edu.ucne.registroestudiantes.Domain.Model.Estudiante
import edu.ucne.registroestudiantes.Domain.Repository.EstudianteRepository
import javax.inject.Inject

class UpsertEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(estudiante: Estudiante): Result<Int> {

        if (estudiante.nombres.isBlank()) {
            return Result.failure(IllegalArgumentException("El nombre no puede estar vacío"))
        }

        if (estudiante.nombres.length > 50) {
            return Result.failure(IllegalArgumentException("El nombre no puede tener más de 50 caracteres"))
        }

        if (estudiante.email.isBlank()) {
            return Result.failure(IllegalArgumentException("El email no puede estar vacío"))
        }

        if (!estudiante.email.contains("@")) {
            return Result.failure(IllegalArgumentException("Email no válido"))
        }

        if (estudiante.edad <= 0) {
            return Result.failure(IllegalArgumentException("La edad debe ser mayor a 0"))
        }

        return runCatching {
            repository.upsert(estudiante)
        }
    }
}
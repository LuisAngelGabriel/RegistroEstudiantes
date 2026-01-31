package edu.ucne.registroestudiantes.domain.asignaturas.usecase

import edu.ucne.registroestudiantes.domain.asignaturas.model.Asignaturas
import edu.ucne.registroestudiantes.domain.asignaturas.repository.AsignaturaRepository
import javax.inject.Inject

class UpsertAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(asignatura: Asignaturas): Result<Int> {

        if (asignatura.nombre.isBlank()) {
            return Result.failure(IllegalArgumentException("El nombre no puede estar vacío"))
        }

        if (asignatura.codigo <= 0) {
            return Result.failure(IllegalArgumentException("El código debe ser mayor a 0"))
        }

        if (asignatura.aula <= 0) {
            return Result.failure(IllegalArgumentException("El aula debe ser mayor a 0"))
        }

        if (asignatura.creditos <= 0) {
            return Result.failure(IllegalArgumentException("Los créditos deben ser mayores a 0"))
        }

        return runCatching {
            repository.upsert(asignatura)
        }
    }
}
package edu.ucne.registroestudiantes.domain.asignaturas.usecase

import edu.ucne.registroestudiantes.domain.asignaturas.model.Asignaturas
import edu.ucne.registroestudiantes.domain.asignaturas.repository.AsignaturaRepository
import javax.inject.Inject

class UpsertAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository,
    private val validateAsignaturaUseCase: ValidateAsignaturaUseCase
) {
    suspend operator fun invoke(asignatura: Asignaturas): Result<Int> {
        return try {
            val validation = validateAsignaturaUseCase(
                nombre = asignatura.nombre,
                codigo = asignatura.codigo,
                aula = asignatura.aula,
                creditos = asignatura.creditos,
                currentAsignaturaId = if (asignatura.asignaturaId != 0) asignatura.asignaturaId else null
            )

            if (!validation.isValid) {
                val errorMsg = validation.nombreError ?: validation.codigoError ?: validation.aulaError ?: validation.creditosError ?: "Error de validación"
                Result.failure(IllegalArgumentException(errorMsg))
            } else {
                val id = repository.upsert(asignatura)
                Result.success(id)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
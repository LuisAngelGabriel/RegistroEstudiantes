package edu.ucne.registroestudiantes.domain.asignaturas.usecase

import edu.ucne.registroestudiantes.domain.asignaturas.model.Asignaturas
import edu.ucne.registroestudiantes.domain.asignaturas.repository.AsignaturaRepository
import javax.inject.Inject

class GetAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(id: Int): Asignaturas? {
        if (id <= 0) throw IllegalArgumentException("El id debe ser mayor que 0")
        return repository.getAsignatura(id)
    }
}
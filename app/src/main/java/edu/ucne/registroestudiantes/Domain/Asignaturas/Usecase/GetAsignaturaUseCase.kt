package edu.ucne.registroestudiantes.Domain.Asignaturas.Usecase

import edu.ucne.registroestudiantes.Domain.Asignaturas.Model.Asignaturas
import edu.ucne.registroestudiantes.Domain.Asignaturas.Repository.AsignaturaRepository
import javax.inject.Inject

class GetAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(id: Int): Asignaturas? {
        if (id <= 0) throw IllegalArgumentException("El id debe ser mayor que 0")
        return repository.getAsignatura(id)
    }
}
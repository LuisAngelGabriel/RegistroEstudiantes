package edu.ucne.registroestudiantes.Domain.Asignaturas.Usecase

import edu.ucne.registroestudiantes.Domain.Asignaturas.Model.Asignaturas
import edu.ucne.registroestudiantes.Domain.Asignaturas.Repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    operator fun invoke(): Flow<List<Asignaturas>> {
        return repository.observeAsignaturas()
    }
}
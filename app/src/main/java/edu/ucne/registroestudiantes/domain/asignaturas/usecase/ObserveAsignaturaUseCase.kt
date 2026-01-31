package edu.ucne.registroestudiantes.domain.asignaturas.usecase

import edu.ucne.registroestudiantes.domain.asignaturas.model.Asignaturas
import edu.ucne.registroestudiantes.domain.asignaturas.repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    operator fun invoke(): Flow<List<Asignaturas>> {
        return repository.observeAsignaturas()
    }
}
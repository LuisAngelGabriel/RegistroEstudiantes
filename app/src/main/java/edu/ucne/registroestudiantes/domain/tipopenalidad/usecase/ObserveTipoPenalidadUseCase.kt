package edu.ucne.registroestudiantes.domain.tipopenalidad.usecase
import edu.ucne.registroestudiantes.domain.tipopenalidad.model.TipoPenalidad
import edu.ucne.registroestudiantes.domain.tipopenalidad.repository.TipoPenalidadRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    operator fun invoke(): Flow<List<TipoPenalidad>> {
        return repository.observeTiposPenalidades()
    }
}
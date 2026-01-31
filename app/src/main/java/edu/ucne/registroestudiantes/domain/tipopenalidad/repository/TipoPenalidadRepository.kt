package edu.ucne.registroestudiantes.domain.tipopenalidad.repository

import edu.ucne.registroestudiantes.domain.tipopenalidad.model.TipoPenalidad
import kotlinx.coroutines.flow.Flow

interface TipoPenalidadRepository {

    fun observeTiposPenalidades(): Flow<List<TipoPenalidad>>

    suspend fun getTipoPenalidad(id: Int): TipoPenalidad?

    suspend fun upsert(tipoPenalidad: TipoPenalidad): Int

    suspend fun delete(id: Int)

    suspend fun getTipoPenalidadByNombre(nombre: String): List<TipoPenalidad>
}
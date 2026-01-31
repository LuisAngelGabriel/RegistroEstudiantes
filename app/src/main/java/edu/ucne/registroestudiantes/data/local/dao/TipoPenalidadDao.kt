package edu.ucne.registroestudiantes.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.registroestudiantes.data.local.entities.TipoPenalidadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TipoPenalidadDao {

    @Query("SELECT * FROM TiposPenalidades ORDER BY tipoId DESC")
    fun observerAll(): Flow<List<TipoPenalidadEntity>>

    @Query("SELECT * FROM TiposPenalidades WHERE tipoId = :id")
    suspend fun getById(id: Int): TipoPenalidadEntity?

    @Upsert
    suspend fun upsert(tipoPenalidad: TipoPenalidadEntity): Long

    @Delete
    suspend fun delete(tipoPenalidad: TipoPenalidadEntity)

    @Query("DELETE FROM TiposPenalidades WHERE tipoId = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM TiposPenalidades WHERE nombre = :nombre")
    suspend fun getTipoPenalidadByNombre(nombre: String): List<TipoPenalidadEntity>
}
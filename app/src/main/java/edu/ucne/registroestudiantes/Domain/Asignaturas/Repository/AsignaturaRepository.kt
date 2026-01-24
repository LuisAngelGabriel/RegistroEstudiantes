package edu.ucne.registroestudiantes.Domain.Asignaturas.Repository

import edu.ucne.registroestudiantes.Domain.Asignaturas.Model.Asignaturas
import kotlinx.coroutines.flow.Flow

interface AsignaturaRepository {

    fun observeAsignaturas(): Flow<List<Asignaturas>>

    suspend fun getAsignatura(id: Int): Asignaturas?

    suspend fun upsert(asignatura: Asignaturas): Int

    suspend fun delete(id: Int)

    suspend fun getAsignaturasByNombre(nombre: String): List<Asignaturas>
}
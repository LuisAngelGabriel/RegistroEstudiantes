package edu.ucne.registroestudiantes.domain.asignaturas.repository

import edu.ucne.registroestudiantes.domain.asignaturas.model.Asignaturas
import kotlinx.coroutines.flow.Flow

interface AsignaturaRepository {

    fun observeAsignaturas(): Flow<List<Asignaturas>>

    suspend fun getAsignatura(id: Int): Asignaturas?

    suspend fun upsert(asignatura: Asignaturas): Int

    suspend fun delete(id: Int)

    suspend fun getAsignaturasByNombre(nombre: String): List<Asignaturas>
}
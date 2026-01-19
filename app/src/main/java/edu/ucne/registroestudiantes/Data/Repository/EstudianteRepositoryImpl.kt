package edu.ucne.registroestudiantes.Data.Repository

import edu.ucne.registroestudiantes.Data.Local.EstudianteDao
import edu.ucne.registroestudiantes.Data.Local.EstudianteEntity
import edu.ucne.registroestudiantes.Domain.Model.Estudiante
import edu.ucne.registroestudiantes.Domain.Repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EstudianteRepositoryImpl @Inject constructor(
    private val estudianteDao: EstudianteDao
) : EstudianteRepository {

    override fun observeEstudiante(): Flow<List<Estudiante>> {
        return estudianteDao.observerAll().map { entities ->
            entities.map { it.toEstudiante() }
        }
    }

    override suspend fun getEstudiante(id: Int): Estudiante? {
        return estudianteDao.getById(id)?.toEstudiante()
    }

    override suspend fun upsert(estudiante: Estudiante): Int {
        val entity = estudiante.toEntity()
        val result = estudianteDao.upsert(entity)
        return if (estudiante.estudianteId == 0) result.toInt() else estudiante.estudianteId
    }

    override suspend fun delete(id: Int) {
        estudianteDao.deleteById(id)
    }

    override suspend fun getEstudiantesByNombre(nombre: String): List<Estudiante> {
        return estudianteDao.getEstudiantesByNombre(nombre).map { it.toEstudiante() }
    }
}

fun EstudianteEntity.toEstudiante(): Estudiante {
    return Estudiante(
        estudianteId = estudianteId,
        nombres = nombres,
        email = email,
        edad = edad
    )
}

fun Estudiante.toEntity(): EstudianteEntity {
    return EstudianteEntity(
        estudianteId = estudianteId,
        nombres = nombres,
        email = email,
        edad = edad
    )
}
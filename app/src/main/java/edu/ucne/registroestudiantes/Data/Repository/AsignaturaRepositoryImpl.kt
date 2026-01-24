package edu.ucne.registroestudiantes.Data.Repository
import edu.ucne.registroestudiantes.Data.Local.AsignaturaDao
import edu.ucne.registroestudiantes.Data.Local.AsignaturaEntity
import edu.ucne.registroestudiantes.Domain.Asignaturas.Model.Asignaturas
import edu.ucne.registroestudiantes.Domain.Asignaturas.Repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AsignaturaRepositoryImpl @Inject constructor(
    private val asignaturaDao: AsignaturaDao
) : AsignaturaRepository {

    override fun observeAsignaturas(): Flow<List<Asignaturas>> {
        return asignaturaDao.observerAll().map { entities ->
            entities.map { it.toAsignaturas() }
        }
    }

    override suspend fun getAsignatura(id: Int): Asignaturas? {
        return asignaturaDao.getById(id)?.toAsignaturas()
    }

    override suspend fun upsert(asignatura: Asignaturas): Int {
        val entity = asignatura.toEntity()
        val result = asignaturaDao.upsert(entity)
        return if (asignatura.asignaturaId == 0) result.toInt() else asignatura.asignaturaId
    }

    override suspend fun delete(id: Int) {
        asignaturaDao.deleteById(id)
    }

    override suspend fun getAsignaturasByNombre(nombre: String): List<Asignaturas> {
        return asignaturaDao.getAsignaturasByNombre(nombre).map { it.toAsignaturas() }
    }
}

fun AsignaturaEntity.toAsignaturas(): Asignaturas {
    return Asignaturas(
        asignaturaId = asignaturaId,
        codigo = codigo,
        nombre = nombre,
        aula = aula,
        creditos = creditos
    )
}

fun Asignaturas.toEntity(): AsignaturaEntity {
    return AsignaturaEntity(
        asignaturaId = asignaturaId,
        codigo = codigo,
        nombre = nombre,
        aula = aula,
        creditos = creditos
    )
}
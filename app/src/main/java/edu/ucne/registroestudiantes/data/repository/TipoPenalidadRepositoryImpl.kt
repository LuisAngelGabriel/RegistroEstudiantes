package edu.ucne.registroestudiantes.data.repository

import edu.ucne.registroestudiantes.data.local.dao.TipoPenalidadDao
import edu.ucne.registroestudiantes.data.local.entities.TipoPenalidadEntity
import edu.ucne.registroestudiantes.domain.tipopenalidad.model.TipoPenalidad
import edu.ucne.registroestudiantes.domain.tipopenalidad.repository.TipoPenalidadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TipoPenalidadRepositoryImpl @Inject constructor(
    private val tipoPenalidadDao: TipoPenalidadDao
) : TipoPenalidadRepository {

    override fun observeTiposPenalidades(): Flow<List<TipoPenalidad>> {
        return tipoPenalidadDao.observerAll().map { entities ->
            entities.map { it.toTipoPenalidad() }
        }
    }

    override suspend fun getTipoPenalidad(id: Int): TipoPenalidad? {
        return tipoPenalidadDao.getById(id)?.toTipoPenalidad()
    }

    override suspend fun upsert(tipoPenalidad: TipoPenalidad): Int {
        val entity = tipoPenalidad.toEntity()
        val result = tipoPenalidadDao.upsert(entity)
        return if (tipoPenalidad.tipoId == 0) result.toInt() else tipoPenalidad.tipoId
    }

    override suspend fun delete(id: Int) {
        tipoPenalidadDao.deleteById(id)
    }

    override suspend fun getTipoPenalidadByNombre(nombre: String): List<TipoPenalidad> {
        return tipoPenalidadDao.getTipoPenalidadByNombre(nombre).map { it.toTipoPenalidad() }
    }
}

fun TipoPenalidadEntity.toTipoPenalidad(): TipoPenalidad {
    return TipoPenalidad(
        tipoId = tipoId,
        nombre = nombre,
        descripcion = descripcion,
        puntosDescuento = puntosDescuento
    )
}

fun TipoPenalidad.toEntity(): TipoPenalidadEntity {
    return TipoPenalidadEntity(
        tipoId = tipoId,
        nombre = nombre,
        descripcion = descripcion,
        puntosDescuento = puntosDescuento
    )
}
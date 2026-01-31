package edu.ucne.registroestudiantes.data.mapper
import edu.ucne.registroestudiantes.data.local.entities.TipoPenalidadEntity
import edu.ucne.registroestudiantes.domain.tipopenalidad.model.TipoPenalidad

fun TipoPenalidadEntity.toDomain(): TipoPenalidad =
    TipoPenalidad(
        tipoId = tipoId,
        nombre = nombre,
        descripcion = descripcion,
        puntosDescuento = puntosDescuento
    )

fun TipoPenalidad.toEntity(): TipoPenalidadEntity =
    TipoPenalidadEntity(
        tipoId = tipoId,
        nombre = nombre,
        descripcion = descripcion,
        puntosDescuento = puntosDescuento
    )
package edu.ucne.registroestudiantes.data.mapper
import edu.ucne.registroestudiantes.domain.asignaturas.model.Asignaturas
import edu.ucne.registroestudiantes.data.local.entities.AsignaturaEntity

fun AsignaturaEntity.toDomain(): Asignaturas =
    Asignaturas(
        asignaturaId = asignaturaId,
        codigo = codigo,
        nombre = nombre,
        aula = aula,
        creditos = creditos
    )

fun Asignaturas.toEntity(): AsignaturaEntity =
    AsignaturaEntity(
        asignaturaId = asignaturaId,
        codigo = codigo,
        nombre = nombre,
        aula = aula,
        creditos = creditos
    )
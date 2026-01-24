package edu.ucne.registroestudiantes.Data.Mapper
import edu.ucne.registroestudiantes.Domain.Asignaturas.Model.Asignaturas
import edu.ucne.registroestudiantes.Data.Local.AsignaturaEntity

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
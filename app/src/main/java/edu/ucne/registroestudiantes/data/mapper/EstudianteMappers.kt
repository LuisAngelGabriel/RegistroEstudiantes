package edu.ucne.registroestudiantes.data.mapper

import edu.ucne.registroestudiantes.domain.estudiantes.model.Estudiante
import edu.ucne.registroestudiantes.data.local.entities.EstudianteEntity

fun EstudianteEntity.toDomain():Estudiante=
    Estudiante(
        estudianteId = estudianteId,
        nombres = nombres,
        email = email,
        edad = edad
    )

fun Estudiante.toEntity():EstudianteEntity =
    EstudianteEntity(
        estudianteId = estudianteId,
        nombres = nombres,
        email = email,
        edad = edad
    )


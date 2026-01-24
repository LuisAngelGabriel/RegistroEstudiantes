package edu.ucne.registroestudiantes.Data.Mapper

import edu.ucne.registroestudiantes.Domain.Estudiantes.Model.Estudiante
import edu.ucne.registroestudiantes.Data.Local.EstudianteEntity

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


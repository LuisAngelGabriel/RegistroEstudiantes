package edu.ucne.registroestudiantes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registroestudiantes.data.local.AsignaturaDao
import edu.ucne.registroestudiantes.data.local.EstudianteDao
import edu.ucne.registroestudiantes.data.local.EstudianteEntity
import edu.ucne.registroestudiantes.data.local.AsignaturaEntity


@Database(
    entities = [
        EstudianteEntity::class,
        AsignaturaEntity::class
    ],
    version = 2,
    exportSchema = false)

abstract class EstudianteDB: RoomDatabase(){

    abstract fun estudianteDao(): EstudianteDao
    abstract fun asignaturaDao(): AsignaturaDao
}

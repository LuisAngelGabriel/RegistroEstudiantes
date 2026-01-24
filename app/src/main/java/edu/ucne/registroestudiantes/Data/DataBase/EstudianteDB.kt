package edu.ucne.registroestudiantes.Data.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registroestudiantes.Data.Local.AsignaturaDao
import edu.ucne.registroestudiantes.Data.Local.EstudianteDao
import edu.ucne.registroestudiantes.Data.Local.EstudianteEntity
import edu.ucne.registroestudiantes.Data.Local.AsignaturaEntity


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

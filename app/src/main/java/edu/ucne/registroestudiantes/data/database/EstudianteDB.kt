package edu.ucne.registroestudiantes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registroestudiantes.data.local.dao.AsignaturaDao
import edu.ucne.registroestudiantes.data.local.dao.EstudianteDao
import edu.ucne.registroestudiantes.data.local.dao.TipoPenalidadDao
import edu.ucne.registroestudiantes.data.local.entities.EstudianteEntity
import edu.ucne.registroestudiantes.data.local.entities.AsignaturaEntity
import edu.ucne.registroestudiantes.data.local.entities.TipoPenalidadEntity



@Database(
    entities = [
        EstudianteEntity::class,
        AsignaturaEntity::class,
        TipoPenalidadEntity::class
    ],
    version = 3,
    exportSchema = false)

abstract class EstudianteDB: RoomDatabase(){

    abstract fun estudianteDao(): EstudianteDao
    abstract fun asignaturaDao(): AsignaturaDao

    abstract fun TipoPenalidadDao(): TipoPenalidadDao
}

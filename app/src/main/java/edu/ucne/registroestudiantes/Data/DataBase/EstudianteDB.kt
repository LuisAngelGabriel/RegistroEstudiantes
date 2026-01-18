package edu.ucne.registroestudiantes.Data.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registroestudiantes.Data.Local.EstudianteDao
import edu.ucne.registroestudiantes.Data.Local.EstudianteEntity


@Database(entities =[EstudianteEntity::class],
    version = 1,
    exportSchema = false)

abstract class EstudianteDB: RoomDatabase(){

    abstract fun estudianteDao(): EstudianteDao
}

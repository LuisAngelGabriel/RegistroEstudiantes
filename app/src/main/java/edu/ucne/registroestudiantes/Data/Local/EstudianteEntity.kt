package edu.ucne.registroestudiantes.Data.Local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estudiantes")
data class EstudianteEntity(
    @PrimaryKey(autoGenerate = true)
    val estudianteId: Int = 0,
    val nombres: String,
    val email: String,
    val edad: Int
)
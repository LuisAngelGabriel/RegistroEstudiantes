package edu.ucne.registroestudiantes.Data.Local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asignaturas")
data class AsignaturaEntity (
    @PrimaryKey(autoGenerate = true)
    val asignaturaId: Int = 0,
    val codigo: Int,
    val nombre: String,
    val aula: Int,
    val creditos: Int
)
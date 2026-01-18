package edu.ucne.registroestudiantes.Data.Local
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface EstudianteDao  {

    @Query("SELECT * FROM estudiantes ORDER BY estudianteId DESC")
    fun observerAll():Flow<List<EstudianteEntity>>

    @Query("SELECT * FROM estudiantes WHERE estudianteId = :id")
    suspend fun  getById(id: Int): EstudianteEntity?

    @Upsert
    suspend fun upsert(estudiante: EstudianteEntity)

    @Delete
    suspend fun delete(estudiante: EstudianteEntity)

    @Query("DELETE FROM estudiantes WHERE estudianteId = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM estudiantes WHERE nombres = :nombre")
    suspend fun getEstudiantesByNombre(nombre: String): List<EstudianteEntity>
}
    package edu.ucne.registroestudiantes.Data.Local

    import androidx.room.Dao
    import androidx.room.Delete
    import androidx.room.Query
    import androidx.room.Upsert
    import kotlinx.coroutines.flow.Flow

    @Dao
    interface AsignaturaDao {

        @Query("SELECT * FROM asignaturas ORDER BY asignaturaId DESC")
        fun observerAll(): Flow<List<AsignaturaEntity>>

        @Query("SELECT * FROM asignaturas WHERE asignaturaId = :id")
        suspend fun getById(id: Int): AsignaturaEntity?

        @Upsert
        suspend fun upsert(asignatura: AsignaturaEntity): Long

        @Delete
        suspend fun delete(asignatura: AsignaturaEntity)

        @Query("DELETE FROM asignaturas WHERE asignaturaId = :id")
        suspend fun deleteById(id: Int)

        @Query("SELECT * FROM asignaturas WHERE nombre = :nombre")
        suspend fun getAsignaturasByNombre(nombre: String): List<AsignaturaEntity>
    }
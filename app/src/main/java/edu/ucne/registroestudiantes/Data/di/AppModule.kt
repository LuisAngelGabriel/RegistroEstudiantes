package edu.ucne.registroestudiantes.Data.di
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registroestudiantes.Data.DataBase.EstudianteDB
import edu.ucne.registroestudiantes.Data.Local.AsignaturaDao
import edu.ucne.registroestudiantes.Data.Local.EstudianteDao
import edu.ucne.registroestudiantes.Data.Repository.AsignaturaRepositoryImpl
import edu.ucne.registroestudiantes.Data.Repository.EstudianteRepositoryImpl
import edu.ucne.registroestudiantes.Domain.Asignaturas.Repository.AsignaturaRepository
import edu.ucne.registroestudiantes.Domain.Estudiantes.Repository.EstudianteRepository
import jakarta.inject.Singleton


@InstallIn(
    SingletonComponent::class)
@Module

object AppModule {
    @Provides
    @Singleton
    fun provideEstudianteDB(@ApplicationContext appContext: Context) : EstudianteDB{

        return Room.databaseBuilder(
            appContext,
            EstudianteDB::class.java,
            "EstudianteDB"
        ).fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideEstudianteDao(estudianteDB: EstudianteDB) : EstudianteDao{
        return estudianteDB.estudianteDao()
    }

    @Provides
    @Singleton
    fun provideEstudianteRepositoryImpl(estudianteDao: EstudianteDao) : EstudianteRepositoryImpl{
        return EstudianteRepositoryImpl(estudianteDao)
    }

    @Provides
    @Singleton
    fun provideEstudianteRepository(impl: EstudianteRepositoryImpl): EstudianteRepository {
        return impl
    }

    @Provides
    @Singleton
    fun provideAsignaturaDao(estudianteDB: EstudianteDB): AsignaturaDao {
        return estudianteDB.asignaturaDao()
    }

    @Provides
    @Singleton
    fun provideAsignaturaRepositoryImpl(asignaturaDao: AsignaturaDao): AsignaturaRepositoryImpl {
        return AsignaturaRepositoryImpl(asignaturaDao)
    }

    @Provides
    @Singleton
    fun provideAsignaturaRepository(impl: AsignaturaRepositoryImpl): AsignaturaRepository {
        return impl
    }
}
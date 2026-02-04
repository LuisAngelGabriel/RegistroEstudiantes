package edu.ucne.registroestudiantes.di
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registroestudiantes.data.database.EstudianteDB
import edu.ucne.registroestudiantes.data.local.dao.AsignaturaDao
import edu.ucne.registroestudiantes.data.local.dao.EstudianteDao
import edu.ucne.registroestudiantes.data.local.dao.TipoPenalidadDao
import edu.ucne.registroestudiantes.data.repository.AsignaturaRepositoryImpl
import edu.ucne.registroestudiantes.data.repository.EstudianteRepositoryImpl
import edu.ucne.registroestudiantes.data.repository.TipoPenalidadRepositoryImpl
import edu.ucne.registroestudiantes.domain.asignaturas.repository.AsignaturaRepository
import edu.ucne.registroestudiantes.domain.estudiantes.repository.EstudianteRepository
import edu.ucne.registroestudiantes.domain.tipopenalidad.repository.TipoPenalidadRepository
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

    @Provides
    @Singleton
    fun provideTipoPenalidadDao(estudianteDB: EstudianteDB): TipoPenalidadDao {
        return estudianteDB.tipoPenalidadDao()
    }

    @Provides
    @Singleton
    fun provideTipoPenalidadRepositoryImpl(tipoPenalidadDao: TipoPenalidadDao): TipoPenalidadRepositoryImpl {
        return TipoPenalidadRepositoryImpl(tipoPenalidadDao)
    }

    @Provides
    @Singleton
    fun provideTipoPenalidadRepository(impl: TipoPenalidadRepositoryImpl): TipoPenalidadRepository {
        return impl
    }

}
package com.tinkoff.aljokes.di

import android.content.Context
import androidx.room.Room
import com.tinkoff.aljokes.data.datasource.local.AppDatabase
import com.tinkoff.aljokes.data.datasource.remote.CatRetrofitInstance
import com.tinkoff.aljokes.data.datasource.remote.RetrofitInstance
import com.tinkoff.aljokes.data.repository.CatImageRepositoryImpl
import com.tinkoff.aljokes.data.repository.JokesRepositoryImpl
import com.tinkoff.aljokes.domain.repository.CatImageRepository
import com.tinkoff.aljokes.domain.repository.JokesRepository
import com.tinkoff.aljokes.domain.usecase.AddInitialJokesUseCase
import com.tinkoff.aljokes.domain.usecase.AddJokesUseCase
import com.tinkoff.aljokes.domain.usecase.DeleteAllJokesUseCase
import com.tinkoff.aljokes.domain.usecase.LoadCatImageUseCase
import com.tinkoff.aljokes.domain.usecase.LoadJokesUseCase

object AppModule {

    private var appDatabase: AppDatabase? = null
    private val api = RetrofitInstance.api
    private val catApi = CatRetrofitInstance.api

    fun createDatabase(context: Context): AppDatabase {
        if (appDatabase == null) {
            appDatabase = Room
                .databaseBuilder(context, AppDatabase::class.java, "jokes_db")
                .fallbackToDestructiveMigration()
                .build()
        }
        return requireNotNull(appDatabase) { "AppDatabase is not initialized" }
        // Если appDatabase по какой-то причине всё-таки null, будет выброшено исключение с осмысленным сообщением
    }

    fun provideJokesRepository(context: Context): JokesRepository {
        val appDatabase = createDatabase(context)
        return JokesRepositoryImpl(appDatabase.jokesDao(), api)
    }

    fun provideCatImageRepository(): CatImageRepository {
        return CatImageRepositoryImpl(catApi)
    }

    fun loadJokesUseCase(jokesRepository: JokesRepository): LoadJokesUseCase {
        return LoadJokesUseCase(jokesRepository)
    }

    fun deleteJokesUseCase(jokesRepository: JokesRepository): DeleteAllJokesUseCase {
        return DeleteAllJokesUseCase(jokesRepository)
    }

    fun addJokesUseCase(jokesRepository: JokesRepository): AddJokesUseCase {
        return AddJokesUseCase(jokesRepository)
    }

    fun loadCatImageUseCase(catImageRepository: CatImageRepository): LoadCatImageUseCase {
        return LoadCatImageUseCase(catImageRepository)
    }

    fun addInitialJokesUseCase(jokesRepository: JokesRepository) : AddInitialJokesUseCase {
        return AddInitialJokesUseCase(jokesRepository)
    }
}
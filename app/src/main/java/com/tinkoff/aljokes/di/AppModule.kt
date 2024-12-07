package com.tinkoff.aljokes.di

import android.content.Context
import androidx.room.Room
import com.tinkoff.aljokes.data.datasource.local.AppDatabase
import com.tinkoff.aljokes.data.repository.JokesRepositoryImpl
import com.tinkoff.aljokes.domain.repository.JokesRepository
import com.tinkoff.aljokes.domain.usecase.AddJokesUseCase
import com.tinkoff.aljokes.domain.usecase.DeleteJokesUseCase
import com.tinkoff.aljokes.domain.usecase.LoadCatImageUseCase
import com.tinkoff.aljokes.domain.usecase.LoadJokesUseCase

object AppModule {

    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "jokes_db"
        ).fallbackToDestructiveMigration().build()
    }

    fun provideJokesRepository(database: AppDatabase): JokesRepositoryImpl {
        return JokesRepositoryImpl(database.jokesDao())
    }

    fun provideUseCases(repository: JokesRepository): UseCases {
        return UseCases(
            loadJokesUseCase = LoadJokesUseCase(repository),
            loadCatImageUseCase = LoadCatImageUseCase(repository),
            deleteJokesUseCase = DeleteJokesUseCase(repository),
            addJokesUseCase = AddJokesUseCase(repository)
        )
    }
}
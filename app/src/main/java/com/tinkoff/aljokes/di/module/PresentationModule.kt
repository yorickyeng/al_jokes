package com.tinkoff.aljokes.di.module

import com.tinkoff.aljokes.data.datasource.local.AppDatabase
import com.tinkoff.aljokes.domain.usecase.AddJokesUseCase
import com.tinkoff.aljokes.domain.usecase.DeleteAllJokesUseCase
import com.tinkoff.aljokes.domain.usecase.LoadCatImageUseCase
import com.tinkoff.aljokes.domain.usecase.LoadJokesUseCase
import com.tinkoff.aljokes.presentation.add_jokes_screen.AddJokesViewModelFactory
import com.tinkoff.aljokes.presentation.kitten_screen.KittenViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresentationModule {

    @Provides
    @Singleton
    fun provideKittenViewModelFactory(
        loadJokesUseCase: LoadJokesUseCase,
        deleteAllJokesUseCase: DeleteAllJokesUseCase,
        loadCatImageUseCase: LoadCatImageUseCase,
        database: AppDatabase
    ): KittenViewModelFactory {
        return KittenViewModelFactory(
            loadJokesUseCase,
            deleteAllJokesUseCase,
            loadCatImageUseCase,
            database
        )
    }

    @Provides
    @Singleton
    fun provideAddJokesViewModelFactory(
        addJokesUseCase: AddJokesUseCase
    ): AddJokesViewModelFactory {
        return AddJokesViewModelFactory(
            addJokesUseCase
        )
    }
}
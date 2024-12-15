package com.tinkoff.aljokes.di.module

import com.tinkoff.aljokes.domain.repository.CatImageRepository
import com.tinkoff.aljokes.domain.repository.JokesRepository
import com.tinkoff.aljokes.domain.usecase.AddInitialJokesUseCase
import com.tinkoff.aljokes.domain.usecase.AddJokesUseCase
import com.tinkoff.aljokes.domain.usecase.DeleteAllJokesUseCase
import com.tinkoff.aljokes.domain.usecase.LoadCatImageUseCase
import com.tinkoff.aljokes.domain.usecase.LoadJokesUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun loadJokesUseCase(repository: JokesRepository): LoadJokesUseCase {
        return LoadJokesUseCase(repository)
    }

    @Provides
    fun deleteJokesUseCase(repository: JokesRepository): DeleteAllJokesUseCase {
        return DeleteAllJokesUseCase(repository)
    }

    @Provides
    fun addJokesUseCase(repository: JokesRepository): AddJokesUseCase {
        return AddJokesUseCase(repository)
    }

    @Provides
    fun loadCatImageUseCase(repository: CatImageRepository): LoadCatImageUseCase {
        return LoadCatImageUseCase(repository)
    }

    @Provides
    fun addInitialJokesUseCase(repository: JokesRepository): AddInitialJokesUseCase {
        return AddInitialJokesUseCase(repository)
    }
}
package com.tinkoff.aljokes.di

import com.tinkoff.aljokes.domain.usecase.AddJokesUseCase
import com.tinkoff.aljokes.domain.usecase.DeleteJokesUseCase
import com.tinkoff.aljokes.domain.usecase.LoadCatImageUseCase
import com.tinkoff.aljokes.domain.usecase.LoadJokesUseCase

data class UseCases(
    val loadJokesUseCase: LoadJokesUseCase,
    val loadCatImageUseCase: LoadCatImageUseCase,
    val deleteJokesUseCase: DeleteJokesUseCase,
    val addJokesUseCase: AddJokesUseCase
)
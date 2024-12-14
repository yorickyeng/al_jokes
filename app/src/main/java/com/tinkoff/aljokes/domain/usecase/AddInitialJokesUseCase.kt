package com.tinkoff.aljokes.domain.usecase

import com.tinkoff.aljokes.domain.repository.JokesRepository
import com.tinkoff.aljokes.domain.repository.StaticJokesDataSource

class AddInitialJokesUseCase(
    private val jokesRepository: JokesRepository
) {
    suspend operator fun invoke() {
        if (jokesRepository.getJokes().value.isNullOrEmpty()) {
            val staticJokes = StaticJokesDataSource.initialJokes
            jokesRepository.saveJokesToLocal(staticJokes)
        }
    }
}
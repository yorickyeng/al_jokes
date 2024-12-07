package com.tinkoff.aljokes.domain.usecase

import com.tinkoff.aljokes.domain.entity.Jokes
import com.tinkoff.aljokes.domain.repository.JokesRepository

class DeleteJokesUseCase(
    private val jokesRepository: JokesRepository
) {
    suspend operator fun invoke(jokes: List<Jokes>) {
        jokesRepository.deleteJokesFromLocal(jokes)
    }
}
package com.tinkoff.aljokes.domain.usecase

import com.tinkoff.aljokes.domain.entity.Joke
import com.tinkoff.aljokes.domain.repository.JokesRepository

class AddJokesUseCase(
    private val jokesRepository: JokesRepository
) {
    suspend operator fun invoke(jokes: List<Joke>) {
        jokesRepository.saveJokesToLocal(jokes)
    }
}
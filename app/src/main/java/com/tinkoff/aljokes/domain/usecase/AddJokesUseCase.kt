package com.tinkoff.aljokes.domain.usecase

import com.tinkoff.aljokes.domain.entity.Jokes
import com.tinkoff.aljokes.domain.repository.JokesRepository

class AddJokesUseCase(
    private val jokesRepository: JokesRepository
) {
    suspend operator fun invoke(joke: Jokes) {
        jokesRepository.saveOneJokeToLocal(joke)
    }
}
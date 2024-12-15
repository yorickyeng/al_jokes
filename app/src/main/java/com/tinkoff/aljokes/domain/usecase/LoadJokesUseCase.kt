package com.tinkoff.aljokes.domain.usecase

import com.tinkoff.aljokes.domain.repository.JokesRepository
import javax.inject.Inject

class LoadJokesUseCase @Inject constructor(
    private val jokesRepository: JokesRepository
) {
    suspend operator fun invoke(
        count: Int, dark: Boolean
    ) {
        val jokes = jokesRepository.fetchJokes(count, dark)
        jokesRepository.saveJokesToLocal(jokes)
    }
}

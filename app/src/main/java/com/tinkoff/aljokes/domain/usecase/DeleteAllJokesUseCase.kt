package com.tinkoff.aljokes.domain.usecase

import com.tinkoff.aljokes.domain.repository.JokesRepository

class DeleteAllJokesUseCase(
    private val jokesRepository: JokesRepository
) {
    suspend operator fun invoke() {
        jokesRepository.deleteAllJokesFromLocal()
    }
}
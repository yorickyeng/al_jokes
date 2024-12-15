package com.tinkoff.aljokes.domain.usecase

import com.tinkoff.aljokes.domain.repository.JokesRepository
import javax.inject.Inject

class DeleteAllJokesUseCase @Inject constructor(
    private val jokesRepository: JokesRepository
) {
    suspend operator fun invoke() {
        jokesRepository.deleteAllJokesFromLocal()
    }
}
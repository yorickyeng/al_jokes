package com.tinkoff.aljokes.domain.usecase

import com.tinkoff.aljokes.domain.repository.JokesRepository

class LoadCatImageUseCase(
    private val jokesRepository: JokesRepository
) {
    suspend operator fun invoke(): String {
        return jokesRepository.fetchCatImageFromApi()
    }
}
package com.tinkoff.aljokes.domain.usecase

import com.tinkoff.aljokes.domain.repository.CatImageRepository

class LoadCatImageUseCase(
    private val catImageRepository: CatImageRepository
) {
    suspend operator fun invoke(): String? {
        return catImageRepository.fetchCatImageUrl()
    }
}
package com.tinkoff.aljokes.domain.usecase

import com.tinkoff.aljokes.domain.repository.CatImageRepository
import javax.inject.Inject

class LoadCatImageUseCase @Inject constructor(
    private val catImageRepository: CatImageRepository
) {
    suspend operator fun invoke(): String? {
        return catImageRepository.fetchCatImageUrl()
    }
}
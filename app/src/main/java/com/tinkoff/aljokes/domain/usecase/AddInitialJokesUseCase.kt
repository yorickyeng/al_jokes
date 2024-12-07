package com.tinkoff.aljokes.domain.usecase

import com.tinkoff.aljokes.data.datasource.local.JokesDao
import com.tinkoff.aljokes.domain.repository.JokesRepository
import com.tinkoff.aljokes.domain.repository.StaticJokesDataSource

class AddInitialJokesUseCase(
    private val jokesRepository: JokesRepository,
    private val jokesDao: JokesDao
) {
    suspend operator fun invoke() {
        if (jokesDao.getJokes().value.isNullOrEmpty()) {
            val staticJokes = StaticJokesDataSource.initialJokes
            jokesRepository.saveJokesToLocal(staticJokes)
        }
    }
}
package com.tinkoff.aljokes.data.repository

import androidx.lifecycle.LiveData
import com.tinkoff.aljokes.data.datasource.local.JokesDao
import com.tinkoff.aljokes.data.datasource.remote.ApiService
import com.tinkoff.aljokes.domain.entity.Joke
import com.tinkoff.aljokes.domain.repository.JokesRepository

class JokesRepositoryImpl(
    private val jokesDao: JokesDao,
    private val api: ApiService
) : JokesRepository {

    override suspend fun fetchJokes(count: Int, dark: Boolean): List<Joke> {
        return if (dark) {
            api.getDarkJoke(count).jokes
        } else {
            api.getAnyJoke(count).jokes
        }
    }

    override suspend fun saveJokesToLocal(jokes: List<Joke>) {
        jokesDao.insertJokes(jokes)
    }

    override suspend fun deleteAllJokesFromLocal() {
        jokesDao.deleteAllJokes()
    }

    override suspend fun getJokes(): LiveData<List<Joke>> {
        return jokesDao.getJokes()
    }
}

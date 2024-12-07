package com.tinkoff.aljokes.data.repository

import com.tinkoff.aljokes.data.datasource.local.JokesDao
import com.tinkoff.aljokes.data.datasource.remote.CatRetrofitInstance
import com.tinkoff.aljokes.data.datasource.remote.RetrofitInstance
import com.tinkoff.aljokes.domain.entity.Jokes
import com.tinkoff.aljokes.domain.repository.JokesRepository

class JokesRepositoryImpl(
    private val jokesDao: JokesDao
) : JokesRepository {

    override suspend fun fetchJokesFromApi(count: Int, dark: Boolean): List<Jokes> {
        return if (dark) {
            RetrofitInstance.api.getDarkJoke(count).jokes
        } else {
            RetrofitInstance.api.getAnyJoke(count).jokes
        }
    }

    override suspend fun saveJokesToLocal(jokes: List<Jokes>) {
        for (joke in jokes) {
            jokesDao.insertJoke(joke)
        }
    }

    override suspend fun saveOneJokeToLocal(joke: Jokes) {
        jokesDao.insertJoke(joke)
    }

    override suspend fun deleteJokesFromLocal(jokes: List<Jokes>) {
        jokesDao.deleteAllJokes()
    }

    override suspend fun fetchCatImageFromApi(): String {
        val catUrl = CatRetrofitInstance.api.getCat().firstOrNull()?.url
        return catUrl ?: ""
    }
}

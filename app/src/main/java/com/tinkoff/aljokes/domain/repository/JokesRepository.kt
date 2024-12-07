package com.tinkoff.aljokes.domain.repository

import com.tinkoff.aljokes.domain.entity.Jokes

interface JokesRepository {
    suspend fun fetchJokesFromApi(count: Int, dark: Boolean): List<Jokes>
    suspend fun saveJokesToLocal(jokes: List<Jokes>)
    suspend fun saveOneJokeToLocal(joke: Jokes)
    suspend fun deleteJokesFromLocal(jokes: List<Jokes>)
    suspend fun fetchCatImageFromApi(): String
}
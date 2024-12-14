package com.tinkoff.aljokes.domain.repository

import androidx.lifecycle.LiveData
import com.tinkoff.aljokes.domain.entity.Joke

interface JokesRepository {
    suspend fun fetchJokes(count: Int, dark: Boolean): List<Joke>
    suspend fun saveJokesToLocal(jokes: List<Joke>)
    suspend fun deleteAllJokesFromLocal()
    suspend fun getJokes(): LiveData<List<Joke>>
}
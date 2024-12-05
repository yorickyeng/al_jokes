package com.tinkoff.aljokes.data.datasource.remote

import com.tinkoff.aljokes.data.datasource.local.JokesDao

suspend fun loadJokesFromApi(
    count: Int,
    dark: Boolean,
    jokesDao: JokesDao,
    setLoading: (Boolean) -> Unit
) {
    try {
        setLoading(true)
        val response = if (dark) {
            RetrofitInstance.api.getDarkJoke(count)
        } else {
            RetrofitInstance.api.getAnyJoke(count)
        }
        for (newJoke in response.jokes) {
            jokesDao.insertJoke(newJoke)
        }
    } catch (e: Exception) {
        println("Error: ${e.message}")
    } finally {
        setLoading(false)
    }
}
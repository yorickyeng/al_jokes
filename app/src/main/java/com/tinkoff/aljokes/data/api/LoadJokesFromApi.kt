package com.tinkoff.aljokes.data.api

import com.tinkoff.aljokes.data.db.JokesDao

suspend fun loadJokesFromApi(count: Int, dark: Boolean, jokesDao: JokesDao) {
    try {
        if (dark) {
            val response = RetrofitInstance.api.getDarkJoke(count)
            for (newJoke in response.jokes) {
                jokesDao.insertJoke(newJoke)
            }
        } else {
            val response = RetrofitInstance.api.getAnyJoke(count)
            for (newJoke in response.jokes) {
                jokesDao.insertJoke(newJoke)
            }
        }
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}
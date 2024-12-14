package com.tinkoff.aljokes.domain.repository

interface CatImageRepository {
    suspend fun fetchCatImageUrl(): String?
}
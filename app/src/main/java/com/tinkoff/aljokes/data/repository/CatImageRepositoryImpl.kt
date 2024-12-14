package com.tinkoff.aljokes.data.repository

import com.tinkoff.aljokes.data.datasource.remote.CatApiService
import com.tinkoff.aljokes.domain.repository.CatImageRepository

class CatImageRepositoryImpl(
    private val api: CatApiService
) : CatImageRepository {
    override suspend fun fetchCatImageUrl(): String? {
        return api.getCat().firstOrNull()?.url
    }
}
package com.tinkoff.aljokes.data.api

import retrofit2.http.GET

interface CatApiService {
    @GET("/v1/images/search")
    suspend fun getCat(): List<CatResponse>
}
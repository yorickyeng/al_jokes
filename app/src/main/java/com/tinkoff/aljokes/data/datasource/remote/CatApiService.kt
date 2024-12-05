package com.tinkoff.aljokes.data.datasource.remote

import retrofit2.http.GET

interface CatApiService {
    @GET("/v1/images/search")
    suspend fun getCat(): List<CatResponse>
}
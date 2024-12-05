package com.tinkoff.aljokes.data.api

import com.google.gson.annotations.SerializedName
import com.tinkoff.aljokes.data.Jokes

data class JokeResponse(
    @SerializedName("jokes")
    val jokes: List<Jokes>
)
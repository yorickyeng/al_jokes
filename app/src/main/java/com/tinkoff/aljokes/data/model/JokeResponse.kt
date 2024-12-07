package com.tinkoff.aljokes.data.model

import com.google.gson.annotations.SerializedName
import com.tinkoff.aljokes.domain.entity.Jokes

data class JokeResponse(
    @SerializedName("jokes")
    val jokes: List<Jokes>
)
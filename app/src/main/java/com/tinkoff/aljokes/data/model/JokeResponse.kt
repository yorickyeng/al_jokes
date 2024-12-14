package com.tinkoff.aljokes.data.model

import com.google.gson.annotations.SerializedName
import com.tinkoff.aljokes.domain.entity.Joke

data class JokeResponse(
    @SerializedName("jokes")
    val jokes: List<Joke>
)
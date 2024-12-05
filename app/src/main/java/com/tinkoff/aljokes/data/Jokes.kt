package com.tinkoff.aljokes.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "jokes")
data class Jokes(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Int,
    @SerializedName("setup")
    val setup: String,
    @SerializedName("delivery")
    val delivery: String,
)
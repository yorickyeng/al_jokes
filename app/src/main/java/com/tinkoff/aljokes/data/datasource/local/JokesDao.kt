package com.tinkoff.aljokes.data.datasource.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tinkoff.aljokes.domain.entity.Jokes

@Dao
interface JokesDao {
    @Query("SElECT * FROM jokes")
    fun getJokes(): LiveData<List<Jokes>>

    @Query("DELETE FROM jokes")
    suspend fun deleteAllJokes()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(joke: Jokes)
}
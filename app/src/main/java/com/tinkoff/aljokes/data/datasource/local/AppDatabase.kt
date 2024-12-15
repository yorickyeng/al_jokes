package com.tinkoff.aljokes.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tinkoff.aljokes.domain.entity.Joke

@Database(entities = [Joke::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jokesDao(): JokesDao
}
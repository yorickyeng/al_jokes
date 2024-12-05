package com.tinkoff.aljokes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tinkoff.aljokes.data.Jokes

@Database(entities = [Jokes::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jokesDao(): JokesDao
}
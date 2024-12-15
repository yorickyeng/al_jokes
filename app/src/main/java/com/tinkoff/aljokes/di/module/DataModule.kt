package com.tinkoff.aljokes.di.module

import android.content.Context
import androidx.room.Room
import com.tinkoff.aljokes.data.datasource.local.AppDatabase
import com.tinkoff.aljokes.data.datasource.local.JokesDao
import com.tinkoff.aljokes.data.datasource.remote.ApiService
import com.tinkoff.aljokes.data.datasource.remote.CatApiService
import com.tinkoff.aljokes.data.repository.CatImageRepositoryImpl
import com.tinkoff.aljokes.data.repository.JokesRepositoryImpl
import com.tinkoff.aljokes.domain.repository.CatImageRepository
import com.tinkoff.aljokes.domain.repository.JokesRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCatRetrofitApiService(): CatApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_CAT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideJokesDao(database: AppDatabase): JokesDao {
        return database.jokesDao()
    }

    @Provides
    @Singleton
    fun provideJokesRepository(jokesDao: JokesDao, api: ApiService): JokesRepository {
        return JokesRepositoryImpl(jokesDao, api)
    }

    @Provides
    @Singleton
    fun provideCatImageRepository(api: CatApiService): CatImageRepository {
        return CatImageRepositoryImpl(api)
    }

    private companion object {
        private const val DB_NAME = "jokes_db"
        private const val BASE_URL = "https://v2.jokeapi.dev/"
        private const val BASE_CAT_URL = "https://api.thecatapi.com/"
    }
}
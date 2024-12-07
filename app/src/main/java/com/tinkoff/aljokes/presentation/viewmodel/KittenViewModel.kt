package com.tinkoff.aljokes.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinkoff.aljokes.domain.entity.Jokes
import com.tinkoff.aljokes.domain.usecase.DeleteJokesUseCase
import com.tinkoff.aljokes.domain.usecase.LoadCatImageUseCase
import com.tinkoff.aljokes.domain.usecase.LoadJokesUseCase
import kotlinx.coroutines.launch


class KittenViewModel(
    private val loadJokesUseCase: LoadJokesUseCase,
    private val loadCatImageUseCase: LoadCatImageUseCase,
    private val deleteJokesUseCase: DeleteJokesUseCase
) : ViewModel() {

    var isLoading by mutableStateOf(false)
    var reloadCat by mutableStateOf(false)
    var catUrl by mutableStateOf("")

    fun loadJokes(dark: Boolean) {
        viewModelScope.launch {
            try {
                isLoading = true
                loadJokesUseCase(10, dark)
            } catch (e: Exception) {
                println("Error: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun deleteJokes(jokes: List<Jokes>) {
        viewModelScope.launch {
            deleteJokesUseCase(jokes)
        }
    }

    fun getCatUrl() {
        viewModelScope.launch {
            try {
                catUrl = loadCatImageUseCase()
            } catch (e: Exception) {
                println("Error fetching cat image: ${e.message}")
            }
        }
    }
}
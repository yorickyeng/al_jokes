package com.tinkoff.aljokes.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinkoff.aljokes.domain.entity.Jokes
import com.tinkoff.aljokes.domain.usecase.AddJokesUseCase
import kotlinx.coroutines.launch

class AddJokesViewModel(
    private val addJokesUseCase: AddJokesUseCase
) : ViewModel() {

    var errorMessage by mutableStateOf("")
    private var isLoading by mutableStateOf(false)

    var id by mutableStateOf("")
    var setup by mutableStateOf("")
    var delivery by mutableStateOf("")

    fun addJoke(joke: Jokes) {
        viewModelScope.launch {
            isLoading = true
            try {
                addJokesUseCase(joke)
                errorMessage = ""
            } catch (e: Exception) {
                errorMessage = "Failed to add joke: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun setError(message: String) {
        errorMessage = message
    }

    fun clearFields() {
        id = ""
        setup = ""
        delivery = ""
    }
}

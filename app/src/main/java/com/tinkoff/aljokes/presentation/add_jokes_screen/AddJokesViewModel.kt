package com.tinkoff.aljokes.presentation.add_jokes_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.tinkoff.aljokes.domain.entity.Joke
import com.tinkoff.aljokes.domain.usecase.AddJokesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddJokesViewModel(
    private val navController: NavController,
    private val addJokesUseCase: AddJokesUseCase
) : ViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    val id = mutableStateOf("")
    val setup = mutableStateOf("")
    val delivery = mutableStateOf("")

    private fun addJoke(jokes: List<Joke>) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                addJokesUseCase(jokes)
                _errorMessage.value = ""
            } catch (e: Exception) {
                _errorMessage.value = "Failed to add joke: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun clearFields() {
        id.value = ""
        setup.value = ""
        delivery.value = ""
    }

    fun onAddClick() {
        if (setup.value.isNotBlank() && delivery.value.isNotBlank() && id.value.toIntOrNull() != null) {
            val newJokeList =
                listOf(
                    Joke(
                        id.value.toInt(),
                        setup.value,
                        delivery.value
                    )
                )
            addJoke(newJokeList)
            clearFields()
            navController.popBackStack()
        } else {
            _errorMessage.value = "Invalid input. Please check your fields."
        }
    }

    fun onIdChange(newId: String) {
        id.value = newId
    }

    fun onSetupChange(newSetup: String) {
        setup.value = newSetup
    }

    fun onDeliveryChange(newDelivery: String) {
        delivery.value = newDelivery
    }
}

package com.tinkoff.aljokes.presentation.add_jokes_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tinkoff.aljokes.domain.usecase.AddJokesUseCase
import javax.inject.Inject

class AddJokesViewModelFactory @Inject constructor(
    private val addJokesUseCase: AddJokesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddJokesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddJokesViewModel(
                addJokesUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

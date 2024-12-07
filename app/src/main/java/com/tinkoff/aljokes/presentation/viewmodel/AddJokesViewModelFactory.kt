package com.tinkoff.aljokes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tinkoff.aljokes.domain.usecase.AddJokesUseCase

class AddJokesViewModelFactory(
    private val addJokesUseCase: AddJokesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddJokesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return AddJokesViewModel(addJokesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

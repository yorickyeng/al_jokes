package com.tinkoff.aljokes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tinkoff.aljokes.domain.usecase.DeleteJokesUseCase
import com.tinkoff.aljokes.domain.usecase.LoadCatImageUseCase
import com.tinkoff.aljokes.domain.usecase.LoadJokesUseCase

class KittenViewModelFactory(
    private val loadJokesUseCase: LoadJokesUseCase,
    private val loadCatImageUseCase: LoadCatImageUseCase,
    private val deleteJokesUseCase: DeleteJokesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KittenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return KittenViewModel(
                loadJokesUseCase, loadCatImageUseCase, deleteJokesUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
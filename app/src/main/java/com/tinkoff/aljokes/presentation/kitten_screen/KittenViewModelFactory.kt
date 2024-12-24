package com.tinkoff.aljokes.presentation.kitten_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tinkoff.aljokes.data.datasource.local.AppDatabase
import com.tinkoff.aljokes.domain.usecase.DeleteAllJokesUseCase
import com.tinkoff.aljokes.domain.usecase.LoadCatImageUseCase
import com.tinkoff.aljokes.domain.usecase.LoadJokesUseCase

class KittenViewModelFactory(
    private val loadJokesUseCase: LoadJokesUseCase,
    private val deleteAllJokesUseCase: DeleteAllJokesUseCase,
    private val loadCatImageUseCase: LoadCatImageUseCase,
    private val db: AppDatabase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KittenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return KittenViewModel(
                loadJokesUseCase, deleteAllJokesUseCase, loadCatImageUseCase, db
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
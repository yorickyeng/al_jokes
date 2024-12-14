package com.tinkoff.aljokes.presentation.kitten_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinkoff.aljokes.data.datasource.local.AppDatabase
import com.tinkoff.aljokes.domain.entity.Joke
import com.tinkoff.aljokes.domain.usecase.DeleteAllJokesUseCase
import com.tinkoff.aljokes.domain.usecase.LoadCatImageUseCase
import com.tinkoff.aljokes.domain.usecase.LoadJokesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class KittenViewModel(
    private val loadJokesUseCase: LoadJokesUseCase,
    private val deleteAllJokesUseCase: DeleteAllJokesUseCase,
    private val loadCatImageUseCase: LoadCatImageUseCase,
    db: AppDatabase
) : ViewModel() {

    private val jokesDao = db.jokesDao()
    val jokeLiveData: LiveData<List<Joke>> = jokesDao.getJokes()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _catUrl = MutableStateFlow<String?>(null)
    val catUrl: StateFlow<String?> = _catUrl
//    var catUrl by mutableStateOf<String?>("")

    init {
        getCatUrl()
    }

    fun loadJokes(dark: Boolean) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                loadJokesUseCase(10, dark)
            } catch (e: Exception) {
                println("Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteJokes() {
        viewModelScope.launch {
            deleteAllJokesUseCase()
        }
    }

    fun getCatUrl() {
        viewModelScope.launch {
            try {
                _catUrl.value = loadCatImageUseCase()
            } catch (e: Exception) {
                println("Error fetching cat image: ${e.message}")
            }
        }
    }
}
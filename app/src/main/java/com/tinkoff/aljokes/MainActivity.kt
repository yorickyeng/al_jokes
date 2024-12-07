package com.tinkoff.aljokes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tinkoff.aljokes.di.AppModule
import com.tinkoff.aljokes.domain.entity.Jokes
import com.tinkoff.aljokes.domain.usecase.AddInitialJokesUseCase
import com.tinkoff.aljokes.presentation.ui.AddJokesScreen
import com.tinkoff.aljokes.presentation.ui.KittenScreen
import com.tinkoff.aljokes.presentation.viewmodel.AddJokesViewModel
import com.tinkoff.aljokes.presentation.viewmodel.AddJokesViewModelFactory
import com.tinkoff.aljokes.presentation.viewmodel.KittenViewModel
import com.tinkoff.aljokes.presentation.viewmodel.KittenViewModelFactory
import com.tinkoff.aljokes.ui.theme.AlJokesTheme

const val KITTEN_ROUTE = "kitten"
const val ADD_JOKE_ROUTE = "add_joke"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppModule.provideDatabase(applicationContext)
        val repository = AppModule.provideJokesRepository(db)
        val useCases = AppModule.provideUseCases(repository)
        val jokesDao = db.jokesDao()
        val jokesLiveData: LiveData<List<Jokes>> = jokesDao.getJokes()

        AddInitialJokesUseCase(repository, jokesDao)

        setContent {
            enableEdgeToEdge()
            AlJokesTheme(darkTheme = isSystemInDarkTheme()) {
                val navController = rememberNavController()

                val kittenViewModel: KittenViewModel = viewModel(
                    factory = KittenViewModelFactory(
                        useCases.loadJokesUseCase,
                        useCases.loadCatImageUseCase,
                        useCases.deleteJokesUseCase
                    )
                )

                val addJokesViewModel: AddJokesViewModel = viewModel(
                    factory = AddJokesViewModelFactory(useCases.addJokesUseCase)
                )

                NavHost(
                    navController = navController,
                    startDestination = KITTEN_ROUTE,
                ) {
                    composable(KITTEN_ROUTE) {
                        KittenScreen(navController, jokesLiveData, kittenViewModel)
                    }
                    composable(ADD_JOKE_ROUTE) {
                        AddJokesScreen(navController, addJokesViewModel)
                    }
                }
            }
        }
    }
}

// только теперь при смене темы во время работы приложения почему-то перестаёт работать кнопка
// delete all и перестаёт грузить шутки при скролле

//com.tinkoff.aljokes/
//├── data/
//│   ├── datasource/ (Room, Retrofit и т.д.)
//│   ├── repository/ (реализация интерфейсов Domain слоя)
//│   ├── model/ (модели данных для хранения/сети)
//├── domain/
//│   ├── model/ (бизнес-модели, независимые от источников данных)
//│   ├── repository/ (интерфейсы репозиториев)
//│   ├── usecase/ (логика работы с данными)
//├── presentation/
//│   ├── ui/ (Compose элементы и экраны)
//│   ├── viewmodel/ (хранение состояния и взаимодействие с Domain)

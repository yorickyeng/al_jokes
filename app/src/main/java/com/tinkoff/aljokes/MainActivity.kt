package com.tinkoff.aljokes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tinkoff.aljokes.domain.usecase.AddInitialJokesUseCase
import com.tinkoff.aljokes.presentation.add_jokes_screen.AddJokesScreen
import com.tinkoff.aljokes.presentation.add_jokes_screen.AddJokesViewModelFactory
import com.tinkoff.aljokes.presentation.kitten_screen.KittenScreen
import com.tinkoff.aljokes.presentation.kitten_screen.KittenViewModelFactory
import com.tinkoff.aljokes.ui.theme.AlJokesTheme
import kotlinx.coroutines.launch
import javax.inject.Inject

const val KITTEN_ROUTE = "kitten"
const val ADD_JOKE_ROUTE = "add_joke"

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var addInitialJokesUseCase: AddInitialJokesUseCase

    @Inject
    lateinit var kittenViewModelFactory: KittenViewModelFactory

    @Inject
    lateinit var addJokesViewModelFactory: AddJokesViewModelFactory

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MyApp).appComponent.inject(this)

        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            addInitialJokesUseCase.invoke()
        }

        setContent {
            enableEdgeToEdge()
            AlJokesTheme(darkTheme = isSystemInDarkTheme()) {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = KITTEN_ROUTE,
                ) {
                    composable(KITTEN_ROUTE) {
                        KittenScreen(navController, kittenViewModelFactory)
                    }
                    composable(ADD_JOKE_ROUTE) {
                        AddJokesScreen(navController, addJokesViewModelFactory)
                    }
                }
            }
        }
    }
}
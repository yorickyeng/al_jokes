package com.tinkoff.aljokes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tinkoff.aljokes.di.AppModule
import com.tinkoff.aljokes.di.AppModule.addInitialJokesUseCase
import com.tinkoff.aljokes.presentation.add_jokes_screen.AddJokesScreen
import com.tinkoff.aljokes.presentation.kitten_screen.KittenScreen
import com.tinkoff.aljokes.ui.theme.AlJokesTheme

const val KITTEN_ROUTE = "kitten"
const val ADD_JOKE_ROUTE = "add_joke"

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppModule.createDatabase(applicationContext)

        val jokesRepository = AppModule.provideJokesRepository(applicationContext)
        val catImageRepository = AppModule.provideCatImageRepository()

        setContent {
            enableEdgeToEdge()
            AlJokesTheme(darkTheme = isSystemInDarkTheme()) {
                val navController = rememberNavController()

                addInitialJokesUseCase(jokesRepository)

                NavHost(
                    navController = navController,
                    startDestination = KITTEN_ROUTE,
                ) {
                    composable(KITTEN_ROUTE) {
                        KittenScreen(
                            navController,
                            jokesRepository,
                            catImageRepository,
                            db
                        )
                    }
                    composable(ADD_JOKE_ROUTE) {
                        AddJokesScreen(navController, jokesRepository)
                    }
                }
            }
        }
    }
}
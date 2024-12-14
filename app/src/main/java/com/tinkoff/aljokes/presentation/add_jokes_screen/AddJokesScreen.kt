package com.tinkoff.aljokes.presentation.add_jokes_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tinkoff.aljokes.di.AppModule
import com.tinkoff.aljokes.domain.repository.JokesRepository

@Composable
fun AddJokesScreen(
    navController: NavController,
    jokesRepository: JokesRepository,
) {

    val addJokesViewModel: AddJokesViewModel = viewModel(
        factory = AddJokesViewModelFactory(
            navController,
            AppModule.addJokesUseCase(jokesRepository)
        )
    )

    val errorMessage by addJokesViewModel.errorMessage.collectAsState()
    val isLoading by addJokesViewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        TextField(
            value = addJokesViewModel.id.value,
            onValueChange = { addJokesViewModel.onIdChange(it) },
            label = { Text("Id") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        TextField(
            value = addJokesViewModel.setup.value,
            onValueChange = { addJokesViewModel.onSetupChange(it) },
            label = { Text("Setup") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        TextField(
            value = addJokesViewModel.delivery.value,
            onValueChange = { addJokesViewModel.onDeliveryChange(it) },
            label = { Text("Delivery") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Button(
            onClick = {
                addJokesViewModel.onAddClick()
            },
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        ) {
            Text(text = if (isLoading) "Adding..." else "Add Joke")
        }
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

package com.tinkoff.aljokes.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tinkoff.aljokes.domain.entity.Jokes
import com.tinkoff.aljokes.presentation.viewmodel.AddJokesViewModel

@Composable
fun AddJokesScreen(navController: NavController, viewModel: AddJokesViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        TextField(
            value = viewModel.id,
            onValueChange = { viewModel.id = it },
            label = { Text("Id") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        TextField(
            value = viewModel.setup,
            onValueChange = { viewModel.setup = it },
            label = { Text("Setup") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        TextField(
            value = viewModel.delivery,
            onValueChange = { viewModel.delivery = it },
            label = { Text("Delivery") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Button(
            onClick = {
                if (viewModel.setup.isNotBlank() && viewModel.delivery.isNotBlank() && viewModel.id.toIntOrNull() != null) {
                    val newJoke = Jokes(viewModel.id.toInt(), viewModel.setup, viewModel.delivery)
                    viewModel.addJoke(newJoke)
                    viewModel.clearFields()
                    navController.popBackStack()
                } else {
                    viewModel.setError("Invalid input. Please check your fields.")
                }
            },
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        ) {
            Text(text = "Add Joke")
        }
        if (viewModel.errorMessage.isNotBlank()) {
            Text(
                text = viewModel.errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

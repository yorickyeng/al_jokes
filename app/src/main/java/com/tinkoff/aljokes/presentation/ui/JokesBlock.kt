package com.tinkoff.aljokes.presentation.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tinkoff.aljokes.domain.entity.Jokes

@Composable
fun JokesBlock(joke: Jokes) {
    var isExpanded by remember { mutableStateOf(false) }
    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 1.dp,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp)
            .border(1.5.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
            .clickable { isExpanded = !isExpanded }
            .animateContentSize(),
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "${joke.id}. ${joke.setup}",
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodyLarge,
            )
            if (isExpanded) {
                Text(
                    text = joke.delivery,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}
package com.tinkoff.aljokes.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import com.tinkoff.aljokes.ADD_JOKE_ROUTE
import com.tinkoff.aljokes.R
import com.tinkoff.aljokes.domain.entity.Jokes
import com.tinkoff.aljokes.presentation.viewmodel.KittenViewModel

@Composable
fun KittenScreen(
    navController: NavController, jokesLiveData: LiveData<List<Jokes>>, viewModel: KittenViewModel
) {

    val jokes by jokesLiveData.observeAsState(initial = emptyList())
    val listState = rememberLazyListState()
    val dark = isSystemInDarkTheme()

    LaunchedEffect(dark) {
        viewModel.loadJokes(dark)
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }.collect { visibleItems ->
            val totalItems = listState.layoutInfo.totalItemsCount
            val lastVisibleItemIndex = visibleItems.lastOrNull()?.index ?: 0

            if (totalItems > 0 && lastVisibleItemIndex >= totalItems - 1) {
                viewModel.loadJokes(dark)
            }
        }
    }

    LaunchedEffect(viewModel.reloadCat) {
        viewModel.getCatUrl()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(all = 10.dp),
    ) {
        LazyColumn(
            state = listState
        ) {
            item(contentType = "cat_image") {
                GlideImage(imageModel = { viewModel.catUrl },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp)
                        .padding(bottom = 16.dp)
                        .clickable(onClick = {
                            viewModel.reloadCat = !viewModel.reloadCat
                        }),
                    loading = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    },
                    failure = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable(onClick = {
                                    viewModel.reloadCat = !viewModel.reloadCat
                                }), contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.kitten),
                                contentDescription = "kitten"
                            )
                        }
                    })
            }
            item(contentType = "buttons") {
                Row {
                    Button(
                        onClick = {
                            navController.navigate(ADD_JOKE_ROUTE)
                        }, Modifier.padding(8.dp)
                    ) {
                        Text(text = "Add Joke")
                    }

                    Button(
                        onClick = {
                            viewModel.deleteJokes(jokes)
                        }, Modifier.padding(8.dp)
                    ) {
                        Text(text = "Delete All")
                    }
                }

                if (jokes.isEmpty()) {
                    Text(
                        text = "All jokes deleted!\n\n" + "Check your internet connection\n" + "and tap on a cat to reload",
                        modifier = Modifier
                            .padding(8.dp)
                            .padding(horizontal = 20.dp),
                        color = Color.Red,
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 32.sp,
                    )
                }
            }
            items(items = jokes, key = { joke -> joke.id }, contentType = { "joke" }) { joke ->
                JokesBlock(joke)
            }
            item(contentType = "loader") {
                if (viewModel.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(MaterialTheme.colorScheme.background),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
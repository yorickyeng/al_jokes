package com.tinkoff.aljokes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skydoves.landscapist.glide.GlideImage
import com.tinkoff.aljokes.data.api.CatRetrofitInstance
import com.tinkoff.aljokes.data.api.Joke
import com.tinkoff.aljokes.data.api.RetrofitInstance
import com.tinkoff.aljokes.ui.theme.AlJokesTheme
import kotlinx.coroutines.launch

private const val KITTEN_ROUTE = "kitten"
private const val ADD_JOKE_ROUTE = "add_joke"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            AlJokesTheme(darkTheme = isSystemInDarkTheme()) {
                val navController = rememberNavController()
                val jokes = remember { mutableStateListOf<Joke>() }
                jokes.addAll(jokeRepository)

                NavHost(
                    navController = navController,
                    startDestination = KITTEN_ROUTE
                ) {
                    composable(KITTEN_ROUTE) {
                        Kitten(navController, jokes)
                    }
                    composable(ADD_JOKE_ROUTE) {
                        AddJokesScreen(navController, jokes)
                    }
                }
            }
        }
    }
}

@Composable
fun AddJokesScreen(
    navController: NavController,
    jokes: MutableList<Joke>
) {
    var setup by remember { mutableStateOf("") }
    var delivery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        TextField(
            value = setup,
            onValueChange = { setup = it },
            label = { Text("Setup") },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onBackground)
        )
        TextField(
            value = delivery,
            onValueChange = { delivery = it },
            label = { Text("Delivery") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Button(
            onClick = {
                if (setup.isNotBlank() && delivery.isNotBlank()) {
                    jokes.add(Joke(setup, delivery))
                    navController.popBackStack()
                }
            },
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        ) {
            Text(text = "Add Joke")
        }
    }
}

@Composable
fun Kitten(navController: NavController, jokes: MutableList<Joke>) {
    val list = remember { mutableStateListOf<Joke>() }
    val dark = isSystemInDarkTheme()

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var catUrl by rememberSaveable { mutableStateOf("") }
    var reloadCat by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val totalItems = listState.layoutInfo.totalItemsCount
                val lastVisibleItemIndex = visibleItems.lastOrNull()?.index ?: 0

                if (totalItems > 0 && lastVisibleItemIndex >= totalItems - 1) {
                    coroutineScope.launch {
                        loadJokes(list, 10, dark) { isLoading = it }
                    }
                }
            }
    }

    LaunchedEffect(reloadCat) {
        try {
            val cat = CatRetrofitInstance.api.getCat().firstOrNull()
            catUrl = cat?.url ?: ""
        } catch (e: Exception) {
            println("Error fetching cat image: ${e.message}")
        }

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
            item {
                GlideImage(
                    imageModel = { catUrl },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp)
                        .padding(bottom = 16.dp)
                        .clickable(onClick = { reloadCat = !reloadCat }),
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
                                .clickable(onClick = { reloadCat = !reloadCat }),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.kitten),
                                contentDescription = "kitten"
                            )
                        }
                    }
                )

                Button(
                    onClick = {
                        navController.navigate("add_joke")
                    },
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(text = "Add Joke")
                }
            }
            items(jokes) { joke ->
                JokeBlock(
                    joke,
                    MaterialTheme.colorScheme.primary,
                    "[Manually added]"
                )
            }
            items(list) { joke ->
                JokeBlock(
                    joke,
                    MaterialTheme.colorScheme.tertiary,
                    "[Loaded from network]"
                )
            }
            item {
                if (isLoading) {
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

val jokeRepository = listOf(
    Joke(
        "What does Santa suffer from if he gets stuck in a chimney?",
        "Claustrophobia!"
    ),
    Joke("What’s a math teacher’s favorite place in NYC?", "Times Square."),
    Joke("What do you call a fish wearing a bowtie?", "Sofishticated."),
    Joke(
        "What was the spider doing on the computer?",
        "He was making a web-site.",
    ),
    Joke(
        "Why did the student bring a ladder to school?",
        "Because he wanted to go to high school."
    ),
    Joke(
        "Why did the biologist break up with the physicist?",
        "There was no chemistry.",
    ),
    Joke("What do you call cheese that isn’t yours?", "Nacho cheese."),
)

@Composable
fun JokeBlock(joke: Joke, color: Color, textMessage: String) {
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
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(
                text = joke.setup,
                color = color,
                style = MaterialTheme.typography.bodyLarge,
            )
            if (isExpanded) {
                Text(
                    text = joke.delivery,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = textMessage,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }

}


private suspend fun loadJokes(
    list: MutableList<Joke>,
    count: Int,
    dark: Boolean,
    setLoading: (Boolean) -> Unit
) {
    try {
        setLoading(true)
        if (dark) {
            val response = RetrofitInstance.api.getDarkJoke(count)
            list.addAll(response.jokes)
        } else {
            val response = RetrofitInstance.api.getAnyJoke(count)
            list.addAll(response.jokes)
        }
    } catch (e: Exception) {
        println("Error: ${e.message}")
    } finally {
        setLoading(false)
    }
}
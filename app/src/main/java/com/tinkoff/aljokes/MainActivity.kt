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
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.skydoves.landscapist.glide.GlideImage
import com.tinkoff.aljokes.data.Jokes
import com.tinkoff.aljokes.data.api.CatRetrofitInstance
import com.tinkoff.aljokes.data.api.loadJokesFromApi
import com.tinkoff.aljokes.data.db.AppDatabase
import com.tinkoff.aljokes.data.db.JokesDao
import com.tinkoff.aljokes.data.db.addInitialJokesToDatabase
import com.tinkoff.aljokes.ui.theme.AlJokesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            AlJokesTheme(darkTheme = isSystemInDarkTheme()) {

                val db = Room.databaseBuilder(
                    context = applicationContext,
                    AppDatabase::class.java,
                    "jokes_db"
                ).fallbackToDestructiveMigration()
                    .build()

                val jokesDao = db.jokesDao()
                if (jokesDao.getJokes().value.isNullOrEmpty()) {
                    addInitialJokesToDatabase(jokesDao)
                }
                val jokesLiveData: LiveData<List<Jokes>> = jokesDao.getJokes()

                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "kitten"
                ) {
                    composable("kitten") {
                        Kitten(navController, jokesLiveData, jokesDao)
                    }
                    composable("add_joke") {
                        AddJokesScreen(navController, jokesDao)
                    }
                }
            }
        }
    }
}


@Composable
fun Kitten(
    navController: NavController,
    jokesLiveData: LiveData<List<Jokes>>,
    jokesDao: JokesDao
) {
    val jokes by jokesLiveData.observeAsState(initial = emptyList())
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var catUrl by rememberSaveable { mutableStateOf("") }
    var reloadCat by remember { mutableStateOf(false) }
    var isDeleted by remember { mutableStateOf(false) }
    var dark by remember { mutableStateOf(false) }
    dark = isSystemInDarkTheme()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val totalItems = listState.layoutInfo.totalItemsCount
                val lastVisibleItemIndex = visibleItems.lastOrNull()?.index ?: 0

                if (totalItems > 0 && lastVisibleItemIndex >= totalItems - 1) {
                    coroutineScope.launch {
                        loadJokesFromApi(10, dark, jokesDao)
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

    LaunchedEffect(isDeleted) {
        if (isDeleted) {
            kotlinx.coroutines.delay(3000)
            isDeleted = false
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
                        .clickable(onClick = {
                            reloadCat = !reloadCat
                            if (isDeleted) isDeleted = false
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

                Row {
                    Button(
                        onClick = {
                            navController.navigate("add_joke")
                        },
                        Modifier.padding(8.dp)
                    ) {
                        Text(text = "Add Joke")
                    }

                    Button(
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                jokesDao.deleteAllJokes()
                            }
                            isDeleted = true
                        },
                        Modifier.padding(8.dp)
                    ) {
                        Text(text = "Delete All")
                    }
                }

                if (isDeleted) {
                    Text(
                        text = "All jokes deleted!\n\n" +
                                "Check your internet connection\n" +
                                "and tap on a cat to reload",
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
            items(jokes) { joke -> JokesBlock(joke, MaterialTheme.colorScheme.tertiary) }
        }
    }
}


@Composable
fun AddJokesScreen(navController: NavController, jokesDao: JokesDao) {
    var id by remember { mutableStateOf("") }
    var setup by remember { mutableStateOf("") }
    var delivery by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        TextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("Id") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        TextField(
            value = setup,
            onValueChange = { setup = it },
            label = { Text("Setup") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
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
                if (setup.isNotBlank() && delivery.isNotBlank() && id.toIntOrNull() != null) {
                    val newJoke = Jokes(id.toInt(), setup, delivery)
                    CoroutineScope(Dispatchers.IO).launch {
                        jokesDao.insertJoke(newJoke)
                    }
                    navController.popBackStack()
                } else {
                    errorMessage = "Invalid input. Please check your fields."
                }
            },
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        ) {
            Text(text = "Add Joke")
        }
        if (errorMessage.isNotBlank()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun JokesBlock(joke: Jokes, color: Color) {
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
                text = "${joke.id}. ${joke.setup}",
                color = color,
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
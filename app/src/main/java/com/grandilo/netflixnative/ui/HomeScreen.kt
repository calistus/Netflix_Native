package com.grandilo.netflixnative.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.grandilo.netflixnative.model.MovieModel
import com.grandilo.netflixnative.ui.movie.MovieEvent
import com.grandilo.netflixnative.ui.movie.MovieState
import com.grandilo.netflixnative.ui.movie.MovieViewModel
import com.grandilo.netflixnative.ui.theme.NetflixNativeTheme
import com.grandilo.netflixnative.utils.Routes


@Composable
fun HomeScreen(navController: NavController,
    viewModel: MovieViewModel = hiltViewModel()
) {
    Column(Modifier.padding(16.dp)) {
        val textState = remember { mutableStateOf(TextFieldValue()) }
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it }
        )
        Text("The textfield has this text: " + textState.value.text)

        Button(
            onClick = {
                print("Search received" + textState.value.text)
                viewModel.onEvent(MovieEvent.SearchMovieEvent(textState.value.text))
            },
        ) {
            Text(text = "Go")
        }

        val movieState by viewModel.movieState.collectAsState()
        val context = LocalContext.current

        when (movieState) {
            is MovieState.MovieLoadingState -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is MovieState.MovieLoadedState -> {
                val movies = (movieState as MovieState.MovieLoadedState).movies
                MovieList(navController=navController, movies)
            }
            is MovieState.MovieError -> {
                val message = (movieState as MovieState.MovieError).errorMessage
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}


@Composable
fun MovieList(navController: NavController,movies: MovieModel) {
    LazyColumn {
        items(movies.results) { movie ->
            MovieListItem(movie = movie, navController = navController)
        }
    }
}

@Composable
fun MovieListItem(navController: NavController, movie: com.grandilo.netflixnative.model.Result) {
    Column(modifier = Modifier.padding(8.dp)) {
        Image(
            painter = rememberAsyncImagePainter(movie.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clickable(
                    enabled = true,
                    onClickLabel = "Clickable image",
                    onClick = {
                        navController.navigate(Routes.MOVIE_DETAIL)
                    }
                )
        )
        Text(
            text = movie.title,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = movie.description,
            style = MaterialTheme.typography.body1
        )
    }
}


package com.grandilo.netflixnative.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grandilo.netflixnative.model.MovieModel
import com.grandilo.netflixnative.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(val repository: MovieRepository) : ViewModel() {
    val movieState = MutableStateFlow<MovieState>(MovieState.MovieInitialState)

    val movieEvent = Channel<MovieEvent>().receiveAsFlow()

    init {
    }

    fun onEvent(event: MovieEvent) {
        when (event) {
            is MovieEvent.SearchMovieEvent -> {
                viewModelScope.launch {
                    movieState.tryEmit(MovieState.MovieLoadingState("Fetching Movies"))
                    withContext(Dispatchers.IO) {
                        try {
                            val movies = repository.searchMovie(event.searchKey)
                            delay(5000)
                            movieState.tryEmit(MovieState.MovieLoadedState(movies))
                        } catch (e: Exception) {
                            movieState.tryEmit(MovieState.MovieError(e.localizedMessage))
                        }
                    }
                }
            }
            is MovieEvent.BookMarkMovie -> {
                //TODO:Implement Bookmark
            }

        }
    }
}

sealed class MovieState {
    object MovieInitialState : MovieState()
    data class MovieLoadingState(val message: String) : MovieState()
    data class MovieLoadedState(val movies: MovieModel) : MovieState()
    data class MovieError(val errorMessage: String) : MovieState()
}

sealed class MovieEvent {
    object InitApp : MovieEvent()
    data class SearchMovieEvent(val searchKey: String) : MovieEvent()
    data class BookMarkMovie(val movie: MovieModel) : MovieEvent()

}
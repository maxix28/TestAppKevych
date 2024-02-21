package com.example.testappkevych.ui.Screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappkevych.data.MovieRepository
import com.example.testappkevych.network.model.Movie
import com.example.testappkevych.network.model.Result
import com.example.testappkevych.ui.navigation.Destaination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface MovieState {
    object Loading : MovieState
    object Error : MovieState

    data class Success(val movie: Movie) : MovieState

}

val Tag = "MOVIE"

@HiltViewModel
class MovieByIdViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {
    var MovieUiState: MovieState by mutableStateOf(MovieState.Loading)

    var movie_ID = checkNotNull(savedStateHandle[Destaination.MovieById.Movie_Id]).toString()

    private suspend fun getMovieByID() {
        try {
            val result = movieRepository.getMovieByID(movie_ID)
            MovieUiState = MovieState.Success(result)
        } catch (e: Exception) {
            MovieUiState = MovieState.Error
            Log.e(Tag, e.message.toString())

        }
    }

    init {
        viewModelScope.launch {
            getMovieByID()
            Log.d("CAT", "USE hilt")

        }

    }
}
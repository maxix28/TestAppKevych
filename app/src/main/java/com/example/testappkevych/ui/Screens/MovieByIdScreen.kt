package com.example.testappkevych.ui.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun MovieScreen(
    viewModel: MovieByIdViewModel = hiltViewModel<MovieByIdViewModel>(),
    modifier: Modifier = Modifier
) {
    when (viewModel.MovieUiState) {
        MovieState.Error -> {
            Text(text = "Error")
        }

        MovieState.Loading -> {
            Text(text = "Loading")
        }

        is MovieState.Success -> {
            OnMovieSuccess(state = (viewModel.MovieUiState as MovieState.Success))
        }
    }
}

@Composable
fun OnMovieSuccess(state: MovieState.Success, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${state.movie.backdrop_path}",
                contentDescription = null,
                modifier = modifier.fillMaxWidth()
            )
        }

    }

}
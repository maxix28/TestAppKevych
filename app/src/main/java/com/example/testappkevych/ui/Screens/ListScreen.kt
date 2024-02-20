package com.example.testappkevych.ui.Screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testappkevych.network.model.*
@Composable
fun MovieList( viewModel: MovieListViewModel = hiltViewModel<MovieListViewModel>(),modifier: Modifier = Modifier){
when(viewModel.MovieListUIState){
    MovieListState.Error -> {Text("Error")}
    MovieListState.Loading ->{Text("Loading")}
    is MovieListState.Success -> {onSuccess((viewModel.MovieListUIState as MovieListState.Success).MovieList)}
}
}
@Composable
fun onSuccess(movies: Movies,modifier: Modifier = Modifier){

   // val results: List<Result>? = movies.results
    LazyColumn(modifier = modifier
        .fillMaxSize()
        .padding(20.dp)) {
        items(count = movies.results?.size ?: 0) { index ->
            // Use index to access individual items if needed
            val result = movies.results?.get(index)
            if (result != null) {
                Text("Title: ${result.title}")
                println("Overview: ${result.overview}")
                println() // Just for spacing
            }
        }
    }

    LaunchedEffect(key1 = movies ){

        println("movies.results.toString()")
        Log.d("MOVIE",movies.results.toString())
        Log.d("MOVIE","Test")
    }

}
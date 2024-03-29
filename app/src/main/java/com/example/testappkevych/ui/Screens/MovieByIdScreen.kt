package com.example.testappkevych.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.testappkevych.R

@Composable
fun MovieScreen(
    viewModel: MovieByIdViewModel = hiltViewModel<MovieByIdViewModel>(),
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {


        when (viewModel.MovieUiState) {
            MovieState.Error -> {
                  Error ()
            }

            MovieState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center, modifier = modifier
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }

            }

            is MovieState.Success -> {
                OnMovieSuccess(state = (viewModel.MovieUiState as MovieState.Success))
            }
        }
    }
}

@Composable
fun OnMovieSuccess(state: MovieState.Success, modifier: Modifier = Modifier) {
    var movie = state.movie
    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.backdrop_path}",
                contentDescription = null,
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(220.dp)
            )
        }
        item {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = movie.title.toString(),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = movie.release_date.toString().substring(0, 4),
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = movie.runtime.toString() + "m",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

        }
        
        item {
            Row(
                modifier = modifier
                    .fillMaxWidth(0.8f)
                    .padding(10.dp),
                verticalAlignment = Alignment.Bottom,
                //horizontalArrangement = Arrangement.SpaceAround
            ){

                Text(text ="Genres: ", fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground)
                movie.genres?.forEach { 
                    Text(text = it.name.toString(), fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onBackground)
                    Text(text =", ", fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onBackground)
                }
            }
        }
        item {
            Text( text = " Popularity: ${movie.popularity.toString()}", fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground, modifier= modifier.padding(5.dp))
        }

        item{
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground)) {
                        append("Overview: ")
                    }
                    withStyle(style = SpanStyle(fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onBackground)) {
                       append(movie.overview.toString())

                    }

                }
                      ,  modifier= modifier.padding(10.dp)
            )
        }

        item{
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground)) {
                        append("Production Companies: ")
                    }
                    withStyle(style = SpanStyle(fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onBackground)) {
                        movie.production_companies?.forEach{
                            append( "${it.name.toString()}, ")
                        }

                    }

                },
                modifier= modifier.padding(10.dp)
            )
        }
    }

}


@Composable
fun Error(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(modifier = modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.sad_error), contentDescription = null)
            Text(text = "Oops, you faced an error")
            Spacer(modifier = modifier.height(10.dp))


        }

    }
}
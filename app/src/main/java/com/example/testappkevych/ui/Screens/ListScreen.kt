package com.example.testappkevych.ui.Screens

import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.testappkevych.R
import com.example.testappkevych.network.model.*
import kotlin.reflect.KFunction0

@Composable
fun MovieList(
    viewModel: MovieListViewModel = hiltViewModel<MovieListViewModel>(),
    modifier: Modifier = Modifier,
    onMovieClick: (String) -> Unit
) {
    var size by remember {
        mutableStateOf(true)
    }

    Scaffold(topBar = { topBar(BigBarSize = size) }) { paddingValue ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValue)
        ) {


            when (viewModel.MovieListUIState) {
                MovieListState.Error -> {
                    Text("Error")
                }

                MovieListState.Loading -> {
                    //Text("Loading")
                }

                is MovieListState.Success -> {
                    size = false
                    onSuccess(
                        (viewModel.MovieListUIState as MovieListState.Success),
                        onMovieClick = onMovieClick,
                        LoadMoreMoviePopular=   viewModel::loadMoreMoviesPopular,
                        LoadMoreMovieTrend=   viewModel::loadMoreMoviesTrend
                    )
                }
            }
        }
    }

}


@Composable
fun onSuccess(
    state: MovieListState.Success,
    onMovieClick: (String) -> Unit,
    modifier: Modifier = Modifier,

    LoadMoreMoviePopular:()->Unit,
    LoadMoreMovieTrend: ()->Unit
) {
    val listStateTrend = rememberLazyListState()
    val listStatePopular = rememberLazyListState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding()
    ) {
        Text(
            "Trend",
            modifier = modifier.padding(horizontal = 10.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        List(state.MovieListTrend, onMovieClick = onMovieClick, lazyListState = listStateTrend, onLoadMore = LoadMoreMovieTrend)
        Text(
            "Popular",
            modifier = modifier.padding(horizontal = 10.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        List(state.MovieListPopular, onMovieClick = onMovieClick, lazyListState = listStatePopular, onLoadMore = LoadMoreMoviePopular)

    }

}


@Composable
fun List(
    movies: Movies,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    onMovieClick: (String) -> Unit,
    onLoadMore: () -> Unit
) {
    val isScrolledToEnd = lazyListState.layoutInfo.visibleItemsInfo
        .lastOrNull()?.index == (movies.results?.size ?: 0) - 1
    LazyRow(
        state = lazyListState, modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        items(count = movies.results?.size ?: 0) { index ->
            // Use index to access individual items if needed
            val result = movies.results?.get(index)
            if (result != null) {
                OneMovieItem(result, onMovieClick = onMovieClick)

            }

            if (isScrolledToEnd ) {
                LaunchedEffect(Unit) {
                    onLoadMore()
                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneMovieItem(movie: Result, modifier: Modifier = Modifier, onMovieClick: (String) -> Unit) {
    Card(modifier = modifier.padding(5.dp), shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 20.dp
        ),
        onClick = { onMovieClick(movie.id.toString()) }) {

        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
            contentDescription = null,
            modifier = modifier.height(180.dp)
        )
    }

}




@Composable
fun topBar(modifier: Modifier = Modifier, BigBarSize: Boolean) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onPrimary)

            //  .clickable { size=!size }
            .animateContentSize()
            .padding(if (BigBarSize) 100.dp else 10.dp),
        horizontalArrangement = Arrangement.Absolute.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        Text("KEVYCH  movie", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = modifier.width(10.dp))
        Image(
            painter = painterResource(id = R.drawable.icon_tv),
            contentDescription = "Icon",
            modifier = modifier.size(25.dp)
        )
    }
}
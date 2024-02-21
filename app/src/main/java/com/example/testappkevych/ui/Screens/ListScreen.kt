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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.testappkevych.R
import com.example.testappkevych.network.model.*
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction0
import kotlinx.coroutines.flow.collect

@Composable
fun MovieList(
    viewModel: MovieListViewModel = hiltViewModel<MovieListViewModel>(),
    modifier: Modifier = Modifier,
    onMovieClick: (String) -> Unit
) {
    var size by remember {
        mutableStateOf(true)
    }
    val coroutinescope = rememberCoroutineScope()

    Scaffold(topBar = { topBar(BigBarSize = size) }) { paddingValue ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValue)
        ) {


            when (viewModel.MovieListUIState) {
                MovieListState.Error -> {
                    Error(Retry = {
                        coroutinescope.launch {
                            viewModel.retry()
                        }
                    })
                }

                MovieListState.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center, modifier = modifier
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }

                is MovieListState.Success -> {
                    size = false
                    onSuccess(
                        (viewModel.MovieListUIState as MovieListState.Success),
                        onMovieClick = onMovieClick,
                        LoadMoreMoviePopular = viewModel::loadMoreMoviesPopular,
                        LoadMoreMovieTrend = viewModel::loadMoreMoviesTrend
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
    LoadMoreMoviePopular: () -> Unit,
    LoadMoreMovieTrend: () -> Unit
) {
    val listStateTrend = rememberLazyListState()
    val listStatePopular = rememberLazyGridState()

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
            color = MaterialTheme.colorScheme.primary
        )
        ListRow(
            state.MovieListTrend,
            onMovieClick = onMovieClick,
            lazyListState = listStateTrend,
            onLoadMore = LoadMoreMovieTrend
        )
        Text(
            "Popular",
            modifier = modifier.padding(horizontal = 10.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        PopularGrid(
            state.MovieListPopular,
            onMovieClick = onMovieClick, lazyGridState
            = listStatePopular,
            onLoadMore = LoadMoreMoviePopular
        )

    }

}


@Composable
fun ListRow(
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

            val result = movies.results?.get(index)
            if (result != null) {
                OneMovieIteminRow(result, onMovieClick = onMovieClick)

            }

            if (isScrolledToEnd) {
                LaunchedEffect(Unit) {
                    onLoadMore()
                }
            }
        }
    }

}

@Composable
fun ListColumn(
    movies: Movies,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    onMovieClick: (String) -> Unit,
    onLoadMore: () -> Unit
) {
    val isScrolledToEnd = lazyListState.layoutInfo.visibleItemsInfo
        .lastOrNull()?.index == (movies.results?.size ?: 0) - 1
    LazyColumn(
        state = lazyListState, modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        items(count = movies.results?.size ?: 0) { index ->

            val result = movies.results?.get(index)
            if (result != null) {
                OneMovieItemInColumn(result, onMovieClick = onMovieClick)

            }

            if (isScrolledToEnd) {
                LaunchedEffect(Unit) {
                    onLoadMore()
                }
            }
        }
    }

}

@Composable
fun PopularGrid(
    movies: Movies,
    lazyGridState: LazyGridState,
    modifier: Modifier = Modifier,
    onMovieClick: (String) -> Unit,
    onLoadMore: () -> Unit
) {

    val isScrolledToEnd = lazyGridState.layoutInfo.visibleItemsInfo
        .lastOrNull()?.index == (movies.results?.size ?: 0) - 1

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = lazyGridState,
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        items(count = movies.results?.size ?: 0) { index ->
            val result = movies.results?.get(index)
            if (result != null) {
                OneMovieItemInColumn(result, onMovieClick = onMovieClick)
            }
            if (isScrolledToEnd) {
                LaunchedEffect(Unit) {
                    onLoadMore()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneMovieIteminRow(
    movie: Result,
    modifier: Modifier = Modifier,
    onMovieClick: (String) -> Unit
) {

    Card(modifier = modifier.padding(5.dp)
        // .shadow(ambientColor = Color.White, elevation = 20.dp)
        , shape = RoundedCornerShape(10.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        onClick = { onMovieClick(movie.id.toString()) }) {

        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
            contentDescription = null,
            modifier = modifier.height(180.dp)
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneMovieItemInColumn(
    movie: Result,
    modifier: Modifier = Modifier,
    onMovieClick: (String) -> Unit
) {
    Card(modifier = modifier.padding(20.dp), shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        onClick = { onMovieClick(movie.id.toString()) }) {

            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                contentDescription = null, contentScale = ContentScale.Fit,

            )




    }

}


@Composable
fun topBar(modifier: Modifier = Modifier, BigBarSize: Boolean) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)

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


@Composable
fun Error(modifier: Modifier = Modifier, Retry: () -> Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.sad_error), contentDescription = null)
            Text(text = "Oops, you faced an error")
            Spacer(modifier = modifier.height(10.dp))
            IconButton(onClick = Retry) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = modifier.size(200.dp)
                )
            }

        }

    }
}
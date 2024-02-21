package com.example.testappkevych.ui.Screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappkevych.data.MovieRepository
import com.example.testappkevych.network.model.Movies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

val TAG = "MOVIE"
sealed interface MovieListState{
    object Loading: MovieListState
    object Error: MovieListState

    data class Success( val MovieListTrend : Movies,val MovieListPopular : Movies): MovieListState

}
@HiltViewModel
class MovieListViewModel @Inject constructor(private val movieRepository: MovieRepository)  :  ViewModel() {

    var MovieListUIState :MovieListState by mutableStateOf(MovieListState.Loading)
var trendPage =1
var popularPage =1
    suspend fun getMovieList(){
        try{
            MovieListUIState =MovieListState.Success( MovieListPopular =movieRepository.getMoviePopular(popularPage.toString()), MovieListTrend = movieRepository.getMovieTrend(trendPage.toString()))
        }
        catch (e:Exception){
            MovieListUIState = MovieListState.Error
            Log.e(TAG, e.message.toString())
        }
    }
    fun loadMoreMoviesTrend() {
        viewModelScope.launch {
            try {

                trendPage++
                val newTrendMovies = movieRepository.getMovieTrend(trendPage.toString())
                if (MovieListUIState is MovieListState.Success) {
                    val currentTrendMovies = (MovieListUIState as MovieListState.Success).MovieListTrend
                    val updatedTrendMovies = currentTrendMovies.copy(
                        results = (currentTrendMovies.results?.plus(newTrendMovies.results ?: emptyArray()) ?: emptyArray())
                    )
                    MovieListUIState = MovieListState.Success(
                        MovieListTrend = updatedTrendMovies,
                        MovieListPopular = (MovieListUIState as MovieListState.Success).MovieListPopular
                    )
                }
            } catch (e: Exception) {
                MovieListUIState = MovieListState.Error
                Log.e(TAG, e.message.toString())
            }
        }
    } fun loadMoreMoviesPopular() {
        viewModelScope.launch {
            try {

                popularPage++
                val newTrendMovies = movieRepository.getMovieTrend(popularPage.toString())
                if (MovieListUIState is MovieListState.Success) {
                    val currentPopularMovies = (MovieListUIState as MovieListState.Success).MovieListPopular
                    val updatedPopularMovies = currentPopularMovies.copy(
                        results = (currentPopularMovies.results?.plus(newTrendMovies.results ?: emptyArray()) ?: emptyArray()))
                    MovieListUIState = MovieListState.Success(
                        MovieListTrend = (MovieListUIState as MovieListState.Success).MovieListTrend,
                        MovieListPopular =updatedPopularMovies
                    )
                }
            } catch (e: Exception) {
                MovieListUIState = MovieListState.Error
                Log.e(TAG, e.message.toString())
            }
        }
    }
    fun retry(){
        MovieListUIState = MovieListState.Loading

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                getMovieList()

            }
        }

    }
    init{
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                getMovieList()

            }
        }




    }
}
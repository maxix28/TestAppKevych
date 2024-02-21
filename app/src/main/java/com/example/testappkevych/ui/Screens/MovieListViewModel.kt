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

    suspend fun getMovieList(){
        try{
            MovieListUIState =MovieListState.Success( MovieListPopular =movieRepository.getMoviePopular(), MovieListTrend = movieRepository.getMovieTrend())
        }
        catch (e:Exception){
            MovieListUIState = MovieListState.Error
            Log.e(TAG, e.message.toString())
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
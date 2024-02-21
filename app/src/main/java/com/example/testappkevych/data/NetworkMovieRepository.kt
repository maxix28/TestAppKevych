package com.example.testappkevych.data

import com.example.testappkevych.network.MovieServiceApiInterface
import com.example.testappkevych.network.model.Movie
import com.example.testappkevych.network.model.Movies
import com.example.testappkevych.network.model.Result
import javax.inject.Inject

class NetworkMovieRepository @Inject constructor(private val movieServiceApiInterface: MovieServiceApiInterface):MovieRepository {
    override suspend fun getMoviePopular(): Movies = movieServiceApiInterface.getMoviePopular()
    override suspend fun getMovieTrend(): Movies = movieServiceApiInterface.getMovieTrend()
    override suspend fun getMovieByID(id: String): Movie = movieServiceApiInterface.getMovieByID(id)
}

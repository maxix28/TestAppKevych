package com.example.testappkevych.data

import com.example.testappkevych.BuildConfig
import com.example.testappkevych.network.MovieServiceApiInterface
import com.example.testappkevych.network.model.Movie
import com.example.testappkevych.network.model.Movies
import com.example.testappkevych.network.model.Result
import javax.inject.Inject
private val key =BuildConfig.API_KEY
class NetworkMovieRepository @Inject constructor(private val movieServiceApiInterface: MovieServiceApiInterface):MovieRepository {
    override suspend fun getMoviePopular(page:String): Movies = movieServiceApiInterface.getMoviePopular(apiKey = key, page)


    override suspend fun getMovieTrend(page:String): Movies = movieServiceApiInterface.getMovieTrend(apiKey = key,page)
    override suspend fun getMovieByID(id: String): Movie = movieServiceApiInterface.getMovieByID(id,apiKey = key)
}

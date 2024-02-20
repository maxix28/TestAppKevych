package com.example.testappkevych.data

import com.example.testappkevych.network.MovieServiceApiInterface
import com.example.testappkevych.network.model.Movies
import javax.inject.Inject

class NetworkMovieRepository @Inject constructor(private val movieServiceApiInterface: MovieServiceApiInterface):MovieRepository {
    override suspend fun getMovie(): Movies = movieServiceApiInterface.getMovie()
}
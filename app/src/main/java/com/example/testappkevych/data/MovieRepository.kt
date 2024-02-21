package com.example.testappkevych.data

import com.example.testappkevych.network.model.*

interface MovieRepository {
    suspend fun getMoviePopular(): Movies
    suspend fun getMovieTrend(): Movies

    suspend fun getMovieByID(id:String):Movie

}
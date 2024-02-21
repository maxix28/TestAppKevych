package com.example.testappkevych.data

import com.example.testappkevych.network.model.*

interface MovieRepository {
    suspend fun getMoviePopular(page:String): Movies
    suspend fun getMovieTrend(page:String): Movies
    suspend fun getMovieByID(id:String):Movie

}
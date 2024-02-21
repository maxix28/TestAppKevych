package com.example.testappkevych.network

import com.example.testappkevych.network.model.Movie
import com.example.testappkevych.network.model.Movies
import com.example.testappkevych.network.model.Result
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieServiceApiInterface {
    @GET("3/movie/popular?api_key=ed8a7f6930a3121c20d22bbc89dba5c2")
    suspend fun getMoviePopular():Movies
    @GET("3/trending/all/day?api_key=ed8a7f6930a3121c20d22bbc89dba5c2")
    suspend fun getMovieTrend():Movies
    @GET("3/movie/{id}?api_key=ed8a7f6930a3121c20d22bbc89dba5c2")
    suspend fun getMovieByID(@Path("id") id: String?): Movie

}
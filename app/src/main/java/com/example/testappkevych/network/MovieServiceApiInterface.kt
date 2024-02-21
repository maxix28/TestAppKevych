package com.example.testappkevych.network

import com.example.testappkevych.network.model.Movie
import com.example.testappkevych.network.model.Movies
import com.example.testappkevych.network.model.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieServiceApiInterface {
//    @GET("3/movie/popular?api_key=ed8a7f6930a3121c20d22bbc89dba5c2")
//    suspend fun getMoviePopular():Movies
//    @GET("3/trending/all/day?api_key=ed8a7f6930a3121c20d22bbc89dba5c2")
//    suspend fun getMovieTrend():Movies
//    @GET("3/movie/{id}?api_key=ed8a7f6930a3121c20d22bbc89dba5c2")
//    suspend fun getMovieByID(@Path("id") id: String?): Movie

    @GET("3/movie/popular")
    suspend fun getMoviePopular(
        @Query("api_key") apiKey: String,
        @Query("page") page: String
    ): Movies

    @GET("3/trending/all/day")
    suspend fun getMovieTrend(
        @Query("api_key") apiKey: String,
        @Query("page") page: String): Movies

    @GET("3/movie/{id}")
    suspend fun getMovieByID(
        @Path("id") id: String?,
        @Query("api_key") apiKey: String
    ): Movie
}
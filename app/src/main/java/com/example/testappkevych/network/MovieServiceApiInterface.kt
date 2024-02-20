package com.example.testappkevych.network

import com.example.testappkevych.network.model.Movies
import retrofit2.http.GET

interface MovieServiceApiInterface {
    @GET("3/trending/all/day?api_key=ed8a7f6930a3121c20d22bbc89dba5c2")
    suspend fun getMovie():Movies
}
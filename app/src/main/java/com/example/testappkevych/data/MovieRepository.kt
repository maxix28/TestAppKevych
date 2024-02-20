package com.example.testappkevych.data

import com.example.testappkevych.network.model.Movies

interface MovieRepository {
    suspend fun getMovie(): Movies

}
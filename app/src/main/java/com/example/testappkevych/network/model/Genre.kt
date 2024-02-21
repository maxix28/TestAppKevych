package com.example.testappkevych.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int? = null,
    val name: String? = null
)
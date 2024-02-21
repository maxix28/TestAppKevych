package com.example.testappkevych.network.model

import kotlinx.serialization.Serializable

@Serializable
data class BelongsToCollection(
    val backdrop_path: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val poster_path: String? = null
)
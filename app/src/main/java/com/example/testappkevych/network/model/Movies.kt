package com.example.testappkevych.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Movies(
    var page: Int? = null,
    var results: Array<Result>? = null,
    var total_pages: Int? = null,
    var total_results: Int? = null
)
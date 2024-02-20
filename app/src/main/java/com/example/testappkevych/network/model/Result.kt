package com.example.testappkevych.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    var adult: Boolean? = null,
    var backdrop_path: String? = null,
    var first_air_date: String? = null,
    var genre_ids: List<Int>? = null,
    var id: Int? = null,
    var media_type: String? = null,
    var name: String? = null,
    var origin_country: List<String>? = null,
    var original_language: String? = null,
    var original_name: String? = null,
    var original_title: String? = null,
    var overview: String? = null,
    var popularity: Double? = null,
    var poster_path: String? = null,
    var release_date: String? = null,
    var title: String? = null,
    var video: Boolean? = null,
    var vote_average: Double? = null,
    var vote_count: Int? = null
)
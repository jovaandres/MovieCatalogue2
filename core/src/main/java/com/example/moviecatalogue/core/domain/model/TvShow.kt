package com.example.moviecatalogue.core.domain.model

data class TvShow(
    var backdrop_path: String?,
    var first_air_date: String?,
    var id: Int?,
    var isPopular: Boolean,
    var title: String?,
    var original_language: String?,
    var original_name: String?,
    var overview: String?,
    var popularity: Double?,
    var poster_path: String?,
    var textQuery: String?,
    var vote_average: Double?,
    var vote_count: Int?
)

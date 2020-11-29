package com.example.moviecatalogue.core.domain.model

data class DetailTvShow(
    var backdrop_path: String?,
    var first_air_date: String?,
    var homepage: String?,
    var id: Int?,
    var in_production: Boolean?,
    var isFavorite: Boolean? = false,
    var last_air_date: String?,
    var title: String?,
    var number_of_episodes: Int?,
    var number_of_seasons: Int?,
    var original_language: String?,
    var original_name: String?,
    var overview: String?,
    var popularity: Double?,
    var poster_path: String?,
    var status: String?,
    var type: String?,
    var vote_average: Double?,
    var vote_count: Int?
)

package com.example.moviecatalogue.core.data.source.remote.response

data class MovieDetailResponse(
    var adult: Boolean?,
    var backdrop_path: String?,
    var belongs_to_collection: Any?,
    var budget: Int?,
    var homepage: String?,
    var id: Int?,
    var imdb_id: String?,
    var original_language: String?,
    var original_title: String?,
    var overview: String?,
    var popularity: Double?,
    var poster_path: String?,
    var release_date: String?,
    var revenue: Int?,
    var runtime: Int?,
    var status: String?,
    var tagline: String?,
    var title: String?,
    var video: Boolean?,
    var vote_average: Double?,
    var vote_count: Int?
)
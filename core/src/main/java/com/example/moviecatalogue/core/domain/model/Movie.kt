package com.example.moviecatalogue.core.domain.model

data class Movie(
    var adult: Boolean?,
    var backdropPath: String?,
    var id: Int?,
    var isPopular: Boolean,
    var originalLanguage: String?,
    var originalTitle: String?,
    var overview: String?,
    var popularity: Double?,
    var posterPath: String?,
    var releaseDate: String?,
    var title: String?,
    var textQuery: String?,
    var video: Boolean?,
    var voteAverage: Double?,
    var voteCount: Int?
)

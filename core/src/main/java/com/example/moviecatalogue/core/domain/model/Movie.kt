package com.example.moviecatalogue.core.domain.model

data class Movie(
    var backdropPath: String?,
    var id: Int?,
    var isPopular: Boolean,
    var overview: String?,
    var posterPath: String?,
    var releaseDate: String?,
    var title: String?,
    var textQuery: String?,
    var voteAverage: Double?
)

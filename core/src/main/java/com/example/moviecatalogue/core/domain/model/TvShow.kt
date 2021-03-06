package com.example.moviecatalogue.core.domain.model

data class TvShow(
    var backdropPath: String?,
    var releaseDate: String?,
    var id: Int?,
    var isPopular: Boolean,
    var title: String?,
    var overview: String?,
    var posterPath: String?,
    var textQuery: String?,
    var voteAverage: Double?
)

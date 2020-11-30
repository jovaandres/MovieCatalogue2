package com.example.moviecatalogue.core.domain.model

data class TvShow(
    var backdropPath: String?,
    var firstAirDate: String?,
    var id: Int?,
    var isPopular: Boolean,
    var title: String?,
    var originalLanguage: String?,
    var originalName: String?,
    var overview: String?,
    var popularity: Double?,
    var posterPath: String?,
    var textQuery: String?,
    var voteAverage: Double?,
    var voteCount: Int?
)

package com.example.moviecatalogue.core.domain.model

data class DetailMovie(
    var adult: Boolean?,
    var backdropPath: String?,
    var budget: Int?,
    var homepage: String?,
    var id: Int?,
    var imdbId: String?,
    var isFavorite: Boolean? = false,
    var originalLanguage: String?,
    var originalTitle: String?,
    var overview: String?,
    var popularity: Double?,
    var posterPath: String?,
    var releaseDate: String?,
    var revenue: Int?,
    var runtime: Int?,
    var status: String?,
    var tagline: String?,
    var title: String?,
    var video: Boolean?,
    var voteAverage: Double?,
    var voteCount: Int?
)

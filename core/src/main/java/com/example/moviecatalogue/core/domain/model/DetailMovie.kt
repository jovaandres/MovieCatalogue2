package com.example.moviecatalogue.core.domain.model

data class DetailMovie(
    var backdropPath: String?,
    var homepage: String?,
    var id: Int?,
    var isFavorite: Boolean? = false,
    var overview: String?,
    var posterPath: String?,
    var releaseDate: String?,
    var runtime: Int?,
    var title: String?,
    var voteAverage: Double?
)

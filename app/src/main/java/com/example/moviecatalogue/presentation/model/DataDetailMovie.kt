package com.example.moviecatalogue.presentation.model

data class DataDetailMovie(
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
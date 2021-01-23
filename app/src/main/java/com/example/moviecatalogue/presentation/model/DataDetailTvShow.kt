package com.example.moviecatalogue.presentation.model

data class DataDetailTvShow(
    var backdropPath: String?,
    var firstAirDate: String?,
    var homepage: String?,
    var id: Int?,
    var isFavorite: Boolean? = false,
    var title: String?,
    var numberOfEpisodes: Int?,
    var numberOfSeasons: Int?,
    var overview: String?,
    var posterPath: String?,
    var voteAverage: Double?,
)
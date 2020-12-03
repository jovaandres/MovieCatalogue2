package com.example.moviecatalogue.core.domain.model

data class DetailTvShow(
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

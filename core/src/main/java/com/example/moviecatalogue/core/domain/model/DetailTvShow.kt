package com.example.moviecatalogue.core.domain.model

data class DetailTvShow(
    var backdropPath: String?,
    var firstAirDate: String?,
    var homepage: String?,
    var id: Int?,
    var inProduction: Boolean?,
    var isFavorite: Boolean? = false,
    var lastAirDate: String?,
    var title: String?,
    var numberOfEpisodes: Int?,
    var numberOfSeasons: Int?,
    var originalLanguage: String?,
    var originalName: String?,
    var overview: String?,
    var popularity: Double?,
    var posterPath: String?,
    var status: String?,
    var type: String?,
    var voteAverage: Double?,
    var voteCount: Int?
)

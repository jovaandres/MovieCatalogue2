package com.example.moviecatalogue.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_show_detail")
data class TvShowDetailEntity (
    var backdropPath: String?,
    var firstAirDate: String?,
    var homepage: String?,
    @PrimaryKey
    @NonNull
    var id: Int?,
    var inProduction: Boolean?,
    var isFavorite: Boolean,
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
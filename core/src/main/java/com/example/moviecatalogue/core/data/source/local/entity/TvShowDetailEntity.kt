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
    var isFavorite: Boolean,
    var title: String?,
    var numberOfEpisodes: Int?,
    var numberOfSeasons: Int?,
    var overview: String?,
    var posterPath: String?,
    var voteAverage: Double?,
)
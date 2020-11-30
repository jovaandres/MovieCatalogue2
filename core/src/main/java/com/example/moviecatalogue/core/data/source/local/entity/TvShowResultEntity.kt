package com.example.moviecatalogue.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_show_result")
data class TvShowResultEntity(
    var backdropPath: String?,
    var firstAirDate: String?,
    @PrimaryKey
    @NonNull
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
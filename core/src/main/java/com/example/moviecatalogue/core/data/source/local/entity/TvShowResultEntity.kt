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
    var overview: String?,
    var posterPath: String?,
    var textQuery: String?,
    var voteAverage: Double?,
)
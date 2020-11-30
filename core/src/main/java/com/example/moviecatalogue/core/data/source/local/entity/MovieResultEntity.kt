package com.example.moviecatalogue.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_result")
data class MovieResultEntity (
    var adult: Boolean?,
    var backdropPath: String?,
    @PrimaryKey
    @NonNull
    var id: Int?,
    var isPopular: Boolean,
    var originalLanguage: String?,
    var originalTitle: String?,
    var overview: String?,
    var popularity: Double?,
    var posterPath: String?,
    var releaseDate: String?,
    var title: String?,
    var textQuery: String?,
    var video: Boolean?,
    var voteAverage: Double?,
    var voteCount: Int?
)
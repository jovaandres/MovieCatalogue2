package com.example.moviecatalogue.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_detail")
data class MovieDetailEntity (
    var adult: Boolean?,
    var backdropPath: String?,
    var budget: Int?,
    var homepage: String?,
    @PrimaryKey
    @NonNull
    var id: Int?,
    var imdbId: String?,
    var isFavorite: Boolean,
    var originalLanguage: String?,
    var originalTitle: String?,
    var overview: String?,
    var popularity: Double?,
    var posterPath: String?,
    var releaseDate: String?,
    var revenue: Int?,
    var runtime: Int?,
    var status: String?,
    var tagline: String?,
    var title: String?,
    var video: Boolean?,
    var voteAverage: Double?,
    var voteCount: Int?
)
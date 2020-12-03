package com.example.moviecatalogue.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_detail")
data class MovieDetailEntity (
    var backdropPath: String?,
    var homepage: String?,
    @PrimaryKey
    @NonNull
    var id: Int?,
    var isFavorite: Boolean,
    var overview: String?,
    var posterPath: String?,
    var releaseDate: String?,
    var runtime: Int?,
    var title: String?,
    var voteAverage: Double?
)
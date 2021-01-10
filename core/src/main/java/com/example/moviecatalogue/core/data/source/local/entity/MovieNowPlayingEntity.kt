package com.example.moviecatalogue.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_now_playing")
data class MovieNowPlayingEntity (
    var backdropPath: String?,
    @PrimaryKey
    @NonNull
    var id: Int?,
    var overview: String?,
    var posterPath: String?,
    var releaseDate: String?,
    var title: String?,
    var textQuery: String?,
    var voteAverage: Double?
)
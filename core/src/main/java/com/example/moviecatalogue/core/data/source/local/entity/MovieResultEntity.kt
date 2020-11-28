package com.example.moviecatalogue.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_result")
data class MovieResultEntity (
    var adult: Boolean?,
    var backdrop_path: String?,
    @PrimaryKey
    @NonNull
    var id: Int?,
    var isPopular: Boolean,
    var original_language: String?,
    var original_title: String?,
    var overview: String?,
    var popularity: Double?,
    var poster_path: String?,
    var release_date: String?,
    var title: String?,
    var textQuery: String?,
    var video: Boolean?,
    var vote_average: Double?,
    var vote_count: Int?
)
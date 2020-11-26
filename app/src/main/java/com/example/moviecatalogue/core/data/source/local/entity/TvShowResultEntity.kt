package com.example.moviecatalogue.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_show_result")
data class TvShowResultEntity(
    var backdrop_path: String?,
    var first_air_date: String?,
    @PrimaryKey
    @NonNull
    var id: Int?,
    var isPopular: Boolean,
    var title: String?,
    var original_language: String?,
    var original_name: String?,
    var overview: String?,
    var popularity: Double?,
    var poster_path: String?,
    var textQuery: String?,
    var vote_average: Double?,
    var vote_count: Int?
)
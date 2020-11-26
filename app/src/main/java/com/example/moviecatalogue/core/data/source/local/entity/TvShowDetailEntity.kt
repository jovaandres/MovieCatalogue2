package com.example.moviecatalogue.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_show_detail")
data class TvShowDetailEntity (
    var backdrop_path: String?,
    var first_air_date: String?,
    var homepage: String?,
    @PrimaryKey
    @NonNull
    var id: Int?,
    var in_production: Boolean?,
    var isFavorite: Boolean,
    var last_air_date: String?,
    var title: String?,
    var number_of_episodes: Int?,
    var number_of_seasons: Int?,
    var original_language: String?,
    var original_name: String?,
    var overview: String?,
    var popularity: Double?,
    var poster_path: String?,
    var status: String?,
    var type: String?,
    var vote_average: Double?,
    var vote_count: Int?
)
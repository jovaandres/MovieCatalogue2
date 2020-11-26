package com.example.moviecatalogue.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var adult: Boolean?,
    var backdrop_path: String?,
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
): Parcelable

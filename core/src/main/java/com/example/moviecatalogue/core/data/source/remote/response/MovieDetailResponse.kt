package com.example.moviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("backdrop_path")
    var backdropPath: String?,

    @SerializedName("homepage")
    var homepage: String?,

    @SerializedName("id")
    var id: Int?,

    @SerializedName("overview")
    var overview: String?,

    @SerializedName("poster_path")
    var posterPath: String?,

    @SerializedName("release_date")
    var releaseDate: String?,

    @SerializedName("runtime")
    var runtime: Int?,

    @SerializedName("title")
    var title: String?,

    @SerializedName("vote_average")
    var voteAverage: Double?,
)
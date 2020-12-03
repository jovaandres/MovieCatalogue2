package com.example.moviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResultResponse(
    @SerializedName("backdrop_path")
    var backdropPath: String?,

    @SerializedName("id")
    var id: Int?,

    @SerializedName("overview")
    var overview: String?,

    @SerializedName("poster_path")
    var posterPath: String?,

    @SerializedName("release_date")
    var releaseDate: String?,

    @SerializedName("title")
    var title: String?,

    @SerializedName("vote_average")
    var voteAverage: Double?,
)
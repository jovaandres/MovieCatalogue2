package com.example.moviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowResultResponse(
    @SerializedName("backdrop_path")
    var backdropPath: String?,

    @SerializedName("first_air_date")
    var firstAirDate: String?,

    @SerializedName("id")
    var id: Int?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("overview")
    var overview: String?,

    @SerializedName("poster_path")
    var posterPath: String?,

    @SerializedName("vote_average")
    var voteAverage: Double?,
)
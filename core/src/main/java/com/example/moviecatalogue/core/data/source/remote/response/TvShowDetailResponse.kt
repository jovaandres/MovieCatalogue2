package com.example.moviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowDetailResponse(
    @SerializedName("backdrop_path")
    var backdropPath: String?,

    @SerializedName("first_air_date")
    var firstAirDate: String?,

    @SerializedName("homepage")
    var homepage: String?,

    @SerializedName("id")
    var id: Int?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("number_of_episodes")
    var numberOfEpisodes: Int?,

    @SerializedName("number_of_seasons")
    var numberOfSeasons: Int?,

    @SerializedName("overview")
    var overview: String?,

    @SerializedName("poster_path")
    var posterPath: String?,

    @SerializedName("vote_average")
    var voteAverage: Double?,
)
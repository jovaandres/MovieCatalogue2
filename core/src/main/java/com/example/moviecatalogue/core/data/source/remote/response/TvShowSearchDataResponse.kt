package com.example.moviecatalogue.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowSearchDataResponse(
    @SerializedName("page")
    var page: Int?,

    @SerializedName("results")
    var results: List<TvShowResultResponse>,

    @SerializedName("total_pages")
    var totalPages: Int?,
)
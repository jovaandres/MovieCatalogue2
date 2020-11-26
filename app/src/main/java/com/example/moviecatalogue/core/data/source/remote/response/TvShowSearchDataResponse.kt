package com.example.moviecatalogue.core.data.source.remote.response

data class TvShowSearchDataResponse(
    var page: Int?,
    var results: List<TvShowResultResponse>,
    var total_pages: Int?,
    var total_results: Int?
)
package com.example.moviecatalogue.core.data.source.remote.response

data class MovieSearchDataResponse(
    var page: Int?,
    var results: List<MovieResultResponse>,
    var total_pages: Int?,
    var total_results: Int?
)
package com.example.moviecatalogue.core.data.source.remote.api

import com.example.moviecatalogue.core.data.source.remote.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBService {

    @GET("discover/movie")
    suspend fun getPopularMovieAsync(
        @Query("api_key") key: String,
        @Query("page") page: Int = 1
    ): MovieSearchDataResponse

    @GET("discover/tv")
    suspend fun getPopularTvAsync(
        @Query("api_key") key: String,
        @Query("page") page: Int = 1
    ): TvShowSearchDataResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovieAsync(
        @Query("api_key") key: String,
        @Query("page") page: Int = 1
    ): MovieSearchDataResponse

    @GET("search/movie")
    suspend fun searchMovieAsync(
        @Query("api_key") key: String,
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): MovieSearchDataResponse

    @GET("search/tv")
    suspend fun searchTvShowAsync(
        @Query("api_key") key: String,
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): TvShowSearchDataResponse

    @GET("movie/{id}")
    suspend fun detailMovieAsync(
        @Path("id") id: Int,
        @Query("api_key") key: String
    ): MovieDetailResponse

    @GET("tv/{id}")
    suspend fun detailTvShowAsync(
        @Path("id") id: Int,
        @Query("api_key") key: String
    ): TvShowDetailResponse

    @GET("authentication/guest_session/new")
    fun getSession(@Query("api_key") key: String): Call<SessionResponse>

}
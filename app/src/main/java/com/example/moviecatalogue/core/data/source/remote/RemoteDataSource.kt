package com.example.moviecatalogue.core.data.source.remote

import android.content.Context
import com.example.moviecatalogue.core.data.source.remote.api.ApiResponse
import com.example.moviecatalogue.core.data.source.remote.api.TheMovieDBService
import com.example.moviecatalogue.core.data.source.remote.response.MovieDetailResponse
import com.example.moviecatalogue.core.data.source.remote.response.MovieSearchDataResponse
import com.example.moviecatalogue.core.data.source.remote.response.TvShowDetailResponse
import com.example.moviecatalogue.core.data.source.remote.response.TvShowSearchDataResponse
import com.example.moviecatalogue.core.utils.Constant.API_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    val theMovieDBService: TheMovieDBService,
    @ApplicationContext val context: Context
) {

    suspend fun getPopularMovie(): Flow<ApiResponse<MovieSearchDataResponse>> {
        return flow {
            try {
                val response = theMovieDBService.getPopularMovieAsync(API_KEY)
                if (response.results.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPopularTvShow(): Flow<ApiResponse<TvShowSearchDataResponse>> {
        return flow {
            try {
                val response = theMovieDBService.getPopularTvAsync(API_KEY)
                if (response.results.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchMovieFromApi(query: String): Flow<ApiResponse<MovieSearchDataResponse>> {
        return flow {
            try {
                val response = theMovieDBService.searchMovieAsync(API_KEY, query)
                if (response.results.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchTvShowFromApi(query: String): Flow<ApiResponse<TvShowSearchDataResponse>> {
        return flow {
            try {
                val response = theMovieDBService.searchTvShowAsync(API_KEY, query)
                if (response.results.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailMovie(id: String): Flow<ApiResponse<MovieDetailResponse>> {
        return flow {
            try {
                val response = theMovieDBService.detailMovieAsync(id.toInt(), API_KEY)
                if (response.id != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailTvShow(id: String): Flow<ApiResponse<TvShowDetailResponse>> {
        return flow {
            try {
                val response = theMovieDBService.detailTvShowAsync(id.toInt(), API_KEY)
                if (response.id != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}
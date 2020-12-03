package com.example.moviecatalogue.core.data

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.example.moviecatalogue.core.R
import com.example.moviecatalogue.core.data.source.local.LocalDataSource
import com.example.moviecatalogue.core.data.source.remote.RemoteDataSource
import com.example.moviecatalogue.core.data.source.remote.api.ApiResponse
import com.example.moviecatalogue.core.data.source.remote.response.MovieDetailResponse
import com.example.moviecatalogue.core.data.source.remote.response.MovieSearchDataResponse
import com.example.moviecatalogue.core.data.source.remote.response.TvShowDetailResponse
import com.example.moviecatalogue.core.data.source.remote.response.TvShowSearchDataResponse
import com.example.moviecatalogue.core.domain.model.DetailMovie
import com.example.moviecatalogue.core.domain.model.DetailTvShow
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.domain.model.TvShow
import com.example.moviecatalogue.core.domain.repository.IMovieCatalogueRepository
import com.example.moviecatalogue.core.utils.DataMapper
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieCatalogueRepository @Inject constructor(
    @ApplicationContext val app: Context,
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource
) : IMovieCatalogueRepository {

    override fun getPopularMovie(
        simpleQuery: String,
        sort: String
    ): Flow<Resource<List<Movie>>> {
        return object :
            NetworkBoundResource<List<Movie>, MovieSearchDataResponse>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getPopularMovie(simpleQuery, sort)
                    .map { DataMapper.mapMovieEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return (data == null || data.isEmpty()) && isConnected()
            }

            override suspend fun createCall(): Flow<ApiResponse<MovieSearchDataResponse>> {
                return remoteDataSource.getPopularMovie()
            }

            override suspend fun saveCallResult(data: MovieSearchDataResponse) {
                val popularMovie = DataMapper.mapMovieResponseToEntities(data.results, true, null)
                localDataSource.insertSearchedMovie(popularMovie)
            }
        }.asFlow()
    }

    override fun getPopularTv(
        simpleQuery: String,
        sort: String
    ): Flow<Resource<List<TvShow>>> {
        return object :
            NetworkBoundResource<List<TvShow>, TvShowSearchDataResponse>() {
            override fun loadFromDB(): Flow<List<TvShow>> {
                return localDataSource.getPopularTvShow(simpleQuery, sort)
                    .map { DataMapper.mapTvShowEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<TvShow>?): Boolean {
                return (data == null || data.isEmpty()) && isConnected()
            }

            override suspend fun createCall(): Flow<ApiResponse<TvShowSearchDataResponse>> {
                return remoteDataSource.getPopularTvShow()
            }

            override suspend fun saveCallResult(data: TvShowSearchDataResponse) {
                val popularTv = DataMapper.mapTvShowResponseToEntities(data.results, true, null)
                localDataSource.insertSearchedTvShow(popularTv)
            }
        }.asFlow()
    }

    @FlowPreview
    override fun getSearchedMovie(
        title: String
    ): Flow<Resource<List<Movie>>> {
        return object :
            NetworkBoundResource<List<Movie>, MovieSearchDataResponse>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getSearchedMovie(title)
                    .map { DataMapper.mapMovieEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return isConnected()
            }

            override suspend fun createCall(): Flow<ApiResponse<MovieSearchDataResponse>> {
                return remoteDataSource.searchMovieFromApi(title)
            }

            override suspend fun saveCallResult(data: MovieSearchDataResponse) {
                val movieResult = DataMapper.mapMovieResponseToEntities(data.results, false, title)
                localDataSource.insertSearchedMovie(movieResult)
            }
        }.asFlow()
    }

    @FlowPreview
    override fun getSearchedTvShow(
        title: String
    ): Flow<Resource<List<TvShow>>> {
        return object :
            NetworkBoundResource<List<TvShow>, TvShowSearchDataResponse>() {
            override fun loadFromDB(): Flow<List<TvShow>> {
                return localDataSource.getSearchedTvShow(title)
                    .map { DataMapper.mapTvShowEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<TvShow>?): Boolean {
                return isConnected()
            }

            override suspend fun createCall(): Flow<ApiResponse<TvShowSearchDataResponse>> {
                return remoteDataSource.searchTvShowFromApi(title)
            }

            override suspend fun saveCallResult(data: TvShowSearchDataResponse) {
                val tvShowResult =
                    DataMapper.mapTvShowResponseToEntities(data.results, false, title)
                localDataSource.insertSearchedTvShow(tvShowResult)
            }
        }.asFlow()
    }

    override fun getDetailMovie(id: String): Flow<Resource<DetailMovie>> {
        return object : NetworkBoundResource<DetailMovie, MovieDetailResponse>() {
            override fun loadFromDB(): Flow<DetailMovie> {
                return localDataSource.getDetailMovie(id)
                    .map { DataMapper.mapMovieDetailEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: DetailMovie?): Boolean {
                return data?.id == null
            }

            override suspend fun createCall(): Flow<ApiResponse<MovieDetailResponse>> {
                return remoteDataSource.getDetailMovie(id)
            }

            override suspend fun saveCallResult(data: MovieDetailResponse) {
                val movieDetail = DataMapper.mapMovieDetailResponseToEntities(data, false)
                localDataSource.insertDetailMovie(movieDetail)
            }
        }.asFlow()
    }

    override fun getDetailTvShow(id: String): Flow<Resource<DetailTvShow>> {
        return object :
            NetworkBoundResource<DetailTvShow, TvShowDetailResponse>() {
            override fun loadFromDB(): Flow<DetailTvShow> {
                return localDataSource.getDetailTvShow(id)
                    .map { DataMapper.mapTvShowDetailEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: DetailTvShow?): Boolean {
                return data?.id == null
            }

            override suspend fun createCall(): Flow<ApiResponse<TvShowDetailResponse>> {
                return remoteDataSource.getDetailTvShow(id)
            }

            override suspend fun saveCallResult(data: TvShowDetailResponse) {
                val tvShowDetail = DataMapper.mapTvShowDetailResponseToEntities(data, false)
                localDataSource.insertDetailTvShow(tvShowDetail)
            }
        }.asFlow()
    }

    override fun getFavoriteMovie(
        simpleQuery: String,
        sort: String
    ): Flow<List<DetailMovie>> {
        return localDataSource.getFavoriteMovie(simpleQuery, sort)
            .map { DataMapper.mapMovieFavoriteEntitiesToDomain(it) }
    }

    override fun getFavoriteTvShow(
        simpleQuery: String,
        sort: String
    ): Flow<List<DetailTvShow>> {
        return localDataSource.getFavoriteTvShow(simpleQuery, sort)
            .map { DataMapper.mapTvShowFavoriteEntitiesToDomain(it) }
    }

    override fun insertFavoriteMovie(detailMovie: DetailMovie, newState: Boolean) {
        val movie = DataMapper.mapMovieDetailDomainToEntity(detailMovie, newState)
        GlobalScope.launch(Dispatchers.IO) {
            localDataSource.insertFavoriteMovie(movie, newState)
        }
    }

    override fun insertFavoriteTvShow(detailTvShow: DetailTvShow, newState: Boolean) {
        val tvShow = DataMapper.mapTvShowDetailDomainToEntity(detailTvShow, newState)
        GlobalScope.launch(Dispatchers.IO) {
            localDataSource.insertFavoriteTvShow(tvShow, newState)
        }
    }

    fun isConnected(): Boolean {
        try {
            val cm = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm.activeNetwork == null) {
                FancyToast.makeText(
                    app,
                    app.getString(R.string.connection_error),
                    FancyToast.LENGTH_SHORT,
                    FancyToast.ERROR,
                    false
                ).show()
            }
            return cm.activeNetwork != null
        } catch (e: Exception) {
            Log.e("Connectivity Exception", e.message.toString())
        }
        return false
    }
}
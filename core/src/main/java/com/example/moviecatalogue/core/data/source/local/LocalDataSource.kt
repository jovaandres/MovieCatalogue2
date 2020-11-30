package com.example.moviecatalogue.core.data.source.local

import com.example.moviecatalogue.core.data.source.local.entity.MovieDetailEntity
import com.example.moviecatalogue.core.data.source.local.entity.MovieResultEntity
import com.example.moviecatalogue.core.data.source.local.entity.TvShowDetailEntity
import com.example.moviecatalogue.core.data.source.local.entity.TvShowResultEntity
import com.example.moviecatalogue.core.data.source.local.room.MovieCatalogueDao
import com.example.moviecatalogue.core.utils.SortUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(val mMovieCatalogueDao: MovieCatalogueDao) {

    fun getPopularMovie(
        simpleQuery: String,
        sort: String
    ): Flow<List<MovieResultEntity>> {
        val query = SortUtils.getSortedQuery(simpleQuery, sort)
        return mMovieCatalogueDao.getPopularMovie(query)
    }

    fun getPopularTvShow(
        simpleQuery: String,
        sort: String
    ): Flow<List<TvShowResultEntity>> {
        val query = SortUtils.getSortedQuery(simpleQuery, sort)
        return mMovieCatalogueDao.getPopularTvShow(query)
    }

    fun getSearchedMovie(
        title: String
    ): Flow<List<MovieResultEntity>> {
        return mMovieCatalogueDao.getSearchedMovie(title)
    }

    fun getSearchedTvShow(
        title: String
    ): Flow<List<TvShowResultEntity>> {
        return mMovieCatalogueDao.getSearchedTvShow(title)
    }

    fun getFavoriteMovie(
        simpleQuery: String,
        sort: String
    ): Flow<List<MovieDetailEntity>> {
        val query = SortUtils.getSortedQuery(simpleQuery, sort)
        return mMovieCatalogueDao.getFavoriteMovie(query)
    }

    fun getFavoriteTvShow(
        simpleQuery: String,
        sort: String
    ): Flow<List<TvShowDetailEntity>> {
        val query = SortUtils.getSortedQuery(simpleQuery, sort)
        return mMovieCatalogueDao.getFavoriteTvShow(query)
    }

    fun getDetailMovie(id: String): Flow<MovieDetailEntity> =
        mMovieCatalogueDao.getDetailMovie(id.toInt())

    fun getDetailTvShow(id: String): Flow<TvShowDetailEntity> =
        mMovieCatalogueDao.getDetailTvShow(id.toInt())

    suspend fun insertSearchedMovie(movieResultEntity: List<MovieResultEntity>) {
        mMovieCatalogueDao.insertSearchedMovie(movieResultEntity)
    }

    suspend fun insertSearchedTvShow(tvShowResultEntity: List<TvShowResultEntity>) =
        mMovieCatalogueDao.insertSearchedTvShow(tvShowResultEntity)

    suspend fun insertDetailMovie(movieDetailEntity: MovieDetailEntity) =
        mMovieCatalogueDao.insertDetailMovie(movieDetailEntity)

    suspend fun insertDetailTvShow(tvShowDetailEntity: TvShowDetailEntity) =
        mMovieCatalogueDao.insertDetailTvShow(tvShowDetailEntity)

    suspend fun insertFavoriteMovie(movieDetailEntity: MovieDetailEntity, newState: Boolean) {
        movieDetailEntity.isFavorite = newState
        mMovieCatalogueDao.insertFavoriteMovie(movieDetailEntity)
    }

    suspend fun insertFavoriteTvShow(tvShowDetailEntity: TvShowDetailEntity, newState: Boolean) {
        tvShowDetailEntity.isFavorite = newState
        mMovieCatalogueDao.insertFavoriteTvShow(tvShowDetailEntity)
    }
}
package com.example.moviecatalogue.core.domain.usecase

import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.DetailMovie
import com.example.moviecatalogue.core.domain.model.DetailTvShow
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface MovieCatalogueUseCase {

    fun getPopularMovie(): Flow<Resource<List<Movie>>>

    fun getPopularTv(): Flow<Resource<List<TvShow>>>

    fun getNowPlayingMovie(): Flow<Resource<List<Movie>>>

    fun getSearchedMovie(
        title: String
    ): Flow<Resource<List<Movie>>>

    fun getSearchedTvShow(
        title: String
    ): Flow<Resource<List<TvShow>>>

    fun getFavoriteMovie(
        simpleQuery: String,
        sort: String
    ): Flow<List<DetailMovie>>

    fun getFavoriteTvShow(
        simpleQuery: String,
        sort: String
    ): Flow<List<DetailTvShow>>

    fun getDetailMovie(id: String): Flow<Resource<DetailMovie>>

    fun getDetailTvShow(id: String): Flow<Resource<DetailTvShow>>

    fun insertFavoriteMovie(detailMovie: DetailMovie, newState: Boolean)

    fun insertFavoriteTvShow(detailTvShow: DetailTvShow, newState: Boolean)
}
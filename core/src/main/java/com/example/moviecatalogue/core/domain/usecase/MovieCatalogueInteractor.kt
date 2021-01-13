package com.example.moviecatalogue.core.domain.usecase

import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.DetailMovie
import com.example.moviecatalogue.core.domain.model.DetailTvShow
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.domain.model.TvShow
import com.example.moviecatalogue.core.domain.repository.IMovieCatalogueRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieCatalogueInteractor @Inject constructor(val movieCatalogueRepository: IMovieCatalogueRepository) :
    MovieCatalogueUseCase {

    override fun getPopularMovie(): Flow<Resource<List<Movie>>> =
        movieCatalogueRepository.getPopularMovie()


    override fun getPopularTv(): Flow<Resource<List<TvShow>>> =
        movieCatalogueRepository.getPopularTv()

    override fun getNowPlayingMovie(): Flow<Resource<List<Movie>>> =
        movieCatalogueRepository.getNowPlayingMovie()

    override fun getSearchedMovie(title: String): Flow<Resource<List<Movie>>> =
        movieCatalogueRepository.getSearchedMovie(title)


    override fun getSearchedTvShow(title: String): Flow<Resource<List<TvShow>>> =
        movieCatalogueRepository.getSearchedTvShow(title)


    override fun getFavoriteMovie(
        simpleQuery: String,
        sort: String
    ): Flow<List<DetailMovie>> =
        movieCatalogueRepository.getFavoriteMovie(simpleQuery, sort)


    override fun getFavoriteTvShow(
        simpleQuery: String,
        sort: String
    ): Flow<List<DetailTvShow>> =
        movieCatalogueRepository.getFavoriteTvShow(simpleQuery, sort)


    override fun getDetailMovie(id: String): Flow<Resource<DetailMovie>> =
        movieCatalogueRepository.getDetailMovie(id)


    override fun getDetailTvShow(id: String): Flow<Resource<DetailTvShow>> =
        movieCatalogueRepository.getDetailTvShow(id)


    override fun insertFavoriteMovie(detailMovie: DetailMovie, newState: Boolean) {
        movieCatalogueRepository.insertFavoriteMovie(detailMovie, newState)
    }

    override fun insertFavoriteTvShow(detailTvShow: DetailTvShow, newState: Boolean) {
        movieCatalogueRepository.insertFavoriteTvShow(detailTvShow, newState)
    }
}
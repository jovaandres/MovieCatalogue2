package com.example.moviecatalogue.ui.favorite.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecatalogue.core.domain.model.DetailMovie
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase

class FavoriteMovieViewModel @ViewModelInject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    fun showFavoriteMovie(simpleQuery: String, sort: String): LiveData<List<DetailMovie>> =
        movieCatalogueUseCase.getFavoriteMovie(simpleQuery, sort).asLiveData()

    fun addToFavoriteMovie(detailMovie: DetailMovie) {
        val newState = !detailMovie.isFavorite!!
        movieCatalogueUseCase.insertFavoriteMovie(detailMovie, newState)
    }
}
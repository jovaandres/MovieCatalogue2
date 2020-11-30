package com.example.moviecatalogue.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecatalogue.core.domain.model.DetailMovie
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import javax.inject.Inject

class FavoriteMovieViewModel @Inject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    fun showFavoriteMovie(simpleQuery: String, sort: String): LiveData<List<DetailMovie>> =
        movieCatalogueUseCase.getFavoriteMovie(simpleQuery, sort).asLiveData()

    fun addToFavoriteMovie(detailMovie: DetailMovie, newState: Boolean) {
        movieCatalogueUseCase.insertFavoriteMovie(detailMovie, newState)
    }
}
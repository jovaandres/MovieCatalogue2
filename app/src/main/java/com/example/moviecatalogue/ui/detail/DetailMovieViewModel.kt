package com.example.moviecatalogue.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.DetailMovie
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase

class DetailMovieViewModel @ViewModelInject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    fun getDetailMovie(id: String): LiveData<Resource<DetailMovie>> =
        movieCatalogueUseCase.getDetailMovie(id).asLiveData()

    fun addToFavoriteMovie(detailMovie: DetailMovie) {
        val newState = !detailMovie.isFavorite!!
        movieCatalogueUseCase.insertFavoriteMovie(detailMovie, newState)
    }
}
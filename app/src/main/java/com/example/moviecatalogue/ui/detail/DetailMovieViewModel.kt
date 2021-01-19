package com.example.moviecatalogue.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.DetailMovie
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailMovieViewModel @ViewModelInject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    private val _detailMovie = MutableStateFlow<Resource<DetailMovie>>(Resource.Init())
    val detailMovie: StateFlow<Resource<DetailMovie>> get() = _detailMovie

    fun getDetailMovie(id: String) {
        viewModelScope.launch {
            movieCatalogueUseCase.getDetailMovie(id)
                .collect { _detailMovie.value = it }
        }
    }

    fun addToFavoriteMovie(detailMovie: DetailMovie) {
        val newState = detailMovie.isFavorite ?: false
        movieCatalogueUseCase.insertFavoriteMovie(detailMovie, !newState)
    }
}
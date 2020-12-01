package com.example.moviecatalogue.favorite.movie

import androidx.lifecycle.*
import com.example.moviecatalogue.core.domain.model.DetailMovie
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteMovieViewModel @Inject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    private val _favoriteMovie = MutableLiveData<List<DetailMovie>>()

    val favoriteMovie: LiveData<List<DetailMovie>> = _favoriteMovie

    fun showFavoriteMovie(simpleQuery: String, sort: String) {
        viewModelScope.launch {
            _favoriteMovie.value = movieCatalogueUseCase.getFavoriteMovie(simpleQuery, sort)
                .filter { it.isNotEmpty() }
                .first()
        }
    }

    fun addToFavoriteMovie(detailMovie: DetailMovie, newState: Boolean) {
        movieCatalogueUseCase.insertFavoriteMovie(detailMovie, newState)
    }
}
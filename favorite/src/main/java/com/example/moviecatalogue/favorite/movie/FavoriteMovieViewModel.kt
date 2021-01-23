package com.example.moviecatalogue.favorite.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.DetailMovie
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import com.example.moviecatalogue.presentation.model.DataDetailMovie
import com.example.moviecatalogue.utils.DataMapper.mapDataDetailMovieToDetailMovie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteMovieViewModel @Inject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    private val _favoriteMovies = MutableStateFlow<Resource<List<DetailMovie>>>(Resource.Init())

    val favoriteMovies: StateFlow<Resource<List<DetailMovie>>> = _favoriteMovies

    fun showFavoriteMovie(simpleQuery: String, sort: String) {
        viewModelScope.launch {
            movieCatalogueUseCase.getFavoriteMovie(simpleQuery, sort)
                .collect { _favoriteMovies.value = Resource.Success(it) }
        }
    }

    fun addToFavoriteMovie(detailMovie: DataDetailMovie, newState: Boolean) {
        val movie = mapDataDetailMovieToDetailMovie(detailMovie)
        movieCatalogueUseCase.insertFavoriteMovie(movie, newState)
    }
}
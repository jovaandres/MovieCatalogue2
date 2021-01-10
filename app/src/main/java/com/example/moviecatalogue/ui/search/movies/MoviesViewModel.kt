package com.example.moviecatalogue.ui.search.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.domain.model.TvShow
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MoviesViewModel @ViewModelInject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    private val _searchMovie = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())
    private val _searchTvShow = MutableStateFlow<Resource<List<TvShow>>>(Resource.Loading())

    val searchMovie: StateFlow<Resource<List<Movie>>> = _searchMovie
    val searchTvShow: StateFlow<Resource<List<TvShow>>> = _searchTvShow

    fun getMovies(title: String) {
        viewModelScope.launch {
            movieCatalogueUseCase.getSearchedMovie(title)
                .collect { _searchMovie.value = it }
        }
    }

    fun getTvShows(title: String) {
        viewModelScope.launch {
            movieCatalogueUseCase.getSearchedTvShow(title)
                .collect { _searchTvShow.value = it }
        }
    }
}
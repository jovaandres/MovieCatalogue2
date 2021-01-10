package com.example.moviecatalogue.ui.popular.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class PopularMoviesViewModel @ViewModelInject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    private val _popularMovies = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())
    private val _nowPlayingMovies = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())

    val popularMovies: StateFlow<Resource<List<Movie>>> = _popularMovies
    val nowPlayingMovies: StateFlow<Resource<List<Movie>>> = _nowPlayingMovies

    fun getPopularMovies(simpleQuery: String, sort: String) {
        viewModelScope.launch {
            movieCatalogueUseCase.getPopularMovie(simpleQuery, sort)
                .collect { _popularMovies.value = it }
        }
    }

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            movieCatalogueUseCase.getNowPlayingMovie()
                .collect { _nowPlayingMovies.value = it }
        }
    }
}
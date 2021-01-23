package com.example.moviecatalogue.ui.popular.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import com.example.moviecatalogue.utils.ListMovie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PopularMoviesViewModel @ViewModelInject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    private val _popularMovies = MutableStateFlow<ListMovie>(Resource.Init())
    private val _nowPlayingMovies = MutableStateFlow<ListMovie>(Resource.Init())

    val popularMovies: StateFlow<ListMovie> get() = _popularMovies
    val nowPlayingMovies: StateFlow<ListMovie> get() = _nowPlayingMovies

    fun getPopularMovies() {
        viewModelScope.launch {
            movieCatalogueUseCase.getPopularMovie()
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
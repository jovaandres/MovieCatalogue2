package com.example.moviecatalogue.ui.search.movies

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.domain.model.TvShow
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MoviesViewModel @ViewModelInject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    private val _searchMovie = MutableStateFlow<Resource<List<Movie>>>(Resource.Init())
    private val _searchTvShow = MutableStateFlow<Resource<List<TvShow>>>(Resource.Init())
    val query = MutableStateFlow("")

    val searchMovie: StateFlow<Resource<List<Movie>>> get() = _searchMovie
    val searchTvShow: StateFlow<Resource<List<TvShow>>> get() = _searchTvShow

    fun getMovieAndTv(title: String) {
        Log.d("TAG", title + "---" + query.value)
        if (query.value != title) {
            viewModelScope.launch {
                movieCatalogueUseCase.getSearchedMovie(title)
                    .collect { _searchMovie.value = it }
            }
            viewModelScope.launch {
                movieCatalogueUseCase.getSearchedTvShow(title)
                    .collect { _searchTvShow.value = it }
            }
        }
    }
}
package com.example.moviecatalogue.ui.popular.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PopularMoviesViewModel @ViewModelInject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    private val _popularMovies = MutableLiveData<Resource<List<Movie>>>()

    val popularMovies: LiveData<Resource<List<Movie>>> = _popularMovies

    fun getPopularMovies(simpleQuery: String, sort: String) {
        viewModelScope.launch {
            _popularMovies.value = movieCatalogueUseCase.getPopularMovie(simpleQuery, sort)
                .filter { it.data?.isNotEmpty() == true }
                .first()
        }
    }
}
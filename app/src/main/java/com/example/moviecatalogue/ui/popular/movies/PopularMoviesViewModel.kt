package com.example.moviecatalogue.ui.popular.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase

class PopularMoviesViewModel @ViewModelInject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    fun getPopularMovies(simpleQuery: String, sort: String): LiveData<Resource<List<Movie>>> =
        movieCatalogueUseCase.getPopularMovie(simpleQuery, sort).asLiveData()
}
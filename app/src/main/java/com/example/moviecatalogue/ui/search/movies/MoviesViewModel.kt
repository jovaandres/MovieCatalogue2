package com.example.moviecatalogue.ui.search.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase

class MoviesViewModel @ViewModelInject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    fun getMovies(title: String): LiveData<Resource<List<Movie>>> =
        movieCatalogueUseCase.getSearchedMovie(title).asLiveData()
}
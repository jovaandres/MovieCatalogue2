package com.example.moviecatalogue.ui.search.tvshows

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.TvShow
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase

class TvShowsViewModel @ViewModelInject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    fun getTvShows(title: String): LiveData<Resource<List<TvShow>>> =
        movieCatalogueUseCase.getSearchedTvShow(title).asLiveData()
}
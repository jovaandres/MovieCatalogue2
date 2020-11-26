package com.example.moviecatalogue.ui.popular.tvshows

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.TvShow
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase

class PopularTvShowViewModel @ViewModelInject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    fun getPopularTvShow(simpleQuery: String, sort: String): LiveData<Resource<List<TvShow>>> =
        movieCatalogueUseCase.getPopularTv(simpleQuery, sort).asLiveData()
}
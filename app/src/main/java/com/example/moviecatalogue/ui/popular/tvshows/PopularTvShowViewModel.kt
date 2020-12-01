package com.example.moviecatalogue.ui.popular.tvshows

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.TvShow
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class PopularTvShowViewModel @ViewModelInject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    private val _popularTvShows = MutableStateFlow<Resource<List<TvShow>>>(Resource.Loading())

    val popularTvShows: StateFlow<Resource<List<TvShow>>> = _popularTvShows

    fun getPopularTvShow(simpleQuery: String, sort: String) {
        viewModelScope.launch {
            movieCatalogueUseCase.getPopularTv(simpleQuery, sort)
                .collect { _popularTvShows.value = it }
        }
    }
}
package com.example.moviecatalogue.ui.search.tvshows

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
class TvShowsViewModel @ViewModelInject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    private val _searchTvShow = MutableStateFlow<Resource<List<TvShow>>>(Resource.Loading())

    val searchTvShow: StateFlow<Resource<List<TvShow>>> = _searchTvShow

    fun getTvShows(title: String) {
        viewModelScope.launch {
            movieCatalogueUseCase.getSearchedTvShow(title)
                .collect { _searchTvShow.value = it }
        }
    }
}
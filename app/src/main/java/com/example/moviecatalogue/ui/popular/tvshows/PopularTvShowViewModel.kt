package com.example.moviecatalogue.ui.popular.tvshows

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import com.example.moviecatalogue.utils.ListTv
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PopularTvShowViewModel @ViewModelInject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    private val _popularTvShows = MutableStateFlow<ListTv>(Resource.Init())

    val popularTvShows: StateFlow<ListTv> get() = _popularTvShows

    fun getPopularTvShow() {
        viewModelScope.launch {
            movieCatalogueUseCase.getPopularTv()
                .collect { _popularTvShows.value = it }
        }
    }
}
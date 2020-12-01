package com.example.moviecatalogue.ui.popular.tvshows

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.TvShow
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PopularTvShowViewModel @ViewModelInject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    private val _popularTvShows = MutableLiveData<Resource<List<TvShow>>>()

    val popularTvShows: LiveData<Resource<List<TvShow>>> = _popularTvShows

    fun getPopularTvShow(simpleQuery: String, sort: String) {
        viewModelScope.launch {
            _popularTvShows.value = movieCatalogueUseCase.getPopularTv(simpleQuery, sort)
                .filter { it.data?.isNotEmpty() == true }
                .first()
        }
    }
}
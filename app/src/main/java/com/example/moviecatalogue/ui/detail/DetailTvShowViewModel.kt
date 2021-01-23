package com.example.moviecatalogue.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.DetailTvShow
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import com.example.moviecatalogue.presentation.model.DataDetailTvShow
import com.example.moviecatalogue.utils.DataMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailTvShowViewModel @ViewModelInject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    private val _detailTv = MutableStateFlow<Resource<DetailTvShow>>(Resource.Init())
    val detailTv: StateFlow<Resource<DetailTvShow>> get() = _detailTv

    fun getDetailTvShow(id: String) {
        viewModelScope.launch {
            movieCatalogueUseCase.getDetailTvShow(id)
                .collect { _detailTv.value = it }
        }
    }

    fun addToFavoriteTvShow(detailTvShow: DataDetailTvShow) {
        val tvShow = DataMapper.mapDataDetailTvShowToDetailTvShow(detailTvShow)
        val newState = detailTvShow.isFavorite ?: false
        movieCatalogueUseCase.insertFavoriteTvShow(tvShow, !newState)
    }
}
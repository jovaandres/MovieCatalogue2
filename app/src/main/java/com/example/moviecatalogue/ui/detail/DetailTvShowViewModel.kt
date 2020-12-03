package com.example.moviecatalogue.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.DetailTvShow
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase

class DetailTvShowViewModel @ViewModelInject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    fun getDetailTvShow(id: String): LiveData<Resource<DetailTvShow>> =
        movieCatalogueUseCase.getDetailTvShow(id).asLiveData()

    fun addToFavoriteTvShow(detailTvShow: DetailTvShow) {
        val newState = detailTvShow.isFavorite ?: false
        movieCatalogueUseCase.insertFavoriteTvShow(detailTvShow, !newState)
    }
}
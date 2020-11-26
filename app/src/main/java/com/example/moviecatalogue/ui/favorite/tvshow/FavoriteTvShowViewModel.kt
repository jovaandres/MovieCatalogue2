package com.example.moviecatalogue.ui.favorite.tvshow

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecatalogue.core.domain.model.DetailTvShow
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase

class FavoriteTvShowViewModel @ViewModelInject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    fun showFavoriteTvShow(simpleQuery: String, sort: String): LiveData<List<DetailTvShow>> =
        movieCatalogueUseCase.getFavoriteTvShow(simpleQuery, sort).asLiveData()

    fun addToFavoriteTvShow(detailTvShow: DetailTvShow) {
        val newState = !detailTvShow.isFavorite!!
        movieCatalogueUseCase.insertFavoriteTvShow(detailTvShow, newState)
    }
}
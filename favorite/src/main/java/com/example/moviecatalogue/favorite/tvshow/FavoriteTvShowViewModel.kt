package com.example.moviecatalogue.favorite.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviecatalogue.core.domain.model.DetailTvShow
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import javax.inject.Inject

class FavoriteTvShowViewModel @Inject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    fun showFavoriteTvShow(simpleQuery: String, sort: String): LiveData<List<DetailTvShow>> =
        movieCatalogueUseCase.getFavoriteTvShow(simpleQuery, sort).asLiveData()

    fun addToFavoriteTvShow(detailTvShow: DetailTvShow, newState: Boolean) {
        movieCatalogueUseCase.insertFavoriteTvShow(detailTvShow, newState)
    }
}
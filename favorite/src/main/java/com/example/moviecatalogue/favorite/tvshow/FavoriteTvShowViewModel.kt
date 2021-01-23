package com.example.moviecatalogue.favorite.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.DetailTvShow
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import com.example.moviecatalogue.presentation.model.DataDetailTvShow
import com.example.moviecatalogue.utils.DataMapper.mapDataDetailTvShowToDetailTvShow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteTvShowViewModel @Inject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    private val _favoriteTvShow = MutableStateFlow<Resource<List<DetailTvShow>>>(Resource.Init())

    val favoriteTvShow: StateFlow<Resource<List<DetailTvShow>>> = _favoriteTvShow

    fun showFavoriteTvShow(simpleQuery: String, sort: String) {
        viewModelScope.launch {
            movieCatalogueUseCase.getFavoriteTvShow(simpleQuery, sort)
                .collect { _favoriteTvShow.value = Resource.Success(it) }
        }
    }

    fun addToFavoriteTvShow(detailTvShow: DataDetailTvShow, newState: Boolean) {
        val tvShow = mapDataDetailTvShowToDetailTvShow(detailTvShow)
        movieCatalogueUseCase.insertFavoriteTvShow(tvShow, newState)
    }
}
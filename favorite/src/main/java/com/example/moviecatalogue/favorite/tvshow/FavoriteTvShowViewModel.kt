package com.example.moviecatalogue.favorite.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.DetailTvShow
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteTvShowViewModel @Inject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    private val _favoriteTvShow = MutableStateFlow<Resource<List<DetailTvShow>>>(Resource.Loading())

    val favoriteTvShow: StateFlow<Resource<List<DetailTvShow>>> = _favoriteTvShow

    fun showFavoriteTvShow(simpleQuery: String, sort: String) {
        viewModelScope.launch {
            movieCatalogueUseCase.getFavoriteTvShow(simpleQuery, sort)
                .collect { _favoriteTvShow.value = Resource.Success(it) }
        }
    }

    fun addToFavoriteTvShow(detailTvShow: DetailTvShow, newState: Boolean) {
        movieCatalogueUseCase.insertFavoriteTvShow(detailTvShow, newState)
    }
}
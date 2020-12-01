package com.example.moviecatalogue.favorite.tvshow

import androidx.lifecycle.*
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.DetailTvShow
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
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
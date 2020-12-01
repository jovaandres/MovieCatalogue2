package com.example.moviecatalogue.favorite.tvshow

import androidx.lifecycle.*
import com.example.moviecatalogue.core.domain.model.DetailTvShow
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteTvShowViewModel @Inject constructor(val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModel() {

    private val _favoriteTvShow = MutableLiveData<List<DetailTvShow>>()

    val favoriteTvShow: LiveData<List<DetailTvShow>> = _favoriteTvShow

    fun showFavoriteTvShow(simpleQuery: String, sort: String) {
        viewModelScope.launch {
            _favoriteTvShow.value = movieCatalogueUseCase.getFavoriteTvShow(simpleQuery, sort)
                .filter { it.isNotEmpty() }
                .first()
        }
    }

    fun addToFavoriteTvShow(detailTvShow: DetailTvShow, newState: Boolean) {
        movieCatalogueUseCase.insertFavoriteTvShow(detailTvShow, newState)
    }
}
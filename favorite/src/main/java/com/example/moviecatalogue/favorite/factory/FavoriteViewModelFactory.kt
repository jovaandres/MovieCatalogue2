package com.example.moviecatalogue.favorite.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import com.example.moviecatalogue.favorite.movie.FavoriteMovieViewModel
import com.example.moviecatalogue.favorite.tvshow.FavoriteTvShowViewModel
import javax.inject.Inject

class FavoriteViewModelFactory @Inject constructor(private val movieCatalogueUseCase: MovieCatalogueUseCase) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass) {
            FavoriteMovieViewModel::class.java -> FavoriteMovieViewModel(movieCatalogueUseCase) as T
            FavoriteTvShowViewModel::class.java -> FavoriteTvShowViewModel(movieCatalogueUseCase) as T
            else -> throw IllegalArgumentException("Unknown class ${modelClass.name}")
        }
    }
}
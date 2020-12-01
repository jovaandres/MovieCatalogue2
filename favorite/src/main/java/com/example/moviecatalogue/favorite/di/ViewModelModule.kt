package com.example.moviecatalogue.favorite.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalogue.favorite.factory.FavoriteViewModelFactory
import com.example.moviecatalogue.favorite.movie.FavoriteMovieViewModel
import com.example.moviecatalogue.favorite.tvshow.FavoriteTvShowViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Suppress("unused")
@Module
@InstallIn(FragmentComponent::class)
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteMovieViewModel::class)
    abstract fun bindFavoriteMovieViewModel(viewModel: FavoriteMovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteTvShowViewModel::class)
    abstract fun bindFavoriteTvShowViewModel(viewModel: FavoriteTvShowViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: FavoriteViewModelFactory): ViewModelProvider.Factory
}
package com.example.moviecatalogue.favorite.di

import android.content.Context
import com.example.moviecatalogue.core.di.CoreModuleDependencies
import com.example.moviecatalogue.favorite.movie.FavoriteMovieFragment
import com.example.moviecatalogue.favorite.tvshow.FavoriteTvShowFragment
import dagger.BindsInstance
import dagger.Component

@FeatureScope
@Component(dependencies = [CoreModuleDependencies::class], modules = [ViewModelModule::class])
interface FavoriteComponent {

    fun inject(fragment: FavoriteMovieFragment)
    fun inject(fragment: FavoriteTvShowFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun coreDependencies(coreModuleDependencies: CoreModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}
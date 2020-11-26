package com.example.moviecatalogue.di

import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueInteractor
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class AppModule {

    @Binds
    abstract fun providesMovieCatalogueUseCase(movieCatalogueInteractor: MovieCatalogueInteractor): MovieCatalogueUseCase

}
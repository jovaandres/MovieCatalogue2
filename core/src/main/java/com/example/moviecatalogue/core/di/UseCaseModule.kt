package com.example.moviecatalogue.core.di

import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueInteractor
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun providesMovieCatalogueUseCase(movieCatalogueInteractor: MovieCatalogueInteractor): MovieCatalogueUseCase
}
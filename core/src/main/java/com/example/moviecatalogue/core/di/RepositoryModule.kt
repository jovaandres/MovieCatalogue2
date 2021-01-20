package com.example.moviecatalogue.core.di

import com.example.moviecatalogue.core.data.AuthenticationRepository
import com.example.moviecatalogue.core.data.MovieCatalogueRepository
import com.example.moviecatalogue.core.domain.repository.IAuthenticationRepository
import com.example.moviecatalogue.core.domain.repository.IMovieCatalogueRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module(includes = [DatabaseModule::class, NetworkModule::class])
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRepository(movieCatalogueRepository: MovieCatalogueRepository): IMovieCatalogueRepository

    @Binds
    abstract fun providesAuthentication(authenticationRepository: AuthenticationRepository): IAuthenticationRepository
}
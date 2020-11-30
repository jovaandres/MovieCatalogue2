package com.example.moviecatalogue.core.di

import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import com.example.moviecatalogue.core.utils.SortPreferences
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@EntryPoint
@InstallIn(ApplicationComponent::class)
interface CoreModuleDependencies {

    fun providesSortPreferences(): SortPreferences

    fun movieCatalogueUseCase(): MovieCatalogueUseCase
}
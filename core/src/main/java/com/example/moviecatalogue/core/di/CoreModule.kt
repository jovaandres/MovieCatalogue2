package com.example.moviecatalogue.core.di

import android.content.Context
import com.example.moviecatalogue.core.utils.SortPreferences
import com.example.moviecatalogue.core.utils.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object CoreModule {

    @Singleton
    @Provides
    fun providesSortPreferences(@ApplicationContext app: Context): SortPreferences =
        SortPreferences(app)

    @Singleton
    @Provides
    fun providesUserPreference(@ApplicationContext app: Context): UserPreferences =
        UserPreferences(app)
}
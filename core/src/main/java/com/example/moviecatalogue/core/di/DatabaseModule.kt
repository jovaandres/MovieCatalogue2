package com.example.moviecatalogue.core.di

import android.content.Context
import androidx.room.Room
import com.example.moviecatalogue.core.data.source.local.room.MovieCatalogueDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesMovieCatalogueDatabase(@ApplicationContext app: Context): MovieCatalogueDatabase =
        Room.databaseBuilder(app, MovieCatalogueDatabase::class.java, "movie_db")
            .fallbackToDestructiveMigration().build()

    @Provides
    fun providesMovieCatalogueDao(db: MovieCatalogueDatabase) = db.movieCatalogueDao()

}
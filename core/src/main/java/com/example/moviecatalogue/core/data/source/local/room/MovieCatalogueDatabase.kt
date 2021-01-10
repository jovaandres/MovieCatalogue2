package com.example.moviecatalogue.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviecatalogue.core.data.source.local.entity.*

@Database(
    entities = [MovieResultEntity::class, MovieDetailEntity::class,
        TvShowResultEntity::class, TvShowDetailEntity::class, MovieNowPlayingEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieCatalogueDatabase : RoomDatabase() {
    abstract fun movieCatalogueDao(): MovieCatalogueDao
}
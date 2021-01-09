package com.example.moviecatalogue.core.di

import android.content.Context
import androidx.room.Room
import com.example.moviecatalogue.core.data.source.local.room.MovieCatalogueDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesMovieCatalogueDatabase(@ApplicationContext app: Context): MovieCatalogueDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("aw39bbiw3".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(app, MovieCatalogueDatabase::class.java, "movie_db")
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun providesMovieCatalogueDao(db: MovieCatalogueDatabase) = db.movieCatalogueDao()

}
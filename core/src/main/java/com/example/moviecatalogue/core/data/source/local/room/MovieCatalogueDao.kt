package com.example.moviecatalogue.core.data.source.local.room

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.moviecatalogue.core.data.source.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieCatalogueDao {

    @Query("SELECT * FROM movie_result WHERE isPopular = 1")
    fun getPopularMovie(): Flow<List<MovieResultEntity>>

    @Query("SELECT * FROM tv_show_result WHERE isPopular = 1")
    fun getPopularTvShow(): Flow<List<TvShowResultEntity>>

    @Query("SELECT * FROM movie_now_playing")
    fun getNowPlayingMovie(): Flow<List<MovieNowPlayingEntity>>

    @Query("SELECT * FROM movie_result WHERE textQuery = :title")
    fun getSearchedMovie(title: String): Flow<List<MovieResultEntity>>

    @Query("SELECT * FROM tv_show_result WHERE textQuery = :title")
    fun getSearchedTvShow(title: String): Flow<List<TvShowResultEntity>>

    @RawQuery(observedEntities = [MovieDetailEntity::class])
    fun getFavoriteMovie(query: SupportSQLiteQuery): Flow<List<MovieDetailEntity>>

    @RawQuery(observedEntities = [TvShowDetailEntity::class])
    fun getFavoriteTvShow(query: SupportSQLiteQuery): Flow<List<TvShowDetailEntity>>

    @Query("SELECT * FROM movie_detail WHERE id = :id")
    fun getDetailMovie(id: Int): Flow<MovieDetailEntity>

    @Query("SELECT * FROM tv_show_detail WHERE id = :id")
    fun getDetailTvShow(id: Int): Flow<TvShowDetailEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSearchedMovie(movieResultEntity: List<MovieResultEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSearchedTvShow(tvShowResultEntity: List<TvShowResultEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNowPlayingMovie(movieNowPlayingEntity: List<MovieNowPlayingEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDetailMovie(movieDetailEntity: MovieDetailEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDetailTvShow(tvShowDetailEntity: TvShowDetailEntity)

    @Update
    suspend fun insertFavoriteMovie(movieDetailEntity: MovieDetailEntity)

    @Update
    suspend fun insertFavoriteTvShow(tvShowDetailEntity: TvShowDetailEntity)

}
package com.example.moviecatalogue.core.utils

import com.example.moviecatalogue.core.data.source.local.entity.MovieDetailEntity
import com.example.moviecatalogue.core.data.source.local.entity.MovieResultEntity
import com.example.moviecatalogue.core.data.source.local.entity.TvShowDetailEntity
import com.example.moviecatalogue.core.data.source.local.entity.TvShowResultEntity
import com.example.moviecatalogue.core.data.source.remote.response.MovieDetailResponse
import com.example.moviecatalogue.core.data.source.remote.response.MovieResultResponse
import com.example.moviecatalogue.core.data.source.remote.response.TvShowDetailResponse
import com.example.moviecatalogue.core.data.source.remote.response.TvShowResultResponse
import com.example.moviecatalogue.core.domain.model.DetailMovie
import com.example.moviecatalogue.core.domain.model.DetailTvShow
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.domain.model.TvShow

object DataMapper {
    fun mapMovieResponseToEntities(
        input: List<MovieResultResponse>,
        isPopular: Boolean,
        textQuery: String?
    ): List<MovieResultEntity> {
        val movieList = ArrayList<MovieResultEntity>()
        input.map {
            val movie = MovieResultEntity(
                backdropPath = it.backdropPath,
                id = it.id,
                isPopular = isPopular,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                title = it.title,
                textQuery = textQuery,
                voteAverage = it.voteAverage,
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapMovieEntitiesToDomain(
        input: List<MovieResultEntity>
    ): List<Movie> =
        input.map {
            Movie(
                backdropPath = it.backdropPath,
                id = it.id,
                isPopular = it.isPopular,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                title = it.title,
                textQuery = it.textQuery,
                voteAverage = it.voteAverage,
            )
        }

    fun mapTvShowResponseToEntities(
        input: List<TvShowResultResponse>,
        isPopular: Boolean,
        textQuery: String?
    ): List<TvShowResultEntity> {
        val tvList = ArrayList<TvShowResultEntity>()
        input.map {
            val tvShow = TvShowResultEntity(
                backdropPath = it.backdropPath,
                firstAirDate = it.firstAirDate,
                id = it.id,
                isPopular = isPopular,
                title = it.name,
                overview = it.overview,
                posterPath = it.posterPath,
                textQuery = textQuery,
                voteAverage = it.voteAverage,
            )
            tvList.add(tvShow)
        }
        return tvList
    }

    fun mapTvShowEntitiesToDomain(
        input: List<TvShowResultEntity>
    ): List<TvShow> =
        input.map {
            TvShow(
                backdropPath = it.backdropPath,
                firstAirDate = it.firstAirDate,
                id = it.id,
                isPopular = it.isPopular,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                textQuery = it.textQuery,
                voteAverage = it.voteAverage,
            )
        }

    fun mapMovieDetailResponseToEntities(
        it: MovieDetailResponse,
        isFavorite: Boolean
    ): MovieDetailEntity =
        MovieDetailEntity(
            backdropPath = it.backdropPath,
            homepage = it.homepage,
            id = it.id,
            isFavorite = isFavorite,
            overview = it.overview,
            posterPath = it.posterPath,
            releaseDate = it.releaseDate,
            runtime = it.runtime,
            title = it.title,
            voteAverage = it.voteAverage,
        )

    fun mapMovieDetailEntitiesToDomain(
        it: MovieDetailEntity?
    ): DetailMovie =
        DetailMovie(
            backdropPath = it?.backdropPath,
            homepage = it?.homepage,
            id = it?.id,
            isFavorite = it?.isFavorite,
            overview = it?.overview,
            posterPath = it?.posterPath,
            releaseDate = it?.releaseDate,
            runtime = it?.runtime,
            title = it?.title,
            voteAverage = it?.voteAverage
        )

    fun mapMovieFavoriteEntitiesToDomain(
        input: List<MovieDetailEntity>
    ): List<DetailMovie> {
        val movieList = ArrayList<DetailMovie>()
        input.map {
            val movie = DetailMovie(
                backdropPath = it.backdropPath,
                homepage = it.homepage,
                id = it.id,
                isFavorite = it.isFavorite,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                runtime = it.runtime,
                title = it.title,
                voteAverage = it.voteAverage,
            )
            movieList.add(movie)
        }
        return movieList
    }


    fun mapMovieDetailDomainToEntity(input: DetailMovie, isFavorite: Boolean): MovieDetailEntity =
        MovieDetailEntity(
            backdropPath = input.backdropPath,
            homepage = input.homepage,
            id = input.id,
            isFavorite = isFavorite,
            overview = input.overview,
            posterPath = input.posterPath,
            releaseDate = input.releaseDate,
            runtime = input.runtime,
            title = input.title,
            voteAverage = input.voteAverage
        )

    fun mapTvShowDetailResponseToEntities(
        it: TvShowDetailResponse,
        isFavorite: Boolean
    ): TvShowDetailEntity =
        TvShowDetailEntity(
            backdropPath = it.backdropPath,
            firstAirDate = it.firstAirDate,
            homepage = it.homepage,
            id = it.id,
            isFavorite = isFavorite,
            title = it.name,
            numberOfEpisodes = it.numberOfEpisodes,
            numberOfSeasons = it.numberOfSeasons,
            overview = it.overview,
            posterPath = it.posterPath,
            voteAverage = it.voteAverage
        )

    fun mapTvShowDetailEntitiesToDomain(
        it: TvShowDetailEntity?
    ): DetailTvShow =
        DetailTvShow(
            backdropPath = it?.backdropPath,
            firstAirDate = it?.firstAirDate,
            homepage = it?.homepage,
            id = it?.id,
            isFavorite = it?.isFavorite,
            title = it?.title,
            numberOfEpisodes = it?.numberOfEpisodes,
            numberOfSeasons = it?.numberOfSeasons,
            overview = it?.overview,
            posterPath = it?.posterPath,
            voteAverage = it?.voteAverage
        )

    fun mapTvShowFavoriteEntitiesToDomain(
        input: List<TvShowDetailEntity>
    ): List<DetailTvShow> {
        val tvShowList = ArrayList<DetailTvShow>()
        input.map {
            val tvShow = DetailTvShow(
                backdropPath = it.backdropPath,
                firstAirDate = it.firstAirDate,
                homepage = it.homepage,
                id = it.id,
                isFavorite = it.isFavorite,
                title = it.title,
                numberOfEpisodes = it.numberOfEpisodes,
                numberOfSeasons = it.numberOfSeasons,
                overview = it.overview,
                posterPath = it.posterPath,
                voteAverage = it.voteAverage
            )
            tvShowList.add(tvShow)
        }
        return tvShowList
    }

    fun mapTvShowDetailDomainToEntity(input: DetailTvShow, isFavorite: Boolean): TvShowDetailEntity =
        TvShowDetailEntity(
            backdropPath = input.backdropPath,
            firstAirDate = input.firstAirDate,
            homepage = input.homepage,
            id = input.id,
            isFavorite = isFavorite,
            title = input.title,
            numberOfEpisodes = input.numberOfEpisodes,
            numberOfSeasons = input.numberOfSeasons,
            overview = input.overview,
            posterPath = input.posterPath,
            voteAverage = input.voteAverage
        )

}
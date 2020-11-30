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
                adult = it.adult,
                backdropPath = it.backdropPath,
                id = it.id,
                isPopular = isPopular,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                overview = it.overview,
                popularity = it.popularity,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                title = it.title,
                textQuery = textQuery,
                video = it.video,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount
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
                adult = it.adult,
                backdropPath = it.backdropPath,
                id = it.id,
                isPopular = it.isPopular,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                overview = it.overview,
                popularity = it.popularity,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                title = it.title,
                textQuery = it.textQuery,
                video = it.video,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount
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
                originalLanguage = it.originalLanguage,
                originalName = it.originalName,
                overview = it.overview,
                popularity = it.popularity,
                posterPath = it.posterPath,
                textQuery = textQuery,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount
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
                originalLanguage = it.originalLanguage,
                originalName = it.originalName,
                overview = it.overview,
                popularity = it.popularity,
                posterPath = it.posterPath,
                textQuery = it.textQuery,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount
            )
        }

    fun mapMovieDetailResponseToEntities(
        it: MovieDetailResponse,
        isFavorite: Boolean
    ): MovieDetailEntity =
        MovieDetailEntity(
            adult = it.adult,
            backdropPath = it.backdropPath,
            budget = it.budget,
            homepage = it.homepage,
            id = it.id,
            imdbId = it.imdbId,
            isFavorite = isFavorite,
            originalLanguage = it.originalLanguage,
            originalTitle = it.originalTitle,
            overview = it.overview,
            popularity = it.popularity,
            posterPath = it.posterPath,
            releaseDate = it.releaseDate,
            revenue = it.revenue,
            runtime = it.runtime,
            status = it.status,
            tagline = it.tagline,
            title = it.title,
            video = it.video,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount
        )

    fun mapMovieDetailEntitiesToDomain(
        it: MovieDetailEntity?
    ): DetailMovie =
        DetailMovie(
            adult = it?.adult,
            backdropPath = it?.backdropPath,
            budget = it?.budget,
            homepage = it?.homepage,
            id = it?.id,
            imdbId = it?.imdbId,
            isFavorite = it?.isFavorite,
            originalLanguage = it?.originalLanguage,
            originalTitle = it?.originalTitle,
            overview = it?.overview,
            popularity = it?.popularity,
            posterPath = it?.posterPath,
            releaseDate = it?.releaseDate,
            revenue = it?.revenue,
            runtime = it?.runtime,
            status = it?.status,
            tagline = it?.tagline,
            title = it?.title,
            video = it?.video,
            voteAverage = it?.voteAverage,
            voteCount = it?.voteCount
        )

    fun mapMovieFavoriteEntitiesToDomain(
        input: List<MovieDetailEntity>
    ): List<DetailMovie> {
        val movieList = ArrayList<DetailMovie>()
        input.map {
            val movie = DetailMovie(
                adult = it.adult,
                backdropPath = it.backdropPath,
                budget = it.budget,
                homepage = it.homepage,
                id = it.id,
                imdbId = it.imdbId,
                isFavorite = it.isFavorite,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                overview = it.overview,
                popularity = it.popularity,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                revenue = it.revenue,
                runtime = it.runtime,
                status = it.status,
                tagline = it.tagline,
                title = it.title,
                video = it.video,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount
            )
            movieList.add(movie)
        }
        return movieList
    }


    fun mapMovieDetailDomainToEntity(input: DetailMovie, isFavorite: Boolean): MovieDetailEntity =
        MovieDetailEntity(
            adult = input.adult,
            backdropPath = input.backdropPath,
            budget = input.budget,
            homepage = input.homepage,
            id = input.id,
            imdbId = input.imdbId,
            isFavorite = isFavorite,
            originalLanguage = input.originalLanguage,
            originalTitle = input.originalTitle,
            overview = input.overview,
            popularity = input.popularity,
            posterPath = input.posterPath,
            releaseDate = input.releaseDate,
            revenue = input.revenue,
            runtime = input.runtime,
            status = input.status,
            tagline = input.tagline,
            title = input.title,
            video = input.video,
            voteAverage = input.voteAverage,
            voteCount = input.voteCount
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
            inProduction = it.inProduction,
            isFavorite = isFavorite,
            lastAirDate = it.lastAirDate,
            title = it.name,
            numberOfEpisodes = it.numberOfEpisodes,
            numberOfSeasons = it.numberOfSeasons,
            originalLanguage = it.originalLanguage,
            originalName = it.originalName,
            overview = it.overview,
            popularity = it.popularity,
            posterPath = it.posterPath,
            status = it.status,
            type = it.type,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount
        )

    fun mapTvShowDetailEntitiesToDomain(
        it: TvShowDetailEntity?
    ): DetailTvShow =
        DetailTvShow(
            backdropPath = it?.backdropPath,
            firstAirDate = it?.firstAirDate,
            homepage = it?.homepage,
            id = it?.id,
            inProduction = it?.inProduction,
            isFavorite = it?.isFavorite,
            lastAirDate = it?.lastAirDate,
            title = it?.title,
            numberOfEpisodes = it?.numberOfEpisodes,
            numberOfSeasons = it?.numberOfSeasons,
            originalLanguage = it?.originalLanguage,
            originalName = it?.originalName,
            overview = it?.overview,
            popularity = it?.popularity,
            posterPath = it?.posterPath,
            status = it?.status,
            type = it?.type,
            voteAverage = it?.voteAverage,
            voteCount = it?.voteCount
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
                inProduction = it.inProduction,
                isFavorite = it.isFavorite,
                lastAirDate = it.lastAirDate,
                title = it.title,
                numberOfEpisodes = it.numberOfEpisodes,
                numberOfSeasons = it.numberOfSeasons,
                originalLanguage = it.originalLanguage,
                originalName = it.originalName,
                overview = it.overview,
                popularity = it.popularity,
                posterPath = it.posterPath,
                status = it.status,
                type = it.type,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount
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
            inProduction = input.inProduction,
            isFavorite = isFavorite,
            lastAirDate = input.lastAirDate,
            title = input.title,
            numberOfEpisodes = input.numberOfEpisodes,
            numberOfSeasons = input.numberOfSeasons,
            originalLanguage = input.originalLanguage,
            originalName = input.originalName,
            overview = input.overview,
            popularity = input.popularity,
            posterPath = input.posterPath,
            status = input.status,
            type = input.type,
            voteAverage = input.voteAverage,
            voteCount = input.voteCount
        )

}
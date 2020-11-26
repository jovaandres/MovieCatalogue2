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
                backdrop_path = it.backdrop_path,
                id = it.id,
                isPopular = isPopular,
                original_language = it.original_language,
                original_title = it.original_title,
                overview = it.overview,
                popularity = it.popularity,
                poster_path = it.poster_path,
                release_date = it.release_date,
                title = it.title,
                textQuery = textQuery,
                video = it.video,
                vote_average = it.vote_average,
                vote_count = it.vote_count
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
                backdrop_path = it.backdrop_path,
                id = it.id,
                isPopular = it.isPopular,
                original_language = it.original_language,
                original_title = it.original_title,
                overview = it.overview,
                popularity = it.popularity,
                poster_path = it.poster_path,
                release_date = it.release_date,
                title = it.title,
                textQuery = it.textQuery,
                video = it.video,
                vote_average = it.vote_average,
                vote_count = it.vote_count
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
                backdrop_path = it.backdrop_path,
                first_air_date = it.first_air_date,
                id = it.id,
                isPopular = isPopular,
                title = it.name,
                original_language = it.original_language,
                original_name = it.original_name,
                overview = it.overview,
                popularity = it.popularity,
                poster_path = it.poster_path,
                textQuery = textQuery,
                vote_average = it.vote_average,
                vote_count = it.vote_count
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
                backdrop_path = it.backdrop_path,
                first_air_date = it.first_air_date,
                id = it.id,
                isPopular = it.isPopular,
                title = it.title,
                original_language = it.original_language,
                original_name = it.original_name,
                overview = it.overview,
                popularity = it.popularity,
                poster_path = it.poster_path,
                textQuery = it.textQuery,
                vote_average = it.vote_average,
                vote_count = it.vote_count
            )
        }

    fun mapMovieDetailResponseToEntities(
        it: MovieDetailResponse,
        isFavorite: Boolean
    ): MovieDetailEntity =
        MovieDetailEntity(
            adult = it.adult,
            backdrop_path = it.backdrop_path,
            budget = it.budget,
            homepage = it.homepage,
            id = it.id,
            imdb_id = it.imdb_id,
            isFavorite = isFavorite,
            original_language = it.original_language,
            original_title = it.original_title,
            overview = it.overview,
            popularity = it.popularity,
            poster_path = it.poster_path,
            release_date = it.release_date,
            revenue = it.revenue,
            runtime = it.runtime,
            status = it.status,
            tagline = it.tagline,
            title = it.title,
            video = it.video,
            vote_average = it.vote_average,
            vote_count = it.vote_count
        )

    fun mapMovieDetailEntitiesToDomain(
        it: MovieDetailEntity?
    ): DetailMovie =
        DetailMovie(
            adult = it?.adult,
            backdrop_path = it?.backdrop_path,
            budget = it?.budget,
            homepage = it?.homepage,
            id = it?.id,
            imdb_id = it?.imdb_id,
            isFavorite = it?.isFavorite,
            original_language = it?.original_language,
            original_title = it?.original_title,
            overview = it?.overview,
            popularity = it?.popularity,
            poster_path = it?.poster_path,
            release_date = it?.release_date,
            revenue = it?.revenue,
            runtime = it?.runtime,
            status = it?.status,
            tagline = it?.tagline,
            title = it?.title,
            video = it?.video,
            vote_average = it?.vote_average,
            vote_count = it?.vote_count
        )

    fun mapMovieFavoriteEntitiesToDomain(
        input: List<MovieDetailEntity>
    ): List<DetailMovie> {
        val movieList = ArrayList<DetailMovie>()
        input.map {
            val movie = DetailMovie(
                adult = it.adult,
                backdrop_path = it.backdrop_path,
                budget = it.budget,
                homepage = it.homepage,
                id = it.id,
                imdb_id = it.imdb_id,
                isFavorite = it.isFavorite,
                original_language = it.original_language,
                original_title = it.original_title,
                overview = it.overview,
                popularity = it.popularity,
                poster_path = it.poster_path,
                release_date = it.release_date,
                revenue = it.revenue,
                runtime = it.runtime,
                status = it.status,
                tagline = it.tagline,
                title = it.title,
                video = it.video,
                vote_average = it.vote_average,
                vote_count = it.vote_count
            )
            movieList.add(movie)
        }
        return movieList
    }


    fun mapMovieDetailDomainToEntity(input: DetailMovie, isFavorite: Boolean): MovieDetailEntity =
        MovieDetailEntity(
            adult = input.adult,
            backdrop_path = input.backdrop_path,
            budget = input.budget,
            homepage = input.homepage,
            id = input.id,
            imdb_id = input.imdb_id,
            isFavorite = isFavorite,
            original_language = input.original_language,
            original_title = input.original_title,
            overview = input.overview,
            popularity = input.popularity,
            poster_path = input.poster_path,
            release_date = input.release_date,
            revenue = input.revenue,
            runtime = input.runtime,
            status = input.status,
            tagline = input.tagline,
            title = input.title,
            video = input.video,
            vote_average = input.vote_average,
            vote_count = input.vote_count
        )

    fun mapTvShowDetailResponseToEntities(
        it: TvShowDetailResponse,
        isFavorite: Boolean
    ): TvShowDetailEntity =
        TvShowDetailEntity(
            backdrop_path = it.backdrop_path,
            first_air_date = it.first_air_date,
            homepage = it.homepage,
            id = it.id,
            in_production = it.in_production,
            isFavorite = isFavorite,
            last_air_date = it.last_air_date,
            title = it.name,
            number_of_episodes = it.number_of_episodes,
            number_of_seasons = it.number_of_seasons,
            original_language = it.original_language,
            original_name = it.original_name,
            overview = it.overview,
            popularity = it.popularity,
            poster_path = it.poster_path,
            status = it.status,
            type = it.type,
            vote_average = it.vote_average,
            vote_count = it.vote_count
        )

    fun mapTvShowDetailEntitiesToDomain(
        it: TvShowDetailEntity?
    ): DetailTvShow =
        DetailTvShow(
            backdrop_path = it?.backdrop_path,
            first_air_date = it?.first_air_date,
            homepage = it?.homepage,
            id = it?.id,
            in_production = it?.in_production,
            isFavorite = it?.isFavorite,
            last_air_date = it?.last_air_date,
            title = it?.title,
            number_of_episodes = it?.number_of_episodes,
            number_of_seasons = it?.number_of_seasons,
            original_language = it?.original_language,
            original_name = it?.original_name,
            overview = it?.overview,
            popularity = it?.popularity,
            poster_path = it?.poster_path,
            status = it?.status,
            type = it?.type,
            vote_average = it?.vote_average,
            vote_count = it?.vote_count
        )

    fun mapTvShowFavoriteEntitiesToDomain(
        input: List<TvShowDetailEntity>
    ): List<DetailTvShow> {
        val tvShowList = ArrayList<DetailTvShow>()
        input.map {
            val tvShow = DetailTvShow(
                backdrop_path = it.backdrop_path,
                first_air_date = it.first_air_date,
                homepage = it.homepage,
                id = it.id,
                in_production = it.in_production,
                isFavorite = it.isFavorite,
                last_air_date = it.last_air_date,
                title = it.title,
                number_of_episodes = it.number_of_episodes,
                number_of_seasons = it.number_of_seasons,
                original_language = it.original_language,
                original_name = it.original_name,
                overview = it.overview,
                popularity = it.popularity,
                poster_path = it.poster_path,
                status = it.status,
                type = it.type,
                vote_average = it.vote_average,
                vote_count = it.vote_count
            )
            tvShowList.add(tvShow)
        }
        return tvShowList
    }

    fun mapTvShowDetailDomainToEntity(input: DetailTvShow, isFavorite: Boolean): TvShowDetailEntity =
        TvShowDetailEntity(
            backdrop_path = input.backdrop_path,
            first_air_date = input.first_air_date,
            homepage = input.homepage,
            id = input.id,
            in_production = input.in_production,
            isFavorite = isFavorite,
            last_air_date = input.last_air_date,
            title = input.title,
            number_of_episodes = input.number_of_episodes,
            number_of_seasons = input.number_of_seasons,
            original_language = input.original_language,
            original_name = input.original_name,
            overview = input.overview,
            popularity = input.popularity,
            poster_path = input.poster_path,
            status = input.status,
            type = input.type,
            vote_average = input.vote_average,
            vote_count = input.vote_count
        )

}
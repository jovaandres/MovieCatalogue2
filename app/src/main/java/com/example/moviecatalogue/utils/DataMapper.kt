package com.example.moviecatalogue.utils

import com.example.moviecatalogue.core.domain.model.DetailMovie
import com.example.moviecatalogue.core.domain.model.DetailTvShow
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.domain.model.TvShow
import com.example.moviecatalogue.presentation.model.DataDetailMovie
import com.example.moviecatalogue.presentation.model.DataDetailTvShow
import com.example.moviecatalogue.presentation.model.DataMovie
import com.example.moviecatalogue.presentation.model.DataTvShow

object DataMapper {

    fun mapMovieToDataMovie(input: Movie): DataMovie {
        return DataMovie(
            id = input.id,
            overview = input.overview,
            posterPath = input.posterPath,
            title = input.title,
            voteAverage = input.voteAverage
        )
    }

    fun mapTvShowToDataTvShow(input: TvShow): DataTvShow {
        return DataTvShow(
            id = input.id,
            overview = input.overview,
            posterPath = input.posterPath,
            title = input.title,
            voteAverage = input.voteAverage
        )
    }

    fun mapDetailMovieToDataDetailMovie(input: DetailMovie): DataDetailMovie {
        return DataDetailMovie(
            backdropPath = input.backdropPath,
            homepage = input.homepage,
            id = input.id,
            isFavorite = input.isFavorite,
            overview = input.overview,
            posterPath = input.posterPath,
            releaseDate = input.releaseDate,
            runtime = input.runtime,
            title = input.title,
            voteAverage = input.voteAverage
        )
    }

    fun mapDataDetailMovieToDetailMovie(input: DataDetailMovie): DetailMovie {
        return DetailMovie(
            backdropPath = input.backdropPath,
            homepage = input.homepage,
            id = input.id,
            isFavorite = input.isFavorite,
            overview = input.overview,
            posterPath = input.posterPath,
            releaseDate = input.releaseDate,
            runtime = input.runtime,
            title = input.title,
            voteAverage = input.voteAverage
        )
    }

    fun mapDetailTvShowToDataDetailTvShow(input: DetailTvShow): DataDetailTvShow {
        return DataDetailTvShow(
            backdropPath = input.backdropPath,
            firstAirDate = input.firstAirDate,
            homepage = input.homepage,
            id = input.id,
            isFavorite = input.isFavorite,
            title = input.title,
            numberOfEpisodes = input.numberOfEpisodes,
            numberOfSeasons = input.numberOfSeasons,
            overview = input.overview,
            posterPath = input.posterPath,
            voteAverage = input.voteAverage
        )
    }

    fun mapDataDetailTvShowToDetailTvShow(input: DataDetailTvShow): DetailTvShow {
        return DetailTvShow(
            backdropPath = input.backdropPath,
            firstAirDate = input.firstAirDate,
            homepage = input.homepage,
            id = input.id,
            isFavorite = input.isFavorite,
            title = input.title,
            numberOfEpisodes = input.numberOfEpisodes,
            numberOfSeasons = input.numberOfSeasons,
            overview = input.overview,
            posterPath = input.posterPath,
            voteAverage = input.voteAverage
        )
    }
}
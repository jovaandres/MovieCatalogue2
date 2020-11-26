package com.example.moviecatalogue.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.moviecatalogue.core.data.MovieCatalogueRepository
import com.example.moviecatalogue.core.data.source.local.entity.MovieDetailEntity
import com.example.moviecatalogue.ui.favorite.movie.FavoriteMovieViewModel
import com.example.moviecatalogue.core.utils.SortUtils
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteMovieViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FavoriteMovieViewModel

    @Mock
    private lateinit var mainRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observer: Observer<PagedList<MovieDetailEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieDetailEntity>

    @Before
    fun setUp() {
        viewModel = FavoriteMovieViewModel(mainRepository)
    }

    @Test
    fun getData() {
        val data = pagedList
        Assert.assertNotNull(data)

        val movies: MutableLiveData<PagedList<MovieDetailEntity>> = MutableLiveData()
        movies.value = data
        Mockito.`when`(
            mainRepository.getFavoriteMovie(
                "SELECT * FROM movie_detail WHERE isFavorite = 1 ",
                SortUtils.ALPHABET_ASC
            )
        ).thenReturn(movies)

        val movie = viewModel.showFavoriteMovie(
            "SELECT * FROM movie_detail WHERE isFavorite = 1 ",
            SortUtils.ALPHABET_ASC
        ).value
        Assert.assertNotNull(movie)

        viewModel.showFavoriteMovie(
            "SELECT * FROM movie_detail WHERE isFavorite = 1 ",
            SortUtils.ALPHABET_ASC
        ).observeForever(observer)
        Mockito.verify(observer).onChanged(data)
    }
}
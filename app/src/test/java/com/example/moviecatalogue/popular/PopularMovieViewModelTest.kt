package com.example.moviecatalogue.popular

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.moviecatalogue.core.data.MovieCatalogueRepository
import com.example.moviecatalogue.core.data.source.local.entity.MovieResultEntity
import com.example.moviecatalogue.ui.popular.movies.PopularMoviesViewModel
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
class PopularMovieViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PopularMoviesViewModel

    @Mock
    private lateinit var mainRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieResultEntity>>>

    @Mock
    private lateinit var pagedList: Resource<PagedList<MovieResultEntity>>

    @Before
    fun setUp() {
        viewModel = PopularMoviesViewModel(mainRepository)
    }

    @Test
    fun getData() {
        val data = pagedList
        Assert.assertNotNull(data)

        val movies: MutableLiveData<Resource<PagedList<MovieResultEntity>>> = MutableLiveData()
        movies.value = data
        Mockito.`when`(
            mainRepository.getPopularMovie(
                "SELECT * FROM movie_result WHERE isPopular = 1 ",
                SortUtils.ALPHABET_ASC
            )
        ).thenReturn(movies)

        val movie = viewModel.getPopularMovies(
            "SELECT * FROM movie_result WHERE isPopular = 1 ",
            SortUtils.ALPHABET_ASC
        ).value
        Assert.assertNotNull(movie)

        viewModel.getPopularMovies(
            "SELECT * FROM movie_result WHERE isPopular = 1 ",
            SortUtils.ALPHABET_ASC
        ).observeForever(observer)
        Mockito.verify(observer).onChanged(data)
    }
}
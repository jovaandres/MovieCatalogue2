package com.example.moviecatalogue.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.moviecatalogue.core.data.MovieCatalogueRepository
import com.example.moviecatalogue.core.data.source.local.entity.MovieResultEntity
import com.example.moviecatalogue.ui.search.movies.MoviesViewModel
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MoviesViewModel
    private lateinit var query: String

    @Mock
    private lateinit var mainRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieResultEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<MovieResultEntity>

    @Before
    fun setUp() {
        viewModel = MoviesViewModel(mainRepository)
        query = "Up"
    }

    @Test
    fun getData() {
        val data = Resource.success(pagedList)
        assertNotNull(data)

        val movies: MutableLiveData<Resource<PagedList<MovieResultEntity>>> = MutableLiveData()
        movies.value = data
        `when`(mainRepository.getSearchedMovie(query)).thenReturn(movies)

        val movie = viewModel.getMovies(query).value
        assertNotNull(movie)

        viewModel.getMovies(query).observeForever(observer)
        Mockito.verify(observer).onChanged(data)
    }
}
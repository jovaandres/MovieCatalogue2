package com.example.moviecatalogue.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.moviecatalogue.core.data.MovieCatalogueRepository
import com.example.moviecatalogue.core.data.source.local.entity.TvShowResultEntity
import com.example.moviecatalogue.ui.search.tvshows.TvShowsViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TvShowsViewModel
    private lateinit var query: String

    @Mock
    private lateinit var mainRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvShowResultEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowResultEntity>

    @Before
    fun setUp() {
        viewModel = TvShowsViewModel(mainRepository)
        query = "Up"
    }

    @Test
    fun getData() {
        val data = Resource.success(pagedList)
        Assert.assertNotNull(data)

        val movies: MutableLiveData<Resource<PagedList<TvShowResultEntity>>> = MutableLiveData()
        movies.value = data
        Mockito.`when`(mainRepository.getSearchedTvShow(query)).thenReturn(movies)

        val movie = viewModel.getTvShows(query).value
        Assert.assertNotNull(movie)

        viewModel.getTvShows(query).observeForever(observer)
        Mockito.verify(observer).onChanged(data)
    }
}
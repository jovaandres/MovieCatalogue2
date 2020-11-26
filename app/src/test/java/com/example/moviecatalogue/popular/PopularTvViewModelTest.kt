package com.example.moviecatalogue.popular

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.moviecatalogue.core.data.MovieCatalogueRepository
import com.example.moviecatalogue.core.data.source.local.entity.TvShowResultEntity
import com.example.moviecatalogue.ui.popular.tvshows.PopularTvShowViewModel
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
class PopularTvViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PopularTvShowViewModel

    @Mock
    private lateinit var mainRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvShowResultEntity>>>

    @Mock
    private lateinit var pagedList: Resource<PagedList<TvShowResultEntity>>

    @Before
    fun setUp() {
        viewModel = PopularTvShowViewModel(mainRepository)
    }

    @Test
    fun getData() {
        val data = pagedList
        Assert.assertNotNull(data)

        val movies: MutableLiveData<Resource<PagedList<TvShowResultEntity>>> = MutableLiveData()
        movies.value = data
        Mockito.`when`(
            mainRepository.getPopularTv(
                "SELECT * FROM tv_show_result WHERE isPopular = 1 ",
                SortUtils.ALPHABET_ASC
            )
        ).thenReturn(movies)

        val movie = viewModel.getPopularTvShow(
            "SELECT * FROM tv_show_result WHERE isPopular = 1 ",
            SortUtils.ALPHABET_ASC
        ).value
        Assert.assertNotNull(movie)

        viewModel.getPopularTvShow(
            "SELECT * FROM tv_show_result WHERE isPopular = 1 ",
            SortUtils.ALPHABET_ASC
        ).observeForever(observer)
        Mockito.verify(observer).onChanged(data)
    }
}
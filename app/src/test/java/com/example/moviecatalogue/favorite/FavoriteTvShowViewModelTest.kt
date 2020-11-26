package com.example.moviecatalogue.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.moviecatalogue.core.data.MovieCatalogueRepository
import com.example.moviecatalogue.core.data.source.local.entity.TvShowDetailEntity
import com.example.moviecatalogue.ui.favorite.tvshow.FavoriteTvShowViewModel
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
class FavoriteTvShowViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FavoriteTvShowViewModel

    @Mock
    private lateinit var mainRepository: MovieCatalogueRepository

    @Mock
    private lateinit var observer: Observer<PagedList<TvShowDetailEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowDetailEntity>

    @Before
    fun setUp() {
        viewModel = FavoriteTvShowViewModel(mainRepository)
    }

    @Test
    fun getData() {
        val data = pagedList
        Assert.assertNotNull(data)

        val movies: MutableLiveData<PagedList<TvShowDetailEntity>> = MutableLiveData()
        movies.value = data
        Mockito.`when`(
            mainRepository.getFavoriteTvShow(
                "SELECT * FROM tv_show_detail WHERE isFavorite = 1 ",
                SortUtils.ALPHABET_ASC
            )
        ).thenReturn(movies)

        val movie = viewModel.showFavoriteTvShow(
            "SELECT * FROM tv_show_detail WHERE isFavorite = 1 ",
            SortUtils.ALPHABET_ASC
        ).value
        Assert.assertNotNull(movie)

        viewModel.showFavoriteTvShow(
            "SELECT * FROM tv_show_detail WHERE isFavorite = 1 ",
            SortUtils.ALPHABET_ASC
        ).observeForever(observer)
        Mockito.verify(observer).onChanged(data)
    }
}
package com.example

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.TvShow
import com.example.moviecatalogue.core.domain.usecase.MovieCatalogueUseCase
import com.example.moviecatalogue.ui.popular.tvshows.PopularTvShowViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PopularTvViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PopularTvShowViewModel

    @Mock
    private lateinit var userCase: MovieCatalogueUseCase

    @Mock
    private lateinit var observer: Observer<Resource<List<TvShow>>>

    @Before
    fun setUp() {
        viewModel = PopularTvShowViewModel(userCase)
    }

    @Test
    fun `observe popular tvShow`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        viewModel.popularTvShows.asLiveData().observeForever(observer)
    }
}
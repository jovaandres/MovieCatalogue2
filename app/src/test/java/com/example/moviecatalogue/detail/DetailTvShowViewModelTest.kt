package com.example.moviecatalogue.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.moviecatalogue.core.data.MovieCatalogueRepository
import com.example.moviecatalogue.core.data.source.local.entity.TvShowDetailEntity
import com.example.moviecatalogue.ui.detail.DetailTvShowViewModel
import com.example.moviecatalogue.core.utils.DummyData
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailTvShowViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailTvShowViewModel
    private lateinit var tvShowId: String

    @Mock
    private lateinit var mainRepository: MovieCatalogueRepository

    @Mock
    private lateinit var tvShowDetailObserver: Observer<Resource<TvShowDetailEntity>>

    @Before
    fun setUp() {
        viewModel = DetailTvShowViewModel(mainRepository)
        tvShowId = "14814"
    }

    @Test
    fun getTvShowDetail() {
        val data = Resource.success(DummyData.generateDummyDetailTvShow())
        assertNotNull(data)

        val detailMovie: MutableLiveData<Resource<TvShowDetailEntity>> = MutableLiveData()
        detailMovie.value = data
        Mockito.`when`(mainRepository.getDetailTvShow(tvShowId)).thenReturn(detailMovie)

        val detailMovies = viewModel.getDetailTvShow(tvShowId).value
        verify(mainRepository).getDetailTvShow(tvShowId)
        assertNotNull(detailMovies)

        viewModel.getDetailTvShow(tvShowId).observeForever(tvShowDetailObserver)
        verify(tvShowDetailObserver).onChanged(data)
    }
}
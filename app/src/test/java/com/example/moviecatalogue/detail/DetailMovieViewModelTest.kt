package com.example.moviecatalogue.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.moviecatalogue.core.data.MovieCatalogueRepository
import com.example.moviecatalogue.core.data.source.local.entity.MovieDetailEntity
import com.example.moviecatalogue.ui.detail.DetailMovieViewModel
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
class DetailMovieViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailMovieViewModel
    private lateinit var movieId: String

    @Mock
    private lateinit var mainRepository: MovieCatalogueRepository

    @Mock
    private lateinit var movieDetailObserver: Observer<Resource<MovieDetailEntity>>

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel(mainRepository)
        movieId = "14160"
    }

    @Test
    fun getMovieDetail() {
        val data = Resource.success(DummyData.generateDummyDetailMovie())
        assertNotNull(data)

        val detailMovie: MutableLiveData<Resource<MovieDetailEntity>> = MutableLiveData()
        detailMovie.value = data
        Mockito.`when`(mainRepository.getDetailMovie(movieId)).thenReturn(detailMovie)

        val detailMovies = viewModel.getDetailMovie(movieId).value
        verify(mainRepository).getDetailMovie(movieId)
        assertNotNull(detailMovies)

        viewModel.getDetailMovie(movieId).observeForever(movieDetailObserver)
        verify(movieDetailObserver).onChanged(data)
    }
}
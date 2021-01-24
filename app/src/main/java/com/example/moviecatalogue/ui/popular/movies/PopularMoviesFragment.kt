package com.example.moviecatalogue.ui.popular.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.utils.SortPreferences
import com.example.moviecatalogue.core.utils.gone
import com.example.moviecatalogue.core.utils.invisible
import com.example.moviecatalogue.core.utils.visible
import com.example.moviecatalogue.databinding.PopularMoviesFragmentBinding
import com.example.moviecatalogue.presentation.adapter.MainMovieAdapter
import com.example.moviecatalogue.presentation.model.DataMovie
import com.example.moviecatalogue.ui.account.SetState
import com.example.moviecatalogue.utils.DataMapper.mapMovieToDataMovie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {

    private var _binding: PopularMoviesFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PopularMoviesViewModel by viewModels()
    private val mainMovieAdapter = MainMovieAdapter()

    @Inject
    lateinit var sortPreferences: SortPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PopularMoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            mainMovieAdapter.onItemClick = {
                val action =
                    PopularMoviesFragmentDirections.actionNavigationMovieToDetailMovieFragment()
                action.movieId = it.id.toString()
                view.findNavController().navigate(action)
            }

            lifecycleScope.launchWhenStarted {
                viewModel.popularMovies.collect {
                    movieObserver(it)
                }
            }
            lifecycleScope.launchWhenStarted {
                viewModel.nowPlayingMovies.collect {
                    nowPlayingObserver(it)
                }
            }
            binding.rvPopMovies.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = mainMovieAdapter
            }

            showPopularMovie()
        }
    }

    private fun showPopularMovie() {
        if (viewModel.popularMovies.value is Resource.Init) {
            viewModel.getPopularMovies()
        }
        if (viewModel.nowPlayingMovies.value is Resource.Init) {
            viewModel.getNowPlayingMovies()
        }
    }

    private fun nowPlayingObserver(data: Resource<List<Movie>>) {
        when (data) {
            is Resource.Init -> {
            }
            is Resource.Loading -> {
                binding.nowMovieProgress.visible()
            }
            is Resource.Success -> {
                val movieList = data.data?.map { mapMovieToDataMovie(it) }
                binding.nowMovieProgress.invisible()
                mainMovieAdapter.nowPlayingMovie = movieList as ArrayList<DataMovie>
            }
            is Resource.Error -> {
                binding.nowMovieProgress.invisible()
            }
        }
    }

    private fun movieObserver(data: Resource<List<Movie>>) {
        when (data) {
            is Resource.Init -> {
            }
            is Resource.Loading -> {
                binding.popMovieProgress.visible()
            }
            is Resource.Success -> {
                val movieList = data.data?.map { mapMovieToDataMovie(it) }
                binding.popMovieProgress.gone()
                mainMovieAdapter.popularMovie = movieList as ArrayList<DataMovie>
            }
            is Resource.Error -> {
                binding.popMovieProgress.gone()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as SetState).setState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.moviecatalogue.ui.popular.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.ui.MoviesAdapter
import com.example.moviecatalogue.core.ui.MoviesAdapterHorizontal
import com.example.moviecatalogue.core.utils.SortPreferences
import com.example.moviecatalogue.core.utils.gone
import com.example.moviecatalogue.core.utils.invisible
import com.example.moviecatalogue.core.utils.visible
import com.example.moviecatalogue.databinding.PopularMoviesFragmentBinding
import com.example.moviecatalogue.ui.detail.DetailMovieActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {

    private var _binding: PopularMoviesFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PopularMoviesViewModel by viewModels()
    private val popularMoviesAdapter = MoviesAdapter()
    private val nowPlayingMoviesAdapter = MoviesAdapterHorizontal()

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
            popularMoviesAdapter.onItemClick = { data ->
                val intent = Intent(activity, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.EXTRA_ID, data.id.toString())
                startActivity(intent)
            }
            nowPlayingMoviesAdapter.onItemClick = { data ->
                val intent = Intent(activity, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.EXTRA_ID, data.id.toString())
                startActivity(intent)
            }
            showPopularMovie()
        }
    }

    private fun showPopularMovie() {
        if (viewModel.popularMovies.value is Resource.Loading) {
            viewModel.getPopularMovies()
        }
        if (viewModel.nowPlayingMovies.value is Resource.Loading) {
            viewModel.getNowPlayingMovies()
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
    }

    private fun nowPlayingObserver(data: Resource<List<Movie>>) {
        when (data) {
            is Resource.Loading -> {
                binding.nowMovieProgress.visible()
                binding.movieNow.invisible()
            }
            is Resource.Success -> {
                binding.nowMovieProgress.invisible()
                binding.movieNow.visible()
                nowPlayingMoviesAdapter.setListMovie(data.data)
                nowPlayingMoviesAdapter.notifyDataSetChanged()
                binding.rvNowMovies.apply {
                    setHasFixedSize(true)
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = nowPlayingMoviesAdapter
                }
            }
            is Resource.Error -> {
                binding.nowMovieProgress.invisible()
            }
        }
    }

    private fun movieObserver(data: Resource<List<Movie>>) {
        when (data) {
            is Resource.Loading -> {
                binding.popMovieProgress.visible()
                binding.moviePop.invisible()
            }
            is Resource.Success -> {
                binding.popMovieProgress.gone()
                binding.moviePop.visible()
                popularMoviesAdapter.setListMovie(data.data)
                popularMoviesAdapter.notifyDataSetChanged()
                binding.rvPopMovies.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    adapter = popularMoviesAdapter
                }
            }
            is Resource.Error -> {
                binding.popMovieProgress.gone()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvNowMovies.adapter = null
        binding.rvPopMovies.adapter = null
        _binding = null
    }
}
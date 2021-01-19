package com.example.moviecatalogue.ui.search.movies

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.domain.model.TvShow
import com.example.moviecatalogue.core.ui.MoviesAdapterHorizontal
import com.example.moviecatalogue.core.ui.TvShowsAdapterHorizontal
import com.example.moviecatalogue.core.utils.gone
import com.example.moviecatalogue.core.utils.invisible
import com.example.moviecatalogue.core.utils.visible
import com.example.moviecatalogue.databinding.MoviesFragmentBinding
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.collect
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: MoviesFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MoviesViewModel by viewModels()
    private val moviesAdapter = MoviesAdapterHorizontal()
    private val tvAdapter = TvShowsAdapterHorizontal()

    private lateinit var toast: Toast

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            toast = Toast.makeText(
                activity,
                null,
                Toast.LENGTH_SHORT
            )

            moviesAdapter.onItemClick = {
                val action = MoviesFragmentDirections.actionNavigationSearchToDetailMovieFragment()
                action.movieId = it.id.toString()
                view.findNavController().navigate(action)
            }

            tvAdapter.onItemClick = {
                val action = MoviesFragmentDirections.actionNavigationSearchToDetailTvFragment()
                action.tvId = it.id.toString()
                view.findNavController().navigate(action)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.searchMovie.collect { movieObserver(it) }

        }
        lifecycleScope.launchWhenStarted {
            viewModel.searchTvShow.collect { tvObserver(it) }
        }

        observeSearchMovie()
    }

    @SuppressLint("CheckResult")
    private fun observeSearchMovie() {
        RxTextView.textChanges(binding.search)
            .skipInitialValue()
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                getMovieAndTv(it.toString())
            }
    }

    private fun getMovieAndTv(title: String) {
        if (title.trim().isNotEmpty()) {
            viewModel.getMovieAndTv(title)
            viewModel.query.value = title
        }
    }

    private fun movieObserver(data_movie: Resource<List<Movie>>) {
        when (data_movie) {
            is Resource.Init -> {
            }
            is Resource.Loading -> {
                moviesAdapter.deleteList()
                binding.movieProgress.visible()
                binding.movie.invisible()
            }
            is Resource.Success -> {
                binding.movieProgress.invisible()
                binding.movie.visible()
                moviesAdapter.movieList = data_movie.data as ArrayList<Movie>
                binding.rvMovies.apply {
                    setHasFixedSize(true)
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = moviesAdapter
                }
                if (data_movie.data?.isEmpty() == true) {
                    toast.setText(R.string.movie_not_found)
                    toast.show()
                }
            }
            is Resource.Error -> {
                binding.movieProgress.invisible()
            }
        }
    }

    private fun tvObserver(data_tv: Resource<List<TvShow>>) {
        when (data_tv) {
            is Resource.Init -> {
            }
            is Resource.Loading -> {
                tvAdapter.deleteList()
                binding.tvProgress.visible()
                binding.tv.invisible()
            }
            is Resource.Success -> {
                binding.tvProgress.gone()
                binding.tv.visible()
                tvAdapter.tvShowList = data_tv.data as ArrayList<TvShow>
                binding.rvTv.apply {
                    setHasFixedSize(true)
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = tvAdapter
                }
                if (data_tv.data?.isEmpty() == true) {
                    toast.setText(R.string.tv_show_not_found)
                    toast.show()
                }
            }
            is Resource.Error -> {
                binding.tvProgress.gone()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvMovies.adapter = null
        binding.rvTv.adapter = null
        toast.cancel()
        _binding = null
    }
}
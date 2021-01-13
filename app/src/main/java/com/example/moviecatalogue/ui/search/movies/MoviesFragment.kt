package com.example.moviecatalogue.ui.search.movies

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.domain.model.TvShow
import com.example.moviecatalogue.core.ui.MoviesAdapterHorizontal
import com.example.moviecatalogue.core.ui.TvShowsAdapterHorizontal
import com.example.moviecatalogue.core.utils.RxSearchObservable
import com.example.moviecatalogue.databinding.MoviesFragmentBinding
import com.example.moviecatalogue.ui.detail.DetailMovieActivity
import com.example.moviecatalogue.ui.detail.DetailTvActivity
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: MoviesFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MoviesViewModel by viewModels()
    private val moviesAdapter = MoviesAdapterHorizontal()
    private val tvAdapter = TvShowsAdapterHorizontal()

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
            moviesAdapter.onItemClick = { data ->
                val intent = Intent(activity, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.EXTRA_ID, data.id.toString())
                startActivity(intent)
            }
            tvAdapter.onItemClick = { data ->
                val intent = Intent(activity, DetailTvActivity::class.java)
                intent.putExtra(DetailTvActivity.EXTRA_ID, data.id.toString())
                startActivity(intent)
            }
        }
        observeSearchMovie()
    }

    @SuppressLint("CheckResult")
    private fun observeSearchMovie() {
        RxSearchObservable.fromView(binding.searchMovie)
            .distinctUntilChanged()
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { it.isNotEmpty() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { getMovieFromViewModel(it.toString()) }
    }

    private fun getMovieFromViewModel(title: String) {
        lifecycleScope.launchWhenStarted {
            viewModel.searchMovie.collect { movieObserver(it) }

        }
        lifecycleScope.launchWhenStarted {
            viewModel.searchTvShow.collect { tvObserver(it) }
        }
        if (title.isNotEmpty()) {
            viewModel.getMovies(title)
            viewModel.getTvShows(title)
        }
    }

    private fun movieObserver(data_movie: Resource<List<Movie>>) {
        when (data_movie) {
            is Resource.Loading -> {
                moviesAdapter.deleteList()
                binding.movieProgress.visibility = View.VISIBLE
                binding.movie.visibility = View.INVISIBLE
            }
            is Resource.Success -> {
                binding.movieProgress.visibility = View.INVISIBLE
                binding.movie.visibility = View.VISIBLE
                moviesAdapter.setListMovie(data_movie.data)
                binding.rvMovies.apply {
                    setHasFixedSize(true)
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = moviesAdapter
                }
                if (data_movie.data?.isEmpty() == true) {
                    FancyToast.makeText(
                        requireContext(),
                        getString(R.string.movie_not_found),
                        FancyToast.LENGTH_SHORT,
                        FancyToast.ERROR,
                        false
                    ).show()
                }
            }
            is Resource.Error -> {
                binding.movieProgress.visibility = View.INVISIBLE
            }
        }
    }

    private fun tvObserver(data_tv: Resource<List<TvShow>>) {
        when (data_tv) {
            is Resource.Loading -> {
                tvAdapter.deleteList()
                binding.tvProgress.visibility = View.VISIBLE
                binding.tv.visibility = View.INVISIBLE
            }
            is Resource.Success -> {
                binding.tvProgress.visibility = View.GONE
                binding.tv.visibility = View.VISIBLE
                tvAdapter.setListTvShow(data_tv.data)
                binding.rvTv.apply {
                    setHasFixedSize(true)
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = tvAdapter
                }
                if (data_tv.data?.isEmpty() == true) {
                    FancyToast.makeText(
                        requireContext(),
                        getString(R.string.tv_show_not_found),
                        FancyToast.LENGTH_SHORT,
                        FancyToast.ERROR,
                        false
                    ).show()
                }
            }
            is Resource.Error -> {
                binding.tvProgress.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
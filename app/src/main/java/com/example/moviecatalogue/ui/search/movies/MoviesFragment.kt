package com.example.moviecatalogue.ui.search.movies

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.moviecatalogue.core.ui.MoviesAdapter
import com.example.moviecatalogue.core.utils.RxSearchObservable
import com.example.moviecatalogue.databinding.MoviesFragmentBinding
import com.example.moviecatalogue.ui.detail.DetailMovieActivity
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
    private val moviesAdapter = MoviesAdapter()

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
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
       var recentQuery = ""
        try {
            recentQuery = viewModel.searchMovie.value.data?.get(0)?.textQuery.toString()
        } catch (e: IndexOutOfBoundsException) {
            Log.d("IndexOutOfRange", e.message.toString())
        }
        if (recentQuery != title && title.isNotEmpty()) {
            viewModel.getMovies(title)
        }
        lifecycleScope.launchWhenStarted {
            viewModel.searchMovie.collect {
                movieObserver(it)
            }
        }
    }

    private fun movieObserver(data: Resource<List<Movie>>) {
        when (data) {
            is Resource.Loading -> {
                binding.movieProgress.visibility = View.VISIBLE
            }
            is Resource.Success -> {
                binding.movieProgress.visibility = View.GONE
                moviesAdapter.setListMovie(data.data)
                moviesAdapter.notifyDataSetChanged()
                binding.rvMovies.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    adapter = moviesAdapter
                }
                if (data.data?.isEmpty() == true) {
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
                binding.movieProgress.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
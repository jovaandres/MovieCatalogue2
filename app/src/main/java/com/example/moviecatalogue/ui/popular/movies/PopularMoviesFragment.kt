package com.example.moviecatalogue.ui.popular.movies

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.ui.MoviesAdapter
import com.example.moviecatalogue.core.ui.MoviesAdapterHorizontal
import com.example.moviecatalogue.core.utils.SortPreferences
import com.example.moviecatalogue.core.utils.SortUtils
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
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showPopularMovie()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu, menu)
        val activeMenuId = menu.getItem(sortPreferences.getMenuPopularMovie())
        activeMenuId.isChecked = true
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        var index = 0
        val simpleQuery = "SELECT * FROM movie_result WHERE isPopular = 1 "
        when (item.itemId) {
            R.id.action_title_az -> {
                sort = SortUtils.ALPHABET_ASC
                index = 0
            }
            R.id.action_title_za -> {
                sort = SortUtils.ALPHABET_DESC
                index = 1
            }
            R.id.action_rating_50 -> {
                sort = SortUtils.RATING_DESC
                index = 2
            }
            R.id.action_rating_05 -> {
                sort = SortUtils.RATING_ASC
                index = 3
            }
            R.id.action_random -> {
                sort = SortUtils.RANDOM
                index = 4
            }
        }
        viewModel.getPopularMovies(simpleQuery, sort)
        lifecycleScope.launchWhenStarted {
            viewModel.popularMovies.collect {
                movieObserver(it)
            }
        }
        item.isChecked = true
        sortPreferences.setPrefPopularMovie(index, sort)
        return super.onOptionsItemSelected(item)
    }

    private fun showPopularMovie() {
        val simpleQuery = "SELECT * FROM movie_result WHERE isPopular = 1 "
        if (viewModel.popularMovies.value is Resource.Loading) {
            sortPreferences.getSortPopularMovie()?.let {
                viewModel.getPopularMovies(simpleQuery, it)
            }
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
                binding.nowMovieProgress.visibility = View.VISIBLE
                binding.movieNow.visibility = View.INVISIBLE
            }
            is Resource.Success -> {
                binding.nowMovieProgress.visibility = View.INVISIBLE
                binding.movieNow.visibility = View.VISIBLE
                nowPlayingMoviesAdapter.setListMovie(data.data)
                nowPlayingMoviesAdapter.notifyDataSetChanged()
                binding.rvNowMovies.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = nowPlayingMoviesAdapter
                }
            }
            is Resource.Error -> {
                binding.popMovieProgress.visibility = View.GONE
            }
        }
    }

    private fun movieObserver(data: Resource<List<Movie>>) {
        when (data) {
            is Resource.Loading -> {
                binding.popMovieProgress.visibility = View.VISIBLE
                binding.moviePop.visibility = View.INVISIBLE
            }
            is Resource.Success -> {
                binding.popMovieProgress.visibility = View.INVISIBLE
                binding.moviePop.visibility = View.VISIBLE
                popularMoviesAdapter.setListMovie(data.data)
                popularMoviesAdapter.notifyDataSetChanged()
                binding.rvPopMovies.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    adapter = popularMoviesAdapter
                }
            }
            is Resource.Error -> {
                binding.popMovieProgress.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
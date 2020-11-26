package com.example.moviecatalogue.ui.popular.movies

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.utils.EspressoIdlingResource
import com.example.moviecatalogue.core.utils.SortPreferences
import com.example.moviecatalogue.core.utils.SortUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.popular_movies_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {

    private val viewModel: PopularMoviesViewModel by viewModels()
    private val popularMoviesAdapter = PopularMoviesAdapter()

    @Inject
    lateinit var sortPreferences: SortPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.popular_movies_fragment, container, false)
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
        viewModel.getPopularMovies(simpleQuery, sort).observe(this, movieObserver)
        item.isChecked = true
        sortPreferences.setPrefPopularMovie(index, sort)
        return super.onOptionsItemSelected(item)
    }

    private fun showPopularMovie() {
        val simpleQuery = "SELECT * FROM movie_result WHERE isPopular = 1 "
        sortPreferences.getSortPopularMovie()?.let {
            viewModel.getPopularMovies(simpleQuery, it)
                .observe(viewLifecycleOwner, movieObserver)
        }
    }

    private val movieObserver = Observer<Resource<List<Movie>>> { data ->
        if (EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) EspressoIdlingResource.increment()
        if (data != null) {
            when (data) {
                is Resource.Loading -> {
                    pop_movie_progress.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    pop_movie_progress.visibility = View.GONE
                    popularMoviesAdapter.setPopularMovieList(data.data)
                    popularMoviesAdapter.notifyDataSetChanged()
                    rv_pop_movies.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(context)
                        adapter = popularMoviesAdapter
                    }
                }
                is Resource.Error -> {
                    pop_movie_progress.visibility = View.GONE
                }
            }
        }
        if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) EspressoIdlingResource.decrement()
    }
}
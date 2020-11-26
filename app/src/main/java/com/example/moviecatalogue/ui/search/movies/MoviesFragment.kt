package com.example.moviecatalogue.ui.search.movies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.utils.EspressoIdlingResource
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movies_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val viewModel: MoviesViewModel by viewModels()
    private val moviesAdapter = MoviesAdapter()

    @Inject
    lateinit var bundle: Bundle
    private lateinit var searchView: SearchView

    companion object {
        private const val RECENT_QUERY = "recent_query"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movies_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        searchView = search_movie as SearchView

        searchMovieByTitle()

        val title = arguments?.getString(RECENT_QUERY)
        if (title != null) {
            getMovieFromViewModel(title)
        }
    }

    private fun searchMovieByTitle() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                getMovieFromViewModel(query.toString())
                arguments = bundle.apply {
                    putString(RECENT_QUERY, query.toString())
                }
                val input =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                input.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun getMovieFromViewModel(title: String) {
        viewModel.getMovies(title).observe(viewLifecycleOwner, movieObserver)
    }

    private val movieObserver = Observer<Resource<List<Movie>>> { data ->
        if (EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) EspressoIdlingResource.increment()
        if (data != null) {
            when (data) {
                is Resource.Loading -> {
                    movie_progress.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    movie_progress.visibility = View.GONE
                    moviesAdapter.setListMovie(data.data)
                    moviesAdapter.notifyDataSetChanged()
                    rv_movies.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(context)
                        adapter = moviesAdapter
                    }
                    if (data.data?.isEmpty()!!) {
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
                    movie_progress.visibility = View.GONE
                }
            }
        }
        if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) EspressoIdlingResource.decrement()
    }
}
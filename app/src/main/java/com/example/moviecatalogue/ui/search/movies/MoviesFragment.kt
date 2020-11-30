package com.example.moviecatalogue.ui.search.movies

import android.content.Context
import android.content.Intent
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
import com.example.moviecatalogue.core.ui.MoviesAdapter
import com.example.moviecatalogue.databinding.MoviesFragmentBinding
import com.example.moviecatalogue.ui.detail.DetailMovieActivity
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: MoviesFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MoviesViewModel by viewModels()
    private val moviesAdapter = MoviesAdapter()

    @Inject
    lateinit var bundle: Bundle

    companion object {
        private const val RECENT_QUERY = "recent_query"
    }

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

        searchMovieByTitle()

        val title = arguments?.getString(RECENT_QUERY)
        if (title != null) {
            getMovieFromViewModel(title)
        }
    }

    private fun searchMovieByTitle() {
        binding.searchMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

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
                getMovieFromViewModel(newText.toString())
                return true
            }
        })
    }

    private fun getMovieFromViewModel(title: String) {
        viewModel.getMovies(title).observe(viewLifecycleOwner, movieObserver)
    }

    private val movieObserver = Observer<Resource<List<Movie>>> { data ->
        if (data != null) {
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
                    binding.movieProgress.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
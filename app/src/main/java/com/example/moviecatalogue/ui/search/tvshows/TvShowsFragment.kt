package com.example.moviecatalogue.ui.search.tvshows

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
import com.example.moviecatalogue.core.domain.model.TvShow
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.tv_shows_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class TvShowsFragment : Fragment() {

    private val viewModel: TvShowsViewModel by viewModels()
    private val tvShowsAdapter = TvShowsAdapter()

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
        return inflater.inflate(R.layout.tv_shows_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        searchView = search_tv_show as SearchView

        searchTvShowByTitle()

        val title = arguments?.getString(RECENT_QUERY)
        if (title != null) {
            getTvShowFromViewModel(title)
        }
    }

    private fun searchTvShowByTitle() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                getTvShowFromViewModel(query.toString())
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

    private fun getTvShowFromViewModel(title: String) {
        viewModel.getTvShows(title).observe(viewLifecycleOwner, tvShowObserver)
    }

    private val tvShowObserver = Observer<Resource<List<TvShow>>> { data ->
        if (data != null) {
            when (data) {
                is Resource.Loading -> tv_progress.visibility = View.VISIBLE
                is Resource.Success -> {
                    tv_progress.visibility = View.GONE
                    tvShowsAdapter.setListTvShow(data.data)
                    tvShowsAdapter.notifyDataSetChanged()
                    rv_tv_shows.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(context)
                        adapter = tvShowsAdapter
                    }
                    if (data.data?.isEmpty()!!) {
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
                    tv_progress.visibility = View.GONE
                }
            }
        }
    }
}
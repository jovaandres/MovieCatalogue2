package com.example.moviecatalogue.ui.search.tvshows

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.utils.RxSearchObservable
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.TvShow
import com.example.moviecatalogue.core.ui.TvShowsAdapter
import com.example.moviecatalogue.databinding.TvShowsFragmentBinding
import com.example.moviecatalogue.ui.detail.DetailTvActivity
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class TvShowsFragment : Fragment() {

    private var _binding: TvShowsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TvShowsViewModel by viewModels()
    private val tvShowsAdapter = TvShowsAdapter()

    @Inject
    lateinit var bundle: Bundle

    companion object {
        private const val RECENT_QUERY = "recent_query"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TvShowsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            tvShowsAdapter.onItemClick = { data ->
                val intent = Intent(activity, DetailTvActivity::class.java)
                intent.putExtra(DetailTvActivity.EXTRA_ID, data.id.toString())
                startActivity(intent)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeSearchMovie()

        val title = arguments?.getString(RECENT_QUERY)
        if (title != null) {
            getTvShowFromViewModel(title)
        }
    }

    @SuppressLint("CheckResult")
    private fun observeSearchMovie() {
        RxSearchObservable.fromView(binding.searchTvShow)
            .distinctUntilChanged()
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { it.isNotEmpty() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { getTvShowFromViewModel(it.toString()) }
    }

    private fun getTvShowFromViewModel(title: String) {
        viewModel.getTvShows(title).observe(viewLifecycleOwner, tvShowObserver)
    }

    private val tvShowObserver = Observer<Resource<List<TvShow>>> { data ->
        if (data != null) {
            when (data) {
                is Resource.Loading -> binding.tvProgress.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding.tvProgress.visibility = View.GONE
                    tvShowsAdapter.setListTvShow(data.data)
                    tvShowsAdapter.notifyDataSetChanged()
                    binding.rvTvShows.apply {
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
                    binding.tvProgress.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
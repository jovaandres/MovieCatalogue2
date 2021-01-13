package com.example.moviecatalogue.ui.popular.tvshows

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
import com.example.moviecatalogue.core.domain.model.TvShow
import com.example.moviecatalogue.core.ui.TvShowsAdapter
import com.example.moviecatalogue.databinding.PopularTvShowFragmentBinding
import com.example.moviecatalogue.ui.detail.DetailTvActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PopularTvShowFragment : Fragment() {

    private var _binding: PopularTvShowFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PopularTvShowViewModel by viewModels()
    private val popularTvShowAdapter = TvShowsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PopularTvShowFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            popularTvShowAdapter.onItemClick = { data ->
                val intent = Intent(activity, DetailTvActivity::class.java)
                intent.putExtra(DetailTvActivity.EXTRA_ID, data.id.toString())
                startActivity(intent)
            }
            showPopularTvShow()
        }
    }

    private fun showPopularTvShow() {
        if (viewModel.popularTvShows.value is Resource.Loading) {
            viewModel.getPopularTvShow()
        }
        lifecycleScope.launchWhenStarted {
            viewModel.popularTvShows.collect {
                tvShowObserver(it)
            }
        }
    }

    private fun tvShowObserver(data: Resource<List<TvShow>>) {
        when (data) {
            is Resource.Loading -> {
                binding.popTvProgress.visibility = View.VISIBLE
            }
            is Resource.Success -> {
                binding.popTvProgress.visibility = View.GONE
                popularTvShowAdapter.setListTvShow(data.data)
                popularTvShowAdapter.notifyDataSetChanged()
                binding.rvPopTvShows.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    adapter = popularTvShowAdapter
                }
            }
            is Resource.Error -> {
                binding.popTvProgress.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
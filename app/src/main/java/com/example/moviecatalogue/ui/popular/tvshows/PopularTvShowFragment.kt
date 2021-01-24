package com.example.moviecatalogue.ui.popular.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.TvShow
import com.example.moviecatalogue.core.utils.gone
import com.example.moviecatalogue.core.utils.visible
import com.example.moviecatalogue.databinding.PopularTvShowFragmentBinding
import com.example.moviecatalogue.presentation.adapter.TvShowsAdapter
import com.example.moviecatalogue.presentation.model.DataTvShow
import com.example.moviecatalogue.utils.DataMapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PopularTvShowFragment : Fragment() {

    private var _binding: PopularTvShowFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PopularTvShowViewModel by viewModels()
    private var _popularTvShowAdapter: TvShowsAdapter? = null
    private val popularTvShowAdapter get() = _popularTvShowAdapter!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PopularTvShowFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _popularTvShowAdapter = TvShowsAdapter()
        if (activity != null) {
            val action = PopularTvShowFragmentDirections.actionNavigationTvToDetailTvFragment()
            popularTvShowAdapter.onItemClick = {
                action.tvId = it.toString()
                view.findNavController().navigate(action)
            }

            lifecycleScope.launchWhenResumed {
                viewModel.popularTvShows.collect {
                    tvShowObserver(it)
                }
            }

            showPopularTvShow()
        }
    }

    private fun showPopularTvShow() {
        if (viewModel.popularTvShows.value is Resource.Init) {
            viewModel.getPopularTvShow()
        }
    }

    private fun tvShowObserver(data: Resource<List<TvShow>>) {
        when (data) {
            is Resource.Init -> {
            }
            is Resource.Loading -> {
                binding.popTvProgress.visible()
            }
            is Resource.Success -> {
                val tvList = data.data?.map { DataMapper.mapTvShowToDataTvShow(it) }
                binding.popTvProgress.gone()
                popularTvShowAdapter.tvShowList = tvList as ArrayList<DataTvShow>
                binding.rvPopTvShows.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    adapter = popularTvShowAdapter
                }
            }
            is Resource.Error -> {
                binding.popTvProgress.gone()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvPopTvShows.adapter = null
        _popularTvShowAdapter = null
        _binding = null
    }
}
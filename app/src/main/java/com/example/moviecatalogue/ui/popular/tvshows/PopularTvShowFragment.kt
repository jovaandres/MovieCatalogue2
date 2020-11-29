package com.example.moviecatalogue.ui.popular.tvshows

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.TvShow
import com.example.moviecatalogue.core.ui.popular.PopularTvShowAdapter
import com.example.moviecatalogue.core.utils.SortPreferences
import com.example.moviecatalogue.core.utils.SortUtils
import com.example.moviecatalogue.databinding.PopularTvShowFragmentBinding
import com.example.moviecatalogue.ui.detail.DetailTvActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PopularTvShowFragment : Fragment() {

    private var _binding: PopularTvShowFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PopularTvShowViewModel by viewModels()
    private val popularTvShowAdapter = PopularTvShowAdapter()

    @Inject
    lateinit var sortPreferences: SortPreferences

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
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showPopularTvShow()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu, menu)
        val activeMenuId = menu.getItem(sortPreferences.getMenuPopularTV())
        activeMenuId.isChecked = true
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        var index = 0
        val simpleQuery = "SELECT * FROM tv_show_result WHERE isPopular = 1 "
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
        viewModel.getPopularTvShow(simpleQuery, sort).observe(this, tvShowObserver)
        item.isChecked = true
        sortPreferences.setPrefPopularTv(index, sort)
        return super.onOptionsItemSelected(item)
    }

    private fun showPopularTvShow() {
        val simpleQuery = "SELECT * FROM tv_show_result WHERE isPopular = 1 "
        sortPreferences.getSortPopularTv()?.let {
            viewModel.getPopularTvShow(simpleQuery, it)
                .observe(viewLifecycleOwner, tvShowObserver)
        }
    }

    private val tvShowObserver = Observer<Resource<List<TvShow>>> { data ->
        if (data != null) {
            when (data) {
                is Resource.Loading -> {
                    binding.popTvProgress.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.popTvProgress.visibility = View.GONE
                    popularTvShowAdapter.setPopularTvShowList(data.data)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
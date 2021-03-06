package com.example.moviecatalogue.favorite.tvshow

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.di.CoreModuleDependencies
import com.example.moviecatalogue.core.domain.model.DetailTvShow
import com.example.moviecatalogue.core.utils.SortPreferences
import com.example.moviecatalogue.core.utils.SortUtils
import com.example.moviecatalogue.core.utils.gone
import com.example.moviecatalogue.core.utils.visible
import com.example.moviecatalogue.favorite.databinding.FavoriteTvShowFragmentBinding
import com.example.moviecatalogue.favorite.di.DaggerFavoriteComponent
import com.example.moviecatalogue.favorite.factory.FavoriteViewModelFactory
import com.example.moviecatalogue.presentation.model.DataDetailTvShow
import com.example.moviecatalogue.utils.DataMapper.mapDetailTvShowToDataDetailTvShow
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class FavoriteTvShowFragment : Fragment() {

    private var _binding: FavoriteTvShowFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: FavoriteViewModelFactory

    private val viewModel: FavoriteTvShowViewModel by viewModels {
        factory
    }
    private var _favoriteTvShowAdapter: FavoriteTvShowAdapter? = null
    private val favoriteTvShowAdapter get() = _favoriteTvShowAdapter!!

    @Inject
    lateinit var sortPreferences: SortPreferences

    override fun onAttach(context: Context) {
        DaggerFavoriteComponent.builder()
            .context(context)
            .coreDependencies(
                EntryPointAccessors.fromApplication(
                    context.applicationContext,
                    CoreModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu, menu)
        val activeMenuId = menu.getItem(sortPreferences.getMenuFavoriteTv())
        activeMenuId.isChecked = true
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        var index = 0
        val simpleQuery = "SELECT * FROM tv_show_detail WHERE isFavorite = 1 "
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
        viewModel.showFavoriteTvShow(simpleQuery, sort)
        item.isChecked = true
        sortPreferences.setPrefFavoriteTv(index, sort)
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavoriteTvShowFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _favoriteTvShowAdapter = FavoriteTvShowAdapter()
        if (activity != null) {
            val bundle = Bundle()
            favoriteTvShowAdapter.onItemClick = {
                bundle.putString("tvId", it.toString())
                view.findNavController()
                    .navigate(R.id.action_navigation_favorite_tv_to_detailTvFragment, bundle)
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.favoriteTvShow.collect {
                tvShowObserver(it)
            }
        }

        itemTouchHelper.attachToRecyclerView(binding.rvFavTvShows)
        showFavoriteTvShow()
    }

    private fun showFavoriteTvShow() {
        val simpleQuery = "SELECT * FROM tv_show_detail WHERE isFavorite = 1 "
        if (viewModel.favoriteTvShow.value is Resource.Init) {
            sortPreferences.getSortFavoriteTv()?.let {
                viewModel.showFavoriteTvShow(simpleQuery, it)
            }
        }
        binding.favTvProgress.gone()
    }

    private fun tvShowObserver(data: Resource<List<DetailTvShow>>) {
        when (data) {
            is Resource.Init -> {
            }
            is Resource.Loading -> binding.favTvProgress.visible()
            is Resource.Success -> {
                val tvList = data.data?.map { mapDetailTvShowToDataDetailTvShow(it) }
                favoriteTvShowAdapter.tvList = tvList as ArrayList<DataDetailTvShow>
                binding.rvFavTvShows.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    adapter = favoriteTvShowAdapter
                }
                binding.favTvProgress.gone()
            }
            is Resource.Error -> {
                binding.favTvProgress.gone()
            }
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val favoriteTvShow = favoriteTvShowAdapter.getSwipedData(swipedPosition)
                favoriteTvShow.let { viewModel.addToFavoriteTvShow(it, false) }

                val snackbar =
                    Snackbar.make(
                        view as View,
                        getString(R.string.delete_success),
                        Snackbar.LENGTH_SHORT
                    )
                snackbar.setAction("Undo") { _ ->
                    favoriteTvShow.let { viewModel.addToFavoriteTvShow(it, true) }
                }
                snackbar.show()
            }
        }
    })

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvFavTvShows.adapter = null
        _favoriteTvShowAdapter = null
        _binding = null
    }
}
package com.example.moviecatalogue.favorite.movie

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
import com.example.moviecatalogue.core.domain.model.DetailMovie
import com.example.moviecatalogue.core.utils.SortPreferences
import com.example.moviecatalogue.core.utils.SortUtils
import com.example.moviecatalogue.core.utils.gone
import com.example.moviecatalogue.core.utils.visible
import com.example.moviecatalogue.favorite.databinding.FavoriteMovieFragmentBinding
import com.example.moviecatalogue.favorite.di.DaggerFavoriteComponent
import com.example.moviecatalogue.favorite.factory.FavoriteViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class FavoriteMovieFragment : Fragment() {

    private var _binding: FavoriteMovieFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: FavoriteViewModelFactory

    private val viewModel: FavoriteMovieViewModel by viewModels {
        factory
    }
    private val favoriteMovieAdapter = FavoriteMovieAdapter()

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavoriteMovieFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            favoriteMovieAdapter.onItemClick = {
                val bundle = Bundle()
                bundle.putString("movieId", it.id.toString())
                view.findNavController()
                    .navigate(R.id.action_navigation_favorite_movie_to_detailMovieFragment, bundle)
            }
        }
        itemTouchHelper.attachToRecyclerView(binding.rvFavMovies)
        showFavoriteMovie()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu, menu)
        val activeMenuId = menu.getItem(sortPreferences.getMenuFavoriteMovie())
        activeMenuId.isChecked = true
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        var index = 0
        val simpleQuery = "SELECT * FROM movie_detail WHERE isFavorite = 1 "
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
        viewModel.showFavoriteMovie(simpleQuery, sort)
        lifecycleScope.launchWhenStarted {
            viewModel.favoriteMovies.collect {
                movieObserver(it)
            }
        }
        item.isChecked = true
        sortPreferences.setPrefFavoriteMovie(index, sort)
        return super.onOptionsItemSelected(item)
    }

    private fun showFavoriteMovie() {
        binding.favMovieProgress.visibility = View.VISIBLE
        val simpleQuery = "SELECT * FROM movie_detail WHERE isFavorite = 1 "
        if (viewModel.favoriteMovies.value is Resource.Loading) {
            sortPreferences.getSortFavoriteMovie()?.let {
                viewModel.showFavoriteMovie(simpleQuery, it)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.favoriteMovies.collect {
                movieObserver(it)
            }
        }
        binding.favMovieProgress.gone()
    }

    private fun movieObserver(data: Resource<List<DetailMovie>>) {
        when (data) {
            is Resource.Loading -> binding.favMovieProgress.visible()
            is Resource.Success -> {
                favoriteMovieAdapter.movieList = data.data as ArrayList<DetailMovie>
                binding.rvFavMovies.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    adapter = favoriteMovieAdapter
                }
                binding.favMovieProgress.gone()
            }
            else -> binding.favMovieProgress.gone()
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
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val favoriteMovie = favoriteMovieAdapter.getSwipedData(swipedPosition)
                favoriteMovie.let { viewModel.addToFavoriteMovie(it, false) }

                val snackbar =
                    Snackbar.make(
                        view as View,
                        getString(R.string.delete_success),
                        Snackbar.LENGTH_SHORT
                    )
                snackbar.setAction("Undo") { _ ->
                    favoriteMovie.let { viewModel.addToFavoriteMovie(it, true) }
                }
                snackbar.show()
            }
        }

    })

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvFavMovies.adapter = null
        _binding = null
    }

}
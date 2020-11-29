package com.example.moviecatalogue.ui.favorite.movie

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.domain.model.DetailMovie
import com.example.moviecatalogue.core.utils.SortPreferences
import com.example.moviecatalogue.core.utils.SortUtils
import com.example.moviecatalogue.databinding.FavoriteMovieFragmentBinding
import com.example.moviecatalogue.ui.detail.DetailMovieActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteMovieFragment : Fragment() {

    private var _binding: FavoriteMovieFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteMovieViewModel by viewModels()
    private val favoriteMovieAdapter = FavoriteMovieAdapter()

    @Inject
    lateinit var sortPreferences: SortPreferences

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
            favoriteMovieAdapter.onItemClick = { data ->
                val intent = Intent(activity, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.EXTRA_ID, data.id.toString())
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding.rvFavMovies)
        showFavoriteMovie()
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
        viewModel.showFavoriteMovie(simpleQuery, sort).observe(this, movieObserver)
        item.isChecked = true
        sortPreferences.setPrefFavoriteMovie(index, sort)
        return super.onOptionsItemSelected(item)
    }

    private fun showFavoriteMovie() {
        binding.favMovieProgress.visibility = View.VISIBLE
        val simpleQuery = "SELECT * FROM movie_detail WHERE isFavorite = 1 "
        sortPreferences.getSortFavoriteMovie()?.let {
            viewModel.showFavoriteMovie(simpleQuery, it)
                .observe(viewLifecycleOwner, movieObserver)
        }
        binding.favMovieProgress.visibility = View.GONE
    }

    private val movieObserver = Observer<List<DetailMovie>> { data ->
        if (data != null) {
            favoriteMovieAdapter.setMovieFavoriteList(data)
            favoriteMovieAdapter.notifyDataSetChanged()
            binding.rvFavMovies.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = favoriteMovieAdapter
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
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val favoriteMovie = favoriteMovieAdapter.getSwipedData(swipedPosition)
                favoriteMovie.let { viewModel.addToFavoriteMovie(it, false) }

                val snackbar =
                    Snackbar.make(view as View, getString(R.string.delete_success), Snackbar.LENGTH_SHORT)
                snackbar.setAction("Undo") { _ ->
                    favoriteMovie.let { viewModel.addToFavoriteMovie(it, true) }
                }
                snackbar.show()
            }
        }

    })

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
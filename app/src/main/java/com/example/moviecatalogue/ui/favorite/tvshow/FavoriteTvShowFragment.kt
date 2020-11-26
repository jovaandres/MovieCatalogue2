package com.example.moviecatalogue.ui.favorite.tvshow

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.domain.model.DetailTvShow
import com.example.moviecatalogue.core.utils.SortPreferences
import com.example.moviecatalogue.core.utils.SortUtils
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.favorite_tv_show_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteTvShowFragment : Fragment() {

    private val viewModel: FavoriteTvShowViewModel by viewModels()
    private val favoriteTvShowAdapter = FavoriteTvShowAdapter()

    @Inject
    lateinit var sortPreferences: SortPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_tv_show_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        itemTouchHelper.attachToRecyclerView(rv_fav_tv_shows)
        showFavoriteTvShow()
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
        viewModel.showFavoriteTvShow(simpleQuery, sort).observe(this, tvShowObserver)
        item.isChecked = true
        sortPreferences.setPrefFavoriteTv(index, sort)
        return super.onOptionsItemSelected(item)
    }

    private fun showFavoriteTvShow() {
        fav_tv_progress.visibility = View.VISIBLE
        val simpleQuery = "SELECT * FROM tv_show_detail WHERE isFavorite = 1 "
        sortPreferences.getSortFavoriteTv()?.let {
            viewModel.showFavoriteTvShow(simpleQuery, it)
                .observe(viewLifecycleOwner, tvShowObserver)
        }
        fav_tv_progress.visibility = View.GONE
    }

    private val tvShowObserver = Observer<List<DetailTvShow>> { data ->
        if (data != null) {
            favoriteTvShowAdapter.setTvShowFavoriteList(data)
            favoriteTvShowAdapter.notifyDataSetChanged()
            rv_fav_tv_shows.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = favoriteTvShowAdapter
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
                val favoriteTvShowEntity = favoriteTvShowAdapter.getSwipedData(swipedPosition)
                favoriteTvShowEntity.let { viewModel.addToFavoriteTvShow(it) }

                val snackbar =
                    Snackbar.make(view as View, getString(R.string.delete_success), Snackbar.LENGTH_SHORT)
                snackbar.setAction("Undo") {
                    favoriteTvShowEntity.let { viewModel.addToFavoriteTvShow(it) }
                }
                snackbar.show()
            }
        }
    })


}
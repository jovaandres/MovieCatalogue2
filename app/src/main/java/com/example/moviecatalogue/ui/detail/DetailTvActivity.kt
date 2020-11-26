package com.example.moviecatalogue.ui.detail

import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.DetailTvShow
import com.example.moviecatalogue.core.utils.Constant
import com.example.moviecatalogue.core.utils.EspressoIdlingResource
import com.shashank.sony.fancytoastlib.FancyToast
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail_tv.*

@AndroidEntryPoint
class DetailTvActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_item"
    }

    private val viewModel: DetailTvShowViewModel by viewModels()
    private lateinit var tvShowData: DetailTvShow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val id = intent?.getStringExtra(EXTRA_ID) as String

        add_fav_tv_fab.setOnClickListener {
            val state = tvShowData.isFavorite
            if (state == true) {
                showDialog(tvShowData)
            } else {
                viewModel.addToFavoriteTvShow(tvShowData)
                FancyToast.makeText(
                    this,
                    getString(R.string.add_success),
                    FancyToast.LENGTH_SHORT,
                    FancyToast.SUCCESS,
                    false
                ).show()
                add_fav_tv_fab.setImageResource(R.drawable.ic_favorite)
            }
        }

        if (EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) EspressoIdlingResource.increment()
        viewModel.getDetailTvShow(id).observe(this, { data ->
            run {
                if (data != null) {
                    when (data) {
                        is Resource.Loading -> tv_detail_progress.visibility = View.VISIBLE
                        is Resource.Success -> {
                            setTvShowDetail(data.data)
                            tv_detail_progress.visibility = View.GONE
                        }
                        is Resource.Error -> {
                            tv_detail_progress.visibility = View.GONE
                        }
                    }
                }
            }
        })
    }

    private fun setTvShowDetail(data: DetailTvShow?) {
        if (data != null) {
            length.text = getString(R.string.season_number, data.number_of_seasons)
            episode.text = getString(R.string.episode_number, data.number_of_episodes)
            name.text = data.title
            date.text = data.first_air_date
            rating.rating = data.vote_average?.toFloat()?.div(2) ?: 0f
            overview.text = data.overview
            Picasso.get()
                .load(Constant.IMAGE_URL + data.backdrop_path)
                .into(background)
            Picasso.get()
                .load(Constant.IMAGE_URL + data.poster_path)
                .into(poster)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            overview.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }
        try {
            tvShowData = data as DetailTvShow
            if (tvShowData.isFavorite == true){
                add_fav_tv_fab.setImageResource(R.drawable.ic_favorite)
            } else {
                add_fav_tv_fab.setImageResource(R.drawable.ic_not_favorite)
            }
            supportActionBar?.title = tvShowData.title
        } catch (e: Exception) {
            Log.d("Detail Exception","${e.message}")
        }

        if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) EspressoIdlingResource.decrement()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showDialog(tvShowData: DetailTvShow) {
        val dialogTitle = getString(R.string.dialog_title)
        val dialogMessage = getString(R.string.dialog_message)
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(dialogTitle)
            .setMessage(dialogMessage)
            .setCancelable(true)
            .setPositiveButton("Yes") { _, _ ->
                viewModel.addToFavoriteTvShow(tvShowData)
                FancyToast.makeText(
                    this,
                    getString(R.string.delete_success),
                    FancyToast.LENGTH_SHORT,
                    FancyToast.SUCCESS,
                    false
                ).show()
                add_fav_tv_fab.setImageResource(R.drawable.ic_not_favorite)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog?.cancel()
            }
        val dialog = alertDialog.create()
        dialog.show()
    }
}
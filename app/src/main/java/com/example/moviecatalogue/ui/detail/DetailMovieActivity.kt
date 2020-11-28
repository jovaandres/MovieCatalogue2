package com.example.moviecatalogue.ui.detail

import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.DetailMovie
import com.example.moviecatalogue.core.utils.Constant.IMAGE_URL
import com.shashank.sony.fancytoastlib.FancyToast
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail_movie.*

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_item"
    }

    private val viewModel: DetailMovieViewModel by viewModels()
    private lateinit var movieData: DetailMovie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val id = intent?.getStringExtra(EXTRA_ID) as String

        add_fav_fab.setOnClickListener {
            val state = movieData.isFavorite
            if (state == true) {
                showDialog(movieData)
            } else {
                viewModel.addToFavoriteMovie(movieData)
                add_fav_fab.setImageResource(R.drawable.ic_favorite)
                FancyToast.makeText(
                    this,
                    getString(R.string.add_success),
                    FancyToast.LENGTH_SHORT,
                    FancyToast.SUCCESS,
                    false
                ).show()
            }
        }

        viewModel.getDetailMovie(id).observe(this, { data ->
            run {
                if (data != null) {
                    when (data) {
                        is Resource.Loading -> movie_detail_progress.visibility = View.VISIBLE
                        is Resource.Success -> {
                            setMovieDetail(data.data)
                            movie_detail_progress.visibility = View.GONE

                        }
                        is Resource.Error -> {
                            movie_detail_progress.visibility = View.GONE
                        }
                    }
                }
            }
        })
    }

    private fun setMovieDetail(data: DetailMovie?) {
        if (data != null) {
            length.text = getString(R.string.length_movie, data.runtime)
            name.text = data.title
            date.text = data.release_date
            rating.rating = data.vote_average?.toFloat()?.div(2) ?: 0f
            overview.text = data.overview
            Picasso.get()
                .load(IMAGE_URL + data.backdrop_path)
                .into(background)
            Picasso.get()
                .load(IMAGE_URL + data.poster_path)
                .into(poster)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            overview.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }
        try {
            movieData = data as DetailMovie
            if (movieData.isFavorite == true){
                add_fav_fab.setImageResource(R.drawable.ic_favorite)
            } else {
                add_fav_fab.setImageResource(R.drawable.ic_not_favorite)
            }
            supportActionBar?.title = movieData.title
        } catch (e: Exception) {
            Log.d("Detail Exception","${e.message}")
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showDialog(movieData: DetailMovie) {
        val dialogTitle = getString(R.string.dialog_title)
        val dialogMessage = getString(R.string.dialog_message)
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(dialogTitle)
            .setMessage(dialogMessage)
            .setCancelable(true)
            .setPositiveButton("Yes") { _, _ ->
                viewModel.addToFavoriteMovie(movieData)
                FancyToast.makeText(
                    this,
                    getString(R.string.delete_success),
                    FancyToast.LENGTH_SHORT,
                    FancyToast.SUCCESS,
                    false
                ).show()
                add_fav_fab.setImageResource(R.drawable.ic_not_favorite)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog?.cancel()
            }
        val dialog = alertDialog.create()
        dialog.show()
    }
}
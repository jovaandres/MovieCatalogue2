package com.example.moviecatalogue.ui.detail

import android.content.Intent
import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.DetailTvShow
import com.example.moviecatalogue.core.utils.Constant
import com.example.moviecatalogue.core.utils.underline
import com.example.moviecatalogue.core.utils.visible
import com.example.moviecatalogue.databinding.ActivityDetailTvBinding
import com.example.moviecatalogue.ui.WebViewActivity
import com.shashank.sony.fancytoastlib.FancyToast
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTvActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_item"
    }

    private val viewModel: DetailTvShowViewModel by viewModels()
    private lateinit var tvShowData: DetailTvShow
    private lateinit var _binding: ActivityDetailTvBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailTvBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        val id = intent?.getStringExtra(EXTRA_ID) as String

        _binding.addFav.setOnClickListener {
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
                _binding.addFav.setImageResource(R.drawable.ic_favorite)
            }
        }

        _binding.back.setOnClickListener {
            onBackPressed()
        }

        viewModel.getDetailTvShow(id).observe(this, { data ->
            run {
                if (data != null) {
                    when (data) {
                        is Resource.Loading -> _binding.tvDetailProgress.visibility = View.VISIBLE
                        is Resource.Success -> {
                            setTvShowDetail(data.data)
                            _binding.tvDetailProgress.visibility = View.GONE
                        }
                        is Resource.Error -> {
                            _binding.tvDetailProgress.visibility = View.GONE
                        }
                    }
                }
            }
        })
    }

    private fun setTvShowDetail(data: DetailTvShow?) {
        if (data != null) {
            _binding.apply {
                length.text = getString(R.string.season_number, data.numberOfSeasons)
                episode.text = getString(R.string.episode_number, data.numberOfEpisodes)
                name.text = data.title
                date.text = data.firstAirDate
                rating.rating = data.voteAverage?.toFloat()?.div(2) ?: 0f
                overview.text = data.overview
                if (data.homepage?.isNotEmpty() == true) {
                    homepage.visible()
                    homepageLink.text = data.homepage
                    homepageLink.underline = true
                    homepageLink.setOnClickListener {
                        val intent = Intent(this@DetailTvActivity, WebViewActivity::class.java)
                        intent.putExtra(WebViewActivity.URL, data.homepage)
                        startActivity(intent)
                    }
                }
                Picasso.get()
                    .load(Constant.IMAGE_URL + data.backdropPath)
                    .into(background)
                Picasso.get()
                    .load(Constant.IMAGE_URL + data.posterPath)
                    .into(poster)
            }

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            _binding.overview.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }
        try {
            tvShowData = data as DetailTvShow
            if (tvShowData.isFavorite == true){
                _binding.addFav.setImageResource(R.drawable.ic_favorite)
            } else {
                _binding.addFav.setImageResource(R.drawable.ic_not_favorite)
            }
            supportActionBar?.title = tvShowData.title
        } catch (e: Exception) {
            FancyToast.makeText(
                this,
                "Unable to finish request ${e.message}",
                FancyToast.LENGTH_SHORT,
                FancyToast.ERROR,
                false
            )
        }

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
                _binding.addFav.setImageResource(R.drawable.ic_not_favorite)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog?.cancel()
            }
        val dialog = alertDialog.create()
        dialog.show()
    }
}
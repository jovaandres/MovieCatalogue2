package com.example.moviecatalogue.ui.detail

import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.DetailMovie
import com.example.moviecatalogue.core.utils.Constant.IMAGE_URL
import com.example.moviecatalogue.core.utils.gone
import com.example.moviecatalogue.core.utils.underline
import com.example.moviecatalogue.core.utils.visible
import com.example.moviecatalogue.databinding.FragmentDetailMovieBinding
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {
    private var idFromFavorite: String? = null

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailMovieViewModel by viewModels()

    private lateinit var movieData: DetailMovie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idFromFavorite = it.getString("movieId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            binding.addFav.setOnClickListener {
                val state = movieData.isFavorite
                if (state == true) {
                    showDialog(movieData)
                } else {
                    viewModel.addToFavoriteMovie(movieData)
                    binding.addFav.setImageResource(R.drawable.ic_favorite)
                    FancyToast.makeText(
                        activity,
                        getString(R.string.add_success),
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,
                        false
                    ).show()
                }
            }

            var args = DetailMovieFragmentArgs.fromBundle(arguments as Bundle).movieId
            if (args == "0") {
                args = idFromFavorite.toString()
            }
            viewModel.getDetailMovie(args)
            lifecycleScope.launchWhenStarted {
                viewModel.detailMovie.collect { data ->
                    when (data) {
                        is Resource.Init -> {
                        }
                        is Resource.Loading -> binding.movieDetailProgress.visible()
                        is Resource.Success -> {
                            setMovieDetail(data.data)
                            binding.movieDetailProgress.gone()
                        }
                        is Resource.Error -> {
                            binding.movieDetailProgress.gone()
                        }
                    }
                }
            }
        }
    }

    private fun setMovieDetail(data: DetailMovie?) {
        if (data != null) {
            binding.apply {
                length.text = getString(R.string.length_movie, data.runtime)
                name.text = data.title
                date.text = data.releaseDate
                rating.rating = data.voteAverage?.toFloat()?.div(2) ?: 0f
                overview.text = data.overview
                if (data.homepage?.isNotEmpty() == true) {
                    homepage.visible()
                    homepageLink.text = data.homepage
                    homepageLink.underline = true
                    homepageLink.setOnClickListener {
                        val action =
                            DetailMovieFragmentDirections.actionDetailMovieFragmentToWebViewFragment()
                        action.url = data.homepage ?: "https://www.google.com"
                        view?.findNavController()?.navigate(action)
                    }
                }
                Glide.with(this@DetailMovieFragment)
                    .load(IMAGE_URL + data.backdropPath)
                    .into(background)
                Glide.with(this@DetailMovieFragment)
                    .load(IMAGE_URL + data.posterPath)
                    .into(poster)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            binding.overview.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }
        try {
            movieData = data as DetailMovie
            if (movieData.isFavorite == true) {
                binding.addFav.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.addFav.setImageResource(R.drawable.ic_not_favorite)
            }
        } catch (e: Exception) {
            FancyToast.makeText(
                activity,
                "Unable to finish request ${e.message}",
                FancyToast.LENGTH_SHORT,
                FancyToast.ERROR,
                false
            )
        }

    }

    private fun showDialog(movieData: DetailMovie) {
        val dialogTitle = getString(R.string.dialog_title)
        val dialogMessage = getString(R.string.dialog_message)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle(dialogTitle)
            .setMessage(dialogMessage)
            .setCancelable(true)
            .setPositiveButton("Yes") { _, _ ->
                viewModel.addToFavoriteMovie(movieData)
                FancyToast.makeText(
                    activity,
                    getString(R.string.delete_success),
                    FancyToast.LENGTH_SHORT,
                    FancyToast.SUCCESS,
                    false
                ).show()
                binding.addFav.setImageResource(R.drawable.ic_not_favorite)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog?.cancel()
            }
        val dialog = alertDialog.create()
        dialog.show()
    }
}
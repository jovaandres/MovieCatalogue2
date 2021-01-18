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
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.DetailTvShow
import com.example.moviecatalogue.core.utils.Constant
import com.example.moviecatalogue.core.utils.gone
import com.example.moviecatalogue.core.utils.underline
import com.example.moviecatalogue.core.utils.visible
import com.example.moviecatalogue.databinding.FragmentDetailTvBinding
import com.shashank.sony.fancytoastlib.FancyToast
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DetailTvFragment : Fragment() {
    private var idFromFavorite: String? = null

    private var _binding: FragmentDetailTvBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailTvShowViewModel by viewModels()

    private lateinit var tvShowData: DetailTvShow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idFromFavorite = it.getString("tvId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailTvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            binding.addFav.setOnClickListener {
                val state = tvShowData.isFavorite
                if (state == true) {
                    showDialog(tvShowData)
                } else {
                    viewModel.addToFavoriteTvShow(tvShowData)
                    FancyToast.makeText(
                        activity,
                        getString(R.string.add_success),
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,
                        false
                    ).show()
                    binding.addFav.setImageResource(R.drawable.ic_favorite)
                }
            }

            var args = DetailTvFragmentArgs.fromBundle(arguments as Bundle).tvId
            if (args == "0") {
                args = idFromFavorite.toString()
            }
            viewModel.getDetailTvShow(args)
            lifecycleScope.launchWhenStarted {
                viewModel.detailTv.collect { data ->
                    when (data) {
                        is Resource.Loading -> binding.tvDetailProgress.visible()
                        is Resource.Success -> {
                            setTvShowDetail(data.data)
                            binding.tvDetailProgress.gone()
                        }
                        is Resource.Error -> {
                            binding.tvDetailProgress.gone()
                        }
                    }
                }
            }
        }
    }

    private fun setTvShowDetail(data: DetailTvShow?) {
        if (data != null) {
            binding.apply {
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
                        val action =
                            DetailTvFragmentDirections.actionDetailTvFragmentToWebViewFragment()
                        action.url = data.homepage ?: "https://www.google.com"
                        view?.findNavController()?.navigate(action)
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
            binding.overview.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }
        try {
            tvShowData = data as DetailTvShow
            if (tvShowData.isFavorite == true) {
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

    private fun showDialog(tvShowData: DetailTvShow) {
        val dialogTitle = getString(R.string.dialog_title)
        val dialogMessage = getString(R.string.dialog_message)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle(dialogTitle)
            .setMessage(dialogMessage)
            .setCancelable(true)
            .setPositiveButton("Yes") { _, _ ->
                viewModel.addToFavoriteTvShow(tvShowData)
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
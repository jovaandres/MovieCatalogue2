package com.example.moviecatalogue.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.utils.Constant
import com.example.moviecatalogue.databinding.ItemMoviesBinding
import com.example.moviecatalogue.databinding.PopularMovieHeaderBinding
import com.example.moviecatalogue.presentation.model.DataMovie
import com.example.moviecatalogue.ui.popular.movies.PopularMoviesFragmentDirections
import com.squareup.picasso.Picasso

class MainMovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_NOW_PLAYING = 0
        private const val TYPE_POPULAR = 1
    }

    var popularMovie = ArrayList<DataMovie>()
        set(value) {
            popularMovie.addAll(value)
            notifyDataSetChanged()
        }

    var nowPlayingMovie = ArrayList<DataMovie>()
        set(value) {
            nowPlayingMovie.addAll(value)
            notifyItemChanged(0)
        }

    var onItemClick: ((DataMovie) -> Unit)? = null

    inner class NowMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = PopularMovieHeaderBinding.bind(itemView)
        val recyclerView = binding.rvNowMovies
    }

    inner class PopularMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMoviesBinding.bind(itemView)
        fun bind(moviesData: DataMovie) {
            with(binding) {
                titleMovie.text = moviesData.title
                descMovie.text = moviesData.overview
                rating.rating = moviesData.voteAverage?.toFloat()?.div(2) ?: 0f
                Picasso.get()
                    .load(Constant.IMAGE_URL + moviesData.posterPath)
                    .into(imgMovie)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(popularMovie[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_NOW_PLAYING) return NowMovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.popular_movie_header, parent, false)
        ) else if (viewType == TYPE_POPULAR) return PopularMovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        )
        throw IllegalArgumentException("Undefined view type")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NowMovieViewHolder) {
            val layoutManager = LinearLayoutManager(
                holder.recyclerView.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            holder.recyclerView.layoutManager = layoutManager
            if (holder.recyclerView.adapter == null) {
                val adapter = MoviesAdapterHorizontal()
                adapter.movieList = nowPlayingMovie
                holder.recyclerView.adapter = adapter
                adapter.onItemClick = {
                    val action =
                        PopularMoviesFragmentDirections.actionNavigationMovieToDetailMovieFragment()
                    action.movieId = it.id.toString()
                    holder.itemView.findNavController().navigate(action)
                }
            } else {
                holder.recyclerView.adapter?.notifyDataSetChanged()
            }
        } else if (holder is PopularMovieViewHolder) {
            holder.bind(popularMovie[position])
        } else {
            throw IllegalArgumentException("Undefined ViewHolder")
        }
    }

    override fun getItemCount(): Int {
        return popularMovie.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_NOW_PLAYING else TYPE_POPULAR
    }
}
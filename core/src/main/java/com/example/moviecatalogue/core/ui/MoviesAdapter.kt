package com.example.moviecatalogue.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalogue.core.R
import com.example.moviecatalogue.core.databinding.ItemMoviesBinding
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.utils.Constant.IMAGE_URL

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    var movieList = ArrayList<Movie>()
        set(value) {
            movieList.clear()
            movieList.addAll(value)
            notifyDataSetChanged()
        }

    var onItemClick: ((Movie) -> Unit)? = null

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMoviesBinding.bind(itemView)
        fun bind(moviesData: Movie) {
            with(binding) {
                titleMovie.text = moviesData.title
                descMovie.text = moviesData.overview
                rating.rating = moviesData.voteAverage?.toFloat()?.div(2) ?: 0f
                Glide.with(itemView)
                    .load(IMAGE_URL + moviesData.posterPath)
                    .into(imgMovie)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(movieList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder =
        MoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        )

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size
}
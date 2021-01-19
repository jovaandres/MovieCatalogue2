package com.example.moviecatalogue.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalogue.core.R
import com.example.moviecatalogue.core.databinding.ItemMovieHorizontalBinding
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.utils.Constant.IMAGE_URL

class MoviesAdapterHorizontal : RecyclerView.Adapter<MoviesAdapterHorizontal.MoviesViewHolder>() {

    var movieList = ArrayList<Movie>()
        set(value) {
            movieList.clear()
            movieList.addAll(value)
            notifyDataSetChanged()
        }

    var onItemClick: ((Movie) -> Unit)? = null

    fun deleteList() {
        movieList.clear()
        notifyDataSetChanged()
    }

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMovieHorizontalBinding.bind(itemView)
        fun bind(moviesData: Movie) {
            with(binding) {
                titleH.text = moviesData.title
                ratingH.text = moviesData.voteAverage?.toString()
                Glide.with(itemView)
                    .load(IMAGE_URL + moviesData.posterPath)
                    .into(imgMovieH)
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
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_horizontal, parent, false)
        )

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size
}
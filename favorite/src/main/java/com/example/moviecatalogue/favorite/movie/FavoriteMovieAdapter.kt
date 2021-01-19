package com.example.moviecatalogue.favorite.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalogue.core.domain.model.DetailMovie
import com.example.moviecatalogue.core.utils.Constant.IMAGE_URL
import com.example.moviecatalogue.favorite.R
import com.example.moviecatalogue.favorite.databinding.ItemMoviesBinding

class FavoriteMovieAdapter : RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder>() {

    var movieList = ArrayList<DetailMovie>()
        set(value) {
            movieList.clear()
            movieList.addAll(value)
            notifyDataSetChanged()
        }

    var onItemClick: ((DetailMovie) -> Unit)? = null

    inner class FavoriteMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMoviesBinding.bind(itemView)
        fun bind(moviesData: DetailMovie) {
            binding.apply {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder =
        FavoriteMovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        )

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    fun getSwipedData(swipedPosition: Int): DetailMovie = movieList[swipedPosition]
    override fun getItemCount(): Int = movieList.size
}
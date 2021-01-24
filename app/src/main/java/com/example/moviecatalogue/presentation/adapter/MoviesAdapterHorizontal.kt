package com.example.moviecatalogue.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.utils.Constant.IMAGE_URL
import com.example.moviecatalogue.databinding.ItemMovieHorizontalBinding
import com.example.moviecatalogue.presentation.model.DataMovie
import com.squareup.picasso.Picasso

class MoviesAdapterHorizontal : RecyclerView.Adapter<MoviesAdapterHorizontal.MoviesViewHolder>() {

    var movieList = ArrayList<DataMovie>()
        set(value) {
            movieList.clear()
            movieList.addAll(value)
            notifyDataSetChanged()
        }

    var onItemClick: ((Int?) -> Unit)? = null

    fun deleteList() {
        movieList.clear()
        notifyDataSetChanged()
    }

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMovieHorizontalBinding.bind(itemView)
        fun bind(moviesData: DataMovie) {
            with(binding) {
                titleH.text = moviesData.title
                ratingH.text = moviesData.voteAverage?.toString()
                Picasso.get()
                    .load(IMAGE_URL + moviesData.posterPath)
                    .into(imgMovieH)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(movieList[adapterPosition].id)
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
package com.example.moviecatalogue.ui.favorite.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.domain.model.DetailMovie
import com.example.moviecatalogue.core.utils.Constant
import com.example.moviecatalogue.ui.detail.DetailMovieActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movies.view.*

class FavoriteMovieAdapter : RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder>() {

    private var movieList = ArrayList<DetailMovie>()

    fun setMovieFavoriteList(newMovieList: List<DetailMovie>?) {
        if (newMovieList == null) return
        movieList.clear()
        movieList.addAll(newMovieList)
        notifyDataSetChanged()
    }

    class FavoriteMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(moviesData: DetailMovie) {
            with(itemView) {
                title_movie.text = moviesData.title
                desc_movie.text = moviesData.overview
                rating.rating = moviesData.vote_average?.toFloat()?.div(2) ?: 0f
                Picasso.get()
                    .load(Constant.IMAGE_URL + moviesData.poster_path)
                    .into(img_movie)
                setOnClickListener {
                    val intent = Intent(it.context, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_ID, moviesData.id.toString())
                    it.context.startActivity(intent)
                }
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
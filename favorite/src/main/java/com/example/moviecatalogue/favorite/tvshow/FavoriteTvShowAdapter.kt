package com.example.moviecatalogue.favorite.tvshow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogue.core.domain.model.DetailTvShow
import com.example.moviecatalogue.core.utils.Constant
import com.example.moviecatalogue.favorite.R
import com.example.moviecatalogue.favorite.databinding.ItemTvShowBinding
import com.squareup.picasso.Picasso

class FavoriteTvShowAdapter :
    RecyclerView.Adapter<FavoriteTvShowAdapter.FavoriteTvShowViewHolder>() {

    var tvList = ArrayList<DetailTvShow>()
        set(value) {
            tvList.clear()
            tvList.addAll(value)
            notifyDataSetChanged()
        }
    var onItemClick: ((DetailTvShow) -> Unit)? = null

    inner class FavoriteTvShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemTvShowBinding.bind(itemView)
        fun bind(tvShowsData: DetailTvShow) {
            binding.run {
                titleTvShow.text = tvShowsData.title
                descTvShow.text = tvShowsData.overview
                rating.rating = tvShowsData.voteAverage?.toFloat()?.div(2) ?: 0f
                Picasso.get()
                    .load(Constant.IMAGE_URL + tvShowsData.posterPath)
                    .into(imgTvShow)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(tvList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTvShowViewHolder =
        FavoriteTvShowViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_tv_show, parent, false)
        )

    override fun onBindViewHolder(holder: FavoriteTvShowViewHolder, position: Int) {
        holder.bind(tvList[position])
    }

    fun getSwipedData(swipedPosition: Int): DetailTvShow = tvList[swipedPosition]
    override fun getItemCount(): Int = tvList.size
}
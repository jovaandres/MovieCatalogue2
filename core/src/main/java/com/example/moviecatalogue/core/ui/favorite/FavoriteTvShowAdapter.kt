package com.example.moviecatalogue.core.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogue.core.R
import com.example.moviecatalogue.core.databinding.ItemTvShowBinding
import com.example.moviecatalogue.core.domain.model.DetailTvShow
import com.example.moviecatalogue.core.utils.Constant
import com.squareup.picasso.Picasso

class FavoriteTvShowAdapter :
    RecyclerView.Adapter<FavoriteTvShowAdapter.FavoriteTvShowViewHolder>() {

    private var tvList = ArrayList<DetailTvShow>()
    var onItemClick: ((DetailTvShow) -> Unit)? = null

    fun setTvShowFavoriteList(newTvList: List<DetailTvShow>?) {
        if (newTvList == null) return
        tvList.clear()
        tvList.addAll(newTvList)
        notifyDataSetChanged()
    }

    inner class FavoriteTvShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemTvShowBinding.bind(itemView)
        fun bind(tvShowsData: DetailTvShow) {
            with(binding) {
                titleTvShow.text = tvShowsData.title
                descTvShow.text = tvShowsData.overview
                rating.rating = tvShowsData.vote_average?.toFloat()?.div(2) ?: 0f
                Picasso.get()
                    .load(Constant.IMAGE_URL + tvShowsData.poster_path)
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
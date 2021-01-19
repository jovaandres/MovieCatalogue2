package com.example.moviecatalogue.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalogue.core.R
import com.example.moviecatalogue.core.databinding.ItemTvShowBinding
import com.example.moviecatalogue.core.domain.model.TvShow
import com.example.moviecatalogue.core.utils.Constant.IMAGE_URL

class TvShowsAdapter :
    RecyclerView.Adapter<TvShowsAdapter.TvShowsViewHolder>() {

    var tvShowList = ArrayList<TvShow>()
        set(value) {
            tvShowList.clear()
            tvShowList.addAll(value)
            notifyDataSetChanged()
        }

    var onItemClick: ((TvShow) -> Unit)? = null

    inner class TvShowsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemTvShowBinding.bind(itemView)
        fun bind(tvShowsData: TvShow) {
            binding.run {
                titleTvShow.text = tvShowsData.title
                descTvShow.text = tvShowsData.overview
                rating.rating = tvShowsData.voteAverage?.toFloat()?.div(2) ?: 0f
                Glide.with(itemView)
                    .load(IMAGE_URL + tvShowsData.posterPath)
                    .into(imgTvShow)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(tvShowList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowsViewHolder =
        TvShowsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_tv_show, parent, false)
        )

    override fun onBindViewHolder(holder: TvShowsViewHolder, position: Int) {
            holder.bind(tvShowList[position])
    }

    override fun getItemCount(): Int = tvShowList.size
}
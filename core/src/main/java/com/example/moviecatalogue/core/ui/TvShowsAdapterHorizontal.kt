package com.example.moviecatalogue.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogue.core.R
import com.example.moviecatalogue.core.databinding.ItemTvHorizontalBinding
import com.example.moviecatalogue.core.domain.model.TvShow
import com.example.moviecatalogue.core.utils.Constant.IMAGE_URL
import com.squareup.picasso.Picasso

class TvShowsAdapterHorizontal :
    RecyclerView.Adapter<TvShowsAdapterHorizontal.TvShowsViewHolder>() {

    var tvShowList = ArrayList<TvShow>()
        set(value) {
            tvShowList.clear()
            tvShowList.addAll(value)
            notifyDataSetChanged()
        }

    var onItemClick: ((TvShow) -> Unit)? = null

    fun deleteList() {
        tvShowList.clear()
        notifyDataSetChanged()
    }

    inner class TvShowsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemTvHorizontalBinding.bind(itemView)
        fun bind(tvShowsData: TvShow) {
            binding.run {
                titleTvH.text = tvShowsData.title
                ratingTvH.text = tvShowsData.voteAverage?.toString()
                Picasso.get()
                    .load(IMAGE_URL + tvShowsData.posterPath)
                    .into(imgTvH)
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
            LayoutInflater.from(parent.context).inflate(R.layout.item_tv_horizontal, parent, false)
        )

    override fun onBindViewHolder(holder: TvShowsViewHolder, position: Int) {
            holder.bind(tvShowList[position])
    }

    override fun getItemCount(): Int = tvShowList.size
}
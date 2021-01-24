package com.example.moviecatalogue.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.utils.Constant.IMAGE_URL
import com.example.moviecatalogue.databinding.ItemTvShowBinding
import com.example.moviecatalogue.presentation.model.DataTvShow
import com.squareup.picasso.Picasso

class TvShowsAdapter :
    RecyclerView.Adapter<TvShowsAdapter.TvShowsViewHolder>() {

    var tvShowList = ArrayList<DataTvShow>()
        set(value) {
            tvShowList.clear()
            tvShowList.addAll(value)
            notifyDataSetChanged()
        }

    var onItemClick: ((Int?) -> Unit)? = null

    inner class TvShowsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemTvShowBinding.bind(itemView)
        fun bind(tvShowsData: DataTvShow) {
            binding.run {
                titleTvShow.text = tvShowsData.title
                descTvShow.text = tvShowsData.overview
                rating.rating = tvShowsData.voteAverage?.toFloat()?.div(2) ?: 0f
                Picasso.get()
                    .load(IMAGE_URL + tvShowsData.posterPath)
                    .into(imgTvShow)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(tvShowList[adapterPosition].id)
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
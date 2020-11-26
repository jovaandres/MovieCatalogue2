package com.example.moviecatalogue.ui.popular.tvshows

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.domain.model.TvShow
import com.example.moviecatalogue.core.utils.Constant
import com.example.moviecatalogue.ui.detail.DetailTvActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_tv_show.view.*

class PopularTvShowAdapter : RecyclerView.Adapter<PopularTvShowAdapter.TvShowsViewHolder>() {

    private var tvShowList = ArrayList<TvShow>()

    fun setPopularTvShowList(newTvShowList: List<TvShow>?) {
        if (newTvShowList == null) return
        tvShowList.clear()
        tvShowList.addAll(newTvShowList)
        notifyDataSetChanged()
    }

    class TvShowsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShowsData: TvShow) {
            with(itemView) {
                title_tv_show.text = tvShowsData.title
                desc_tv_show.text = tvShowsData.overview
                rating.rating = tvShowsData.vote_average?.toFloat()?.div(2) ?: 0f
                Picasso.get()
                    .load(Constant.IMAGE_URL + tvShowsData.poster_path)
                    .into(img_tv_show)
                setOnClickListener {
                    val intent = Intent(it.context, DetailTvActivity::class.java)
                    intent.putExtra(DetailTvActivity.EXTRA_ID, tvShowsData.id.toString())
                    it.context.startActivity(intent)
                }
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
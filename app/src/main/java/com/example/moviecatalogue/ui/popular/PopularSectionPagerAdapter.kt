package com.example.moviecatalogue.ui.popular

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.moviecatalogue.R
import com.example.moviecatalogue.ui.popular.movies.PopularMoviesFragment
import com.example.moviecatalogue.ui.popular.tvshows.PopularTvShowFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class PopularSectionPagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.movies,
            R.string.tv_shows
        )
    }

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> PopularMoviesFragment()
            1 -> PopularTvShowFragment()
            else -> Fragment()
        }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence = mContext.resources.getString(
        TAB_TITLES[position]
    )
}
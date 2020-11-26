package com.example.moviecatalogue.ui

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalogue.R
import com.example.moviecatalogue.ui.favorite.FavoriteActivity
import com.example.moviecatalogue.ui.favorite.FavoriteSectionPagerAdapter
import com.example.moviecatalogue.ui.search.SearchActivity
import com.example.moviecatalogue.ui.search.SearchSectionPagerAdapter
import com.example.moviecatalogue.ui.popular.PopularActivity
import com.example.moviecatalogue.ui.popular.PopularSectionPagerAdapter
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_popular.*
import kotlinx.android.synthetic.main.item_bottom_navigation.*

abstract class BaseActivity : AppCompatActivity(), ChipNavigationBar.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewId = getContentViewId()
        setContentView(viewId)

        when (viewId) {
            R.layout.activity_main -> {
                val sectionPagerAdapter =
                    SearchSectionPagerAdapter(
                        this,
                        supportFragmentManager
                    )
                view_pager.adapter = sectionPagerAdapter
                tabs.setupWithViewPager(view_pager)
                bottom_menu.setItemSelected(R.id.search)
            }
            R.layout.activity_favorite -> {
                val sectionPagerAdapter =
                    FavoriteSectionPagerAdapter(
                        this,
                        supportFragmentManager
                    )
                fav_view_pager.adapter = sectionPagerAdapter
                fav_tabs.setupWithViewPager(fav_view_pager)
                bottom_menu.setItemSelected(R.id.favorite)
                supportActionBar?.title = getString(R.string.favorite)
            }
            R.layout.activity_popular -> {
                val sectionPagerAdapter =
                    PopularSectionPagerAdapter(
                        this,
                        supportFragmentManager
                    )
                pop_view_pager.adapter = sectionPagerAdapter
                pop_tabs.setupWithViewPager(pop_view_pager)
                bottom_menu.setItemSelected(R.id.popular)
                supportActionBar?.title = getString(R.string.popular)
            }
        }
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.action_bar_color)))
        supportActionBar?.elevation = 0f
        bottom_menu.setOnItemSelectedListener(this)
    }

    override fun onItemSelected(id: Int) {
        when (id) {
            R.id.popular -> {
                getActivity(PopularActivity::class.java)
            }
            R.id.search -> {
                getActivity(SearchActivity::class.java)
            }
            R.id.favorite -> {
                getActivity(FavoriteActivity::class.java)
            }
        }
    }

    private fun <T> getActivity(activity: Class<T>) {
        val intent = Intent(this, activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    abstract fun getContentViewId(): Int
}
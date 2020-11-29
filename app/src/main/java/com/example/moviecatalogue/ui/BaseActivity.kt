package com.example.moviecatalogue.ui

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalogue.R
import com.example.moviecatalogue.databinding.ActivityFavoriteBinding
import com.example.moviecatalogue.databinding.ActivityMainBinding
import com.example.moviecatalogue.databinding.ActivityPopularBinding
import com.example.moviecatalogue.ui.favorite.FavoriteActivity
import com.example.moviecatalogue.ui.favorite.FavoriteSectionPagerAdapter
import com.example.moviecatalogue.ui.popular.PopularActivity
import com.example.moviecatalogue.ui.popular.PopularSectionPagerAdapter
import com.example.moviecatalogue.ui.search.SearchActivity
import com.example.moviecatalogue.ui.search.SearchSectionPagerAdapter
import com.ismaeldivita.chipnavigation.ChipNavigationBar

abstract class BaseActivity : AppCompatActivity(), ChipNavigationBar.OnItemSelectedListener {

    private lateinit var _mainBinding: ActivityMainBinding
    private lateinit var _popularBinding: ActivityPopularBinding
    private lateinit var _favoriteBinding: ActivityFavoriteBinding
    private lateinit var bottomMenu: ChipNavigationBar

    companion object {
        const val SEARCH = "search"
        const val POPULAR = "popular"
        const val FAVORITE = "favorite"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (getContentViewId()) {
            SEARCH -> {
                _mainBinding = ActivityMainBinding.inflate(layoutInflater)
                setContentView(_mainBinding.root)
                bottomMenu = findViewById(R.id.bottom_menu)
                val sectionPagerAdapter =
                    SearchSectionPagerAdapter(
                        this,
                        supportFragmentManager
                    )
                _mainBinding.apply {
                    viewPager.adapter = sectionPagerAdapter
                    tabs.setupWithViewPager(viewPager)
                }
                bottomMenu.setItemSelected(R.id.search)
            }
            FAVORITE -> {
                _favoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
                setContentView(_favoriteBinding.root)
                bottomMenu = findViewById(R.id.bottom_menu)
                val sectionPagerAdapter =
                    FavoriteSectionPagerAdapter(
                        this,
                        supportFragmentManager
                    )
                _favoriteBinding.apply {
                    favViewPager.adapter = sectionPagerAdapter
                    favTabs.setupWithViewPager(favViewPager)
                }
                bottomMenu.setItemSelected(R.id.favorite)
                supportActionBar?.title = getString(R.string.favorite)
            }
            POPULAR -> {
                _popularBinding = ActivityPopularBinding.inflate(layoutInflater)
                setContentView(_popularBinding.root)
                bottomMenu = findViewById(R.id.bottom_menu)
                val sectionPagerAdapter =
                    PopularSectionPagerAdapter(
                        this,
                        supportFragmentManager
                    )
                _popularBinding.apply {
                    popViewPager.adapter = sectionPagerAdapter
                    popTabs.setupWithViewPager(popViewPager)
                }
                bottomMenu.setItemSelected(R.id.popular)
                supportActionBar?.title = getString(R.string.popular)
            }
        }
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.action_bar_color)))
        supportActionBar?.elevation = 0f
        bottomMenu.setOnItemSelectedListener(this)
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

    abstract fun getContentViewId(): String
}
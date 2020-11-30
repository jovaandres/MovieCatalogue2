package com.example.moviecatalogue.ui

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.moviecatalogue.R
import com.example.moviecatalogue.databinding.ActivityPopularBinding
import com.example.moviecatalogue.databinding.ActivitySearchBinding
import com.example.moviecatalogue.ui.popular.PopularActivity
import com.example.moviecatalogue.ui.popular.PopularSectionPagerAdapter
import com.example.moviecatalogue.ui.search.SearchActivity
import com.example.moviecatalogue.ui.search.SearchSectionPagerAdapter
import com.ismaeldivita.chipnavigation.ChipNavigationBar

abstract class BaseActivity : AppCompatActivity(), ChipNavigationBar.OnItemSelectedListener {

    private lateinit var mainBinding: ActivitySearchBinding
    private lateinit var popularBinding: ActivityPopularBinding
    private lateinit var bottomMenu: ChipNavigationBar

    companion object {
        const val SEARCH = "search"
        const val POPULAR = "popular"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (getContentViewId()) {
            SEARCH -> {
                mainBinding = ActivitySearchBinding.inflate(layoutInflater)
                setContentView(mainBinding.root)
                bottomMenu = findViewById(R.id.bottom_menu)
                val sectionPagerAdapter =
                    SearchSectionPagerAdapter(
                        this,
                        supportFragmentManager
                    )
                mainBinding.apply {
                    viewPager.adapter = sectionPagerAdapter
                    tabs.setupWithViewPager(viewPager)
                }
                bottomMenu.setItemSelected(R.id.search)
            }
            POPULAR -> {
                popularBinding = ActivityPopularBinding.inflate(layoutInflater)
                setContentView(popularBinding.root)
                bottomMenu = findViewById(R.id.bottom_menu)
                val sectionPagerAdapter =
                    PopularSectionPagerAdapter(
                        this,
                        supportFragmentManager
                    )
                popularBinding.apply {
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
                val uri = ("moviecatalogue://favorite").toUri()
                val intent = Intent(Intent.ACTION_VIEW, uri)

                startActivity(intent)
            }
        }
    }

    private fun <T> getActivity(activity: Class<T>) =
        startActivity(Intent(this, activity))


    override fun onResume() {
        super.onResume()
        updateBottomMenu()
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    private fun updateBottomMenu() {
        when (getContentViewId()) {
            SEARCH -> bottomMenu.setItemSelected(R.id.search)
            POPULAR -> bottomMenu.setItemSelected(R.id.popular)
        }
    }

    abstract fun getContentViewId(): String
}
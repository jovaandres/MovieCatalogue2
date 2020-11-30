package com.example.moviecatalogue.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalogue.R
import com.example.moviecatalogue.favorite.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sectionPagerAdapter =
            FavoriteSectionPagerAdapter(
                this,
                supportFragmentManager
            )
        binding.apply {
            favViewPager.adapter = sectionPagerAdapter
            favTabs.setupWithViewPager(favViewPager)
        }
        supportActionBar?.title = getString(R.string.favorite)
    }

}
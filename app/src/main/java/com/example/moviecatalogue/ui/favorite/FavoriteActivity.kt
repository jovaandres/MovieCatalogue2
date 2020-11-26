package com.example.moviecatalogue.ui.favorite

import com.example.moviecatalogue.R
import com.example.moviecatalogue.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : BaseActivity() {

    override fun getContentViewId(): Int {
        return R.layout.activity_favorite
    }
}
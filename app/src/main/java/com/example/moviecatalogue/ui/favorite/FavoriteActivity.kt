package com.example.moviecatalogue.ui.favorite

import com.example.moviecatalogue.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : BaseActivity() {

    override fun getContentViewId(): String {
        return FAVORITE
    }
}
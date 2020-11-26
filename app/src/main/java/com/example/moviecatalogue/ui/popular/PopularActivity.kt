package com.example.moviecatalogue.ui.popular

import com.example.moviecatalogue.R
import com.example.moviecatalogue.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularActivity : BaseActivity() {

    override fun getContentViewId(): Int {
        return R.layout.activity_popular
    }
}
package com.example.moviecatalogue.ui.search

import com.example.moviecatalogue.R
import com.example.moviecatalogue.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity() {

    override fun getContentViewId(): Int {
        return R.layout.activity_main
    }
}
package com.example.moviecatalogue.ui.search

import com.example.moviecatalogue.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity() {

    override fun getContentViewId(): String {
        return SEARCH
    }
}
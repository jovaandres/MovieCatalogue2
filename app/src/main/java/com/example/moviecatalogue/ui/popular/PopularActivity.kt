package com.example.moviecatalogue.ui.popular

import com.example.moviecatalogue.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularActivity : BaseActivity() {

    override fun getContentViewId(): String {
        return POPULAR
    }
}
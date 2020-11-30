package com.example.moviecatalogue.ui.popular

import android.content.Intent
import com.example.moviecatalogue.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularActivity : BaseActivity() {

    override fun getContentViewId(): String {
        return POPULAR
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(Intent.ACTION_MAIN)
            .addCategory(Intent.CATEGORY_HOME)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
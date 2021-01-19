package com.example.moviecatalogue.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.utils.Constant
import com.example.moviecatalogue.core.utils.SvgLoader
import com.example.moviecatalogue.core.utils.gone
import com.example.moviecatalogue.core.utils.visible
import com.example.moviecatalogue.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

        appBarConfiguration = AppBarConfiguration(navController.graph, binding.navigationLayout)

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.navigationView, navController)

        val attrImage: ImageView =
            binding.navigationView.getHeaderView(0).findViewById(R.id.attr_image)
        SvgLoader.fetchSvg(this, Constant.ATTR_IMAGE, attrImage)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (intArrayOf(
                    R.id.navigation_favorite_movie,
                    R.id.navigation_favorite_tv,
                    R.id.detailMovieFragment,
                    R.id.detailTvFragment,
                    R.id.webViewFragment
                ).contains(
                    destination.id
                )
            ) {
                bottomNavigation.gone()
            } else {
                bottomNavigation.visible()
            }

            if (intArrayOf(
                    R.id.detailMovieFragment,
                    R.id.detailTvFragment,
                    R.id.webViewFragment
                ).contains(destination.id)
            ) {
                supportActionBar?.hide()
            } else {
                supportActionBar?.show()
            }
        }

        bottomNavigation.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
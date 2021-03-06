package com.example.moviecatalogue.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.utils.UserPreferences
import com.example.moviecatalogue.core.utils.gone
import com.example.moviecatalogue.core.utils.visible
import com.example.moviecatalogue.databinding.ActivityMainBinding
import com.example.moviecatalogue.ui.account.SetState
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SetState {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    private lateinit var username: TextView

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(
            if (userPreferences.darkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        username = binding.navigationView.getHeaderView(0).findViewById(R.id.username)
        username.text = userPreferences.username

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(navController.graph, binding.navigationLayout)

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.navigationView, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (intArrayOf(
                    R.id.navigation_favorite_movie,
                    R.id.navigation_favorite_tv,
                    R.id.navigation_register,
                    R.id.detailMovieFragment,
                    R.id.navigation_login,
                    R.id.detailTvFragment,
                    R.id.webViewFragment,
                ).contains(destination.id)
            ) {
                bottomNavigation.gone()
            } else {
                bottomNavigation.visible()
            }

            if (intArrayOf(
                    R.id.detailMovieFragment,
                    R.id.detailTvFragment,
                    R.id.webViewFragment,
                    R.id.navigation_register,
                    R.id.navigation_login
                ).contains(destination.id)
            ) {
                supportActionBar?.hide()
            } else {
                supportActionBar?.show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun setState() {
        username.text = userPreferences.username
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
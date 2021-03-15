package com.glogachev.twitchtopgames.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.glogachev.twitchtopgames.R
import com.google.android.material.navigation.NavigationView
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navContainer =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navContainer.navController

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        //appBarConfiguration = AppBarConfiguration(navGraph = navController.graph)
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.nav_top_games_fragment, R.id.nav_about_fragment),
            drawerLayout = drawer
        )
        toolbar.setupWithNavController(
            navController = navController,
            configuration = appBarConfiguration
        )
        navView.setupWithNavController(navController = navController)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.drawer_about -> {
                    navController.navigate(R.id.action_global_nav_about)
                    drawer.close()
                }
                R.id.drawer_feedback -> {
                    navController.navigate(R.id.action_global_nav_top_games)
                    drawer.close()
                }
                else -> {
                    Timber.d("False" + " Test")
                }
            }
            true
        }
    }
}
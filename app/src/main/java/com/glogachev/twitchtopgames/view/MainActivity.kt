package com.glogachev.twitchtopgames.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.glogachev.twitchtopgames.R
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    //todo() добавить кастомный тулбар в details fr.
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navContainer =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navContainer.navController

        val toolbar: Toolbar = findViewById(R.id.toolbar_holder)
        setSupportActionBar(toolbar)
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.nav_top_games_fragment, R.id.nav_about_fragment),
            drawerLayout = drawer
        )
        // toolbar.setupWithNavController(navController, appBarConfiguration)
        setupActionBarWithNavController(
            navController = navController,
            configuration = appBarConfiguration
        )
        navView.setupWithNavController(navController = navController)
        navView.setNavigationItemSelectedListener { menuItem ->
            clickNavDrawerItem(menuItem, navController, drawer)
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun clickNavDrawerItem(
        it: MenuItem,
        navController: NavController,
        drawer: DrawerLayout
    ) {
        when (it.itemId) {
            R.id.drawer_about -> {
                navController.navigate(R.id.action_global_nav_about)
                drawer.close()
            }
            R.id.drawer_feedback -> {
                navController.navigate(R.id.action_global_nav_top_games)
                drawer.close()
            }
        }
    }
}
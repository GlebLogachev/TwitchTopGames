package com.glogachev.twitchtopgames.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.glogachev.twitchtopgames.R

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.nav_host_fragment_container)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)

        //appBarConfiguration = AppBarConfiguration(navGraph = navController.graph)
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.topGamesFragment),
            drawerLayout = drawer
        )
        toolbar.setupWithNavController(
            navController = navController,
            configuration = appBarConfiguration
        )
    }
}
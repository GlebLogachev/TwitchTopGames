package com.glogachev.twitchtopgames.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.glogachev.twitchtopgames.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.alert_dialog.view.*
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
        //setSupportActionBar(toolbar)
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
        navView.setNavigationItemSelectedListener { menuItem ->
            clickNavDrawerItem(menuItem, navController, drawer)
            true
        }
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.toolbar_send_feedback -> {
                    showFeedbackDialog()
                }
            }
            true
        }
    }

    private fun showFeedbackDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.alert_dialog, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        dialogView.feedback_tie.addTextChangedListener {
            dialogView.feedback_til.isCounterEnabled = it.toString().trim().isNotBlank()
            Timber.d(it.toString())
        }
        dialogView.alert_cancel_btn.setOnClickListener {
            alertDialog.dismiss()
        }

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
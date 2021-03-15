package com.glogachev.twitchtopgames.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import com.glogachev.twitchtopgames.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.nav_host_fragment_container)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Hello"
        setSupportActionBar(toolbar)
    }
}
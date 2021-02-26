package com.glogachev.twitchtopgames.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.glogachev.twitchtopgames.App
import com.glogachev.twitchtopgames.R
import com.glogachev.twitchtopgames.domain.TopGamesRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
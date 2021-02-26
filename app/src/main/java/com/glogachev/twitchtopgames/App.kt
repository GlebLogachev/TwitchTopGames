package com.glogachev.twitchtopgames

import android.app.Application
import com.glogachev.twitchtopgames.data.HttpClientFactory
import com.glogachev.twitchtopgames.data.TopGamesRepositoryImpl
import com.glogachev.twitchtopgames.data.db.GamesDao
import com.glogachev.twitchtopgames.data.db.AppDatabase
import com.glogachev.twitchtopgames.data.retrofit.TopGamesApi
import com.glogachev.twitchtopgames.domain.TopGamesRepository

class App : Application() {

    companion object {
        @JvmStatic
        lateinit var appRepository: TopGamesRepository
    }

    override fun onCreate() {
        super.onCreate()
        val db = AppDatabase.getDatabase(applicationContext)
        val dao: GamesDao = db.getTopGamesDao()
        appRepository = TopGamesRepositoryImpl(
            apiInterface = TopGamesApi.create(HttpClientFactory.createHttpClient()),
            dbInterface = dao
        )
    }
}
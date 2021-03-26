package com.glogachev.twitchtopgames

import android.app.Application
import com.glogachev.twitchtopgames.di.AppComponent
import com.glogachev.twitchtopgames.di.DaggerAppComponent
import com.glogachev.twitchtopgames.di.modules.AppModule
import timber.log.Timber

class App : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        component = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()

    }
}
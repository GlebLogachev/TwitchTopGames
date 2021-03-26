package com.glogachev.twitchtopgames.di

import com.glogachev.twitchtopgames.di.modules.AppModule
import com.glogachev.twitchtopgames.di.modules.DataBaseModule
import com.glogachev.twitchtopgames.di.modules.NetworkModule
import com.glogachev.twitchtopgames.di.modules.RepositoryModule
import com.glogachev.twitchtopgames.view.topGames.main.TopGamesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class, AppModule::class, DataBaseModule::class])
interface AppComponent {

    fun inject(target: TopGamesFragment)
}
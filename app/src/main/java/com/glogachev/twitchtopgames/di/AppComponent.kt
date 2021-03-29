package com.glogachev.twitchtopgames.di

import com.glogachev.twitchtopgames.di.modules.*
import com.glogachev.twitchtopgames.view.topGames.main.TopGamesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class, RepositoryModule::class, AppModule::class, DataBaseModule::class,
        ViewModelsModule::class, ViewModelFactoryModule::class]
)
interface AppComponent {

    fun inject(target: TopGamesFragment)
}
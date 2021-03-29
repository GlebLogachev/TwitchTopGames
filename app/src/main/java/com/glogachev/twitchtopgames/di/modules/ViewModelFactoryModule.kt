package com.glogachev.twitchtopgames.di.modules

import androidx.lifecycle.ViewModelProvider
import com.glogachev.twitchtopgames.view.topGames.main.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
package com.glogachev.twitchtopgames.di.modules

import androidx.lifecycle.ViewModel
import com.glogachev.twitchtopgames.di.ViewModelKey
import com.glogachev.twitchtopgames.view.topGames.main.TopGamesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(TopGamesViewModel::class)
    internal abstract fun provideTopGameViewModel(viewModel: TopGamesViewModel): ViewModel


}
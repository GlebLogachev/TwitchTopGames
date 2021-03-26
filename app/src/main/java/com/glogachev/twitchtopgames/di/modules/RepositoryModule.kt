package com.glogachev.twitchtopgames.di.modules

import com.glogachev.twitchtopgames.data.TopGamesRepositoryImpl
import com.glogachev.twitchtopgames.domain.TopGamesRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun provideRepository(repoImpl: TopGamesRepositoryImpl): TopGamesRepository
}

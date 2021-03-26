package com.glogachev.twitchtopgames.di.modules

import android.content.Context
import com.glogachev.twitchtopgames.data.db.AppDatabase
import com.glogachev.twitchtopgames.data.db.GamesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Singleton
    @Provides
    fun provideDao(context: Context): GamesDao{
        val db = AppDatabase.getDatabase(context = context)
        return db.getTopGamesDao()
    }
}
package com.glogachev.twitchtopgames.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GameDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTopGamesDao(): GamesDao
    companion object {
        @Volatile
        private var instance: AppDatabase? = null
         fun instance (context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context)
                .also {
                    instance = it
                }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "topGamesDatabase"
        ).build()
    }
}
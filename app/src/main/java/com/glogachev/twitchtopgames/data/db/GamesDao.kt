package com.glogachev.twitchtopgames.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Single

@Dao
abstract class GamesDao {
    @Transaction
    open fun updateDatabaseData(newGameDB: List<GameDB>) {
        deleteListOfGames()
        addListOfGames(newGameDB)
    }

    @Insert
    abstract fun addListOfGames(gameDB: List<GameDB>)

    @Query("delete from GameDB ")
    abstract fun deleteListOfGames()

    @Query("SELECT * FROM gameDB")
    abstract fun getAllGames(): Single<List<GameDB>>

}


package com.glogachev.twitchtopgames.data

import com.glogachev.twitchtopgames.data.db.GameDB
import com.glogachev.twitchtopgames.data.db.GamesDao
import com.glogachev.twitchtopgames.data.mappers.toDB
import com.glogachev.twitchtopgames.data.mappers.toDomain
import com.glogachev.twitchtopgames.data.retrofit.TopGamesApi
import com.glogachev.twitchtopgames.domain.TopGamesRepository
import io.reactivex.Single

class TopGamesRepositoryImpl(
    private val apiInterface: TopGamesApi,
    private val dbInterface: GamesDao
) : TopGamesRepository {
    override fun getTopGames(): Single<List<GameDB>> {
        return apiInterface
            .getTopGames()
            .map { gamesNW ->
                val gamesDB = gamesNW.toDomain().map { it.toDB() }
                return@map gamesDB
            }
            .doOnSuccess {
                dbInterface.updateDatabaseData(it)
            }
            .onErrorResumeNext {
                dbInterface.getAllGames()
            }
    }
}
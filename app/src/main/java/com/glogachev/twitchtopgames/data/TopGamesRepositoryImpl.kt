package com.glogachev.twitchtopgames.data

import com.glogachev.twitchtopgames.data.db.GamesDao
import com.glogachev.twitchtopgames.data.mappers.toDB
import com.glogachev.twitchtopgames.data.mappers.toDomain
import com.glogachev.twitchtopgames.data.retrofit.TopGamesApi
import com.glogachev.twitchtopgames.domain.StoreResult
import com.glogachev.twitchtopgames.domain.TopGamesRepository
import com.glogachev.twitchtopgames.domain.model.GameDomain
import com.glogachev.twitchtopgames.generics.toNetworkResult
import io.reactivex.Single

class TopGamesRepositoryImpl(
    private val apiInterface: TopGamesApi,
    private val dbInterface: GamesDao
) : TopGamesRepository {

    private var limit = 10

    override fun getFirstGamePage(): Single<StoreResult<List<GameDomain>>> =
        getTopGames()

    override fun getNextGamesPage(): Single<StoreResult<List<GameDomain>>> {
        limit += 10
        return getTopGames()
    }

    private fun getTopGames() = apiInterface
        .getTopGames(limit = limit.toString())
        .map { gamesNW ->
            dbInterface.updateDatabaseData(gamesNW.toDB())
            gamesNW.toDomain()
        }
        .onErrorResumeNext {
            dbInterface.getAllGames()
                .map { gamesDB ->
                    if (gamesDB.isNullOrEmpty()) {
                        throw it
                    } else {
                        gamesDB.map { it.toDomain() }
                    }
                }
        }
        .toNetworkResult()

}
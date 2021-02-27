package com.glogachev.twitchtopgames.data

import com.glogachev.twitchtopgames.data.db.GameDB
import com.glogachev.twitchtopgames.data.db.GamesDao
import com.glogachev.twitchtopgames.data.mappers.toDB
import com.glogachev.twitchtopgames.data.mappers.toDomain
import com.glogachev.twitchtopgames.data.retrofit.TopGamesApi
import com.glogachev.twitchtopgames.domain.StoreResult
import com.glogachev.twitchtopgames.domain.TopGamesRepository
import io.reactivex.Single

class TopGamesRepositoryImpl(
    private val apiInterface: TopGamesApi,
    private val dbInterface: GamesDao
) : TopGamesRepository {
    override fun getTopGames(): Single<StoreResult<List<GameDB>>> {
        return apiInterface
            .getTopGames()
            .map { gamesNW ->
                gamesNW.toDomain().map { it.toDB() }
            }
            .map<StoreResult<List<GameDB>>> { gamesNW ->
                dbInterface.updateDatabaseData(gamesNW)
                return@map StoreResult.SuccessResult(gamesNW)
            }
            .onErrorResumeNext {
                dbInterface.getAllGames()
                    .map { gamesDB ->
                        if (gamesDB.isNullOrEmpty()) {
                            StoreResult.Error(it)
                        } else {
                            StoreResult.SuccessResult(gamesDB)
                        }
                    }
            }
            .onErrorReturn {
                return@onErrorReturn StoreResult.Error(it)
            }
    }
}
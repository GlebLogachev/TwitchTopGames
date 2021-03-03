package com.glogachev.twitchtopgames.data

import com.glogachev.twitchtopgames.data.db.GamesDao
import com.glogachev.twitchtopgames.data.mappers.toDB
import com.glogachev.twitchtopgames.data.mappers.toDomain
import com.glogachev.twitchtopgames.data.retrofit.TopGamesApi
import com.glogachev.twitchtopgames.domain.StoreResult
import com.glogachev.twitchtopgames.domain.TopGamesRepository
import com.glogachev.twitchtopgames.domain.model.GameDomain
import io.reactivex.Single

class TopGamesRepositoryImpl(
    private val apiInterface: TopGamesApi,
    private val dbInterface: GamesDao
) : TopGamesRepository {
    override fun getTopGames(): Single<StoreResult<List<GameDomain>>> {

        return apiInterface
            .getTopGames()
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

    private fun <T : Any> Single<T>.toNetworkResult(): Single<StoreResult<T>> {
        return map<StoreResult<T>> { result ->
            StoreResult.SuccessResult(result)
        }
            .onErrorReturn {
                StoreResult.Error(it)
            }
    }
}
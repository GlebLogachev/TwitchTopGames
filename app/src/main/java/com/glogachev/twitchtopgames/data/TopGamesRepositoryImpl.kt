package com.glogachev.twitchtopgames.data

import com.glogachev.twitchtopgames.data.db.GamesDao
import com.glogachev.twitchtopgames.data.mappers.toDB
import com.glogachev.twitchtopgames.data.mappers.toDomain
import com.glogachev.twitchtopgames.data.retrofit.NetworkThrowable
import com.glogachev.twitchtopgames.data.retrofit.TopGamesApi
import com.glogachev.twitchtopgames.domain.StoreResult
import com.glogachev.twitchtopgames.domain.TopGamesRepository
import com.glogachev.twitchtopgames.domain.model.GameDomain
import com.glogachev.twitchtopgames.domain.model.sortingError
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
                            val a = gamesDB.map { it.toDomain() }
                            return@map a
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
                val error = when (it) {
                    is NetworkThrowable -> {
                        val errorModelDomain = it.errorModelNW.toDomain()
                        errorModelDomain
                    }
                    else -> {
                        sortingError(it)
                    }
                }
                StoreResult.Error(error = error)
            }
    }
}
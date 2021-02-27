package com.glogachev.twitchtopgames.domain

import com.glogachev.twitchtopgames.data.db.GameDB
import io.reactivex.Single

interface TopGamesRepository {
    fun getTopGames(): Single<StoreResult<List<GameDB>>>
}
package com.glogachev.twitchtopgames.domain

import com.glogachev.twitchtopgames.domain.model.GameDomain
import io.reactivex.Single

interface TopGamesRepository {
    fun getTopGames(): Single<StoreResult<List<GameDomain>>>
}
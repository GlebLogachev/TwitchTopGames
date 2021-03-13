package com.glogachev.twitchtopgames.domain

import com.glogachev.twitchtopgames.domain.model.GameDomain
import io.reactivex.Single

interface TopGamesRepository {
    fun getFirstGamePage(): Single<StoreResult<List<GameDomain>>>
    fun getNextGamesPage(): Single<StoreResult<List<GameDomain>>>
}
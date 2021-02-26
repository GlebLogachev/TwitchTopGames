package com.glogachev.twitchtopgames.domain
import com.glogachev.twitchtopgames.data.db.GameDB
import com.glogachev.twitchtopgames.data.model.TopGamesNW
import io.reactivex.Single

interface TopGamesRepository {
   fun getTopGames(): Single<List<GameDB>>
}
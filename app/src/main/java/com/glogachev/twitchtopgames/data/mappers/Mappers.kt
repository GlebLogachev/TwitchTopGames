package com.glogachev.twitchtopgames.data.mappers

import com.glogachev.twitchtopgames.data.db.GameDB
import com.glogachev.twitchtopgames.data.model.TopGamesNW
import com.glogachev.twitchtopgames.domain.model.GameDomain


internal fun TopGamesNW.toDB(): List<GameDB> {
    return top.map {
        GameDB(
            id = it.game.id,
            viewers = it.viewers,
            channels = it.channels,
            gameName = it.game.name,
            image = it.game.box.large
        )
    }
}
internal fun TopGamesNW.toDomain(): List<GameDomain> {
    return top.map {
        GameDomain(
            id = it.game.id,
            channels = it.channels,
            viewers = it.viewers,
            gameName = it.game.name,
            image = it.game.box.large
        )
    }
}

internal fun GameDB.toDomain(): GameDomain {
    return GameDomain(
        id = id,
        channels = channels,
        viewers = viewers,
        image = image,
        gameName = gameName
    )
}

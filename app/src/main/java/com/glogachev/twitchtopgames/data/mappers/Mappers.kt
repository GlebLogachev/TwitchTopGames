package com.glogachev.twitchtopgames.data.mappers

import com.glogachev.twitchtopgames.data.db.GameDB
import com.glogachev.twitchtopgames.data.model.BoxItemsNW
import com.glogachev.twitchtopgames.data.model.GameItemsNW
import com.glogachev.twitchtopgames.data.model.TopGamesNW
import com.glogachev.twitchtopgames.domain.model.BoxItems
import com.glogachev.twitchtopgames.domain.model.GameItems
import com.glogachev.twitchtopgames.domain.model.Top


internal fun TopGamesNW.toDomain(): List<Top> {
    return top.map { item ->
        Top(
            channels = item.channels,
            viewers = item.viewers,
            game = item.game.toDomain()
             )
           }
    }
internal fun GameItemsNW.toDomain(): GameItems {
    return GameItems(
        id = id,
        name = name,
        box = box.toDomain()
    )
}
internal fun BoxItemsNW.toDomain(): BoxItems {
    return BoxItems(
        large = large
    )
}
internal fun Top.toDB(): GameDB {
    return GameDB (
        id = game.id,
        gameName = game.name,
        image = game.box.large,
        viewers = viewers,
        channels = channels
    )
}

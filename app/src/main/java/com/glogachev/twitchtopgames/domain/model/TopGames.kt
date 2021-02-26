package com.glogachev.twitchtopgames.domain.model

data class Top(
    val channels: String,
    val viewers: String,
    val game: GameItems
)
data class GameItems(
    val box: BoxItems,
    val name: String
)
data class BoxItems(
    val large: String
)


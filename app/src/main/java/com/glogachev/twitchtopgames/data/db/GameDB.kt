package com.glogachev.twitchtopgames.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class GameDB(
    @PrimaryKey var id: Int,
    val gameName: String,
    val image: String,
    val viewers: String,
    val channels: String
): Serializable
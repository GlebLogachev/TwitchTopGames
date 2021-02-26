package com.glogachev.twitchtopgames.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class GameDB(
    val gameName: String,
    val image: String,
    val viewers: String,
    val channels: String
): Serializable {
@PrimaryKey (autoGenerate = true)
var id: Int = 0
}
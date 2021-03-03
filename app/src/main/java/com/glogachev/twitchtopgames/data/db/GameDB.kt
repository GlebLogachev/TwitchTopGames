package com.glogachev.twitchtopgames.data.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class GameDB(
    @PrimaryKey(autoGenerate = true)
    var key: Int = 0,
    var id: Int,
    val gameName: String,
    val image: String,
    val viewers: String,
    val channels: String
) : Parcelable
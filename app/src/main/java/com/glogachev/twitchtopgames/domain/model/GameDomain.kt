package com.glogachev.twitchtopgames.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameDomain(
    val id: Int,
    val channels: String,
    val viewers: String,
    val image: String,
    val gameName: String
) : Parcelable


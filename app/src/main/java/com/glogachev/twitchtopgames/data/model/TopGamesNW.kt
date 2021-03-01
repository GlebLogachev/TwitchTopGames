package com.glogachev.twitchtopgames.data.model

import com.google.gson.annotations.SerializedName

data class TopGamesNW(
    @SerializedName("_total")
    val _total: String,
    @SerializedName("top")
    val top: List<TopNW>
)

data class TopNW(
    @SerializedName("channels")
    val channels: String,
    @SerializedName("viewers")
    val viewers: String,
    @SerializedName("game")
    val game: GameItemsNW
)

data class GameItemsNW(
    @SerializedName("_id")
    val id: Int,
    @SerializedName("box")
    val box: BoxItemsNW,
    @SerializedName("giantbomb_id")
    val giantbombId: String,
    @SerializedName("logo")
    val logo: LogoTypesNW,
    @SerializedName("name")
    val name: String
)
data class LogoTypesNW(
    @SerializedName("large")
    val large: String,
    @SerializedName("medium")
    val medium: String,
    @SerializedName("small")
    val small: String,
    @SerializedName("template")
    val template: String
)

data class BoxItemsNW(
    @SerializedName("large")
    val large: String,
    @SerializedName("medium")
    val medium: String,
    @SerializedName("small")
    val small: String,
    @SerializedName("template")
    val template: String
)

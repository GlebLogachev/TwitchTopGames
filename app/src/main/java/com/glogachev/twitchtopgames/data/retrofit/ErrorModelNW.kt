package com.glogachev.twitchtopgames.data.retrofit


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ErrorModelNW(
    @SerializedName("error")
    val error: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)
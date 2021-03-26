package com.glogachev.twitchtopgames.data.retrofit

import com.glogachev.twitchtopgames.data.model.TopGamesNW
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TopGamesApi {
    @Headers(
        "Content-Type: application/json",
        "Accept: application/vnd.twitchtv.v5+json",
        "Client-ID: 0mbng65k95r72we0j1ii1uf02cssrs"
    )
    @GET("kraken/games/top")
    fun getTopGames(
        @Query("limit") limit: String
    ): Single<TopGamesNW>
}

class NetworkThrowable(
    val errorModelNW: ErrorModelNW
) : Throwable()
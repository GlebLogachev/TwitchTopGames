package com.glogachev.twitchtopgames.data.retrofit

import com.glogachev.twitchtopgames.data.model.TopGamesNW
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface TopGamesApi {
    @Headers(
        "Content-Type: application/json",
        "Accept: application/vnd.twitchtv.v5+json",
        "Client-ID: 0mbng65k95r72we0j1ii1uf02cssrs"
    )
    @GET("kraken/games/top")
    fun getTopGames(): Single<TopGamesNW>

    companion object {
        var BASE_URL = "https://api.twitch.tv/"
        fun create(okHttpClient: OkHttpClient): TopGamesApi {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
            return retrofit.create(TopGamesApi::class.java)
        }
    }
}
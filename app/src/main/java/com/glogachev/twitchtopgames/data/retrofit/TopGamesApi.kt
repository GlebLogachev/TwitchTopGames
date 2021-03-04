package com.glogachev.twitchtopgames.data.retrofit

import androidx.annotation.Keep
import com.glogachev.twitchtopgames.data.model.TopGamesNW
import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import java.lang.reflect.Type

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
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(RxAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
            return retrofit.create(TopGamesApi::class.java)
        }
    }
}

internal class RxAdapterFactory private constructor(
    private val rxAdapterFactory: CallAdapter.Factory
) : CallAdapter.Factory() {

    companion object {
        fun createWithScheduler(
            scheduler: Scheduler
        ): CallAdapter.Factory {
            return RxAdapterFactory(
                rxAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(scheduler)
            )
        }
    }

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val adapter = rxAdapterFactory.get(returnType, annotations, retrofit) ?: return null
        return RxAdapter(retrofit, adapter)
    }
}

private class RxAdapter<R, T>(
    private val retrofit: Retrofit,
    private val adapter: CallAdapter<R, T>
) : CallAdapter<R, T> {
    override fun responseType(): Type {
        return adapter.responseType()
    }

    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
    override fun adapt(call: Call<R>): T {

        val adaptedValue = adapter.adapt(call)
        return when (adaptedValue) {
            is Single<*> -> {
                adaptedValue.onErrorResumeNext { error: Throwable ->
                    Single.error(
                        errorConverter(
                            retrofit,
                            error,
                            call.request()
                        )
                    )
                }
            }
            is Completable -> {
                adaptedValue.onErrorResumeNext { error: Throwable ->
                    Completable.error(
                        errorConverter(
                            retrofit,
                            error,
                            call.request()
                        )
                    )
                }
            }
            else -> throw IllegalStateException("unknown reactive return type in adapter: $adaptedValue")
        } as T
    }
}

fun errorConverter(retrofit: Retrofit, error: Throwable, request: Request): Throwable {
    if (error is HttpException) {
        val errorBody = error.response()?.errorBody()
        val converter =
            retrofit.responseBodyConverter<ErrorModelNW>(ErrorModelNW::class.java, emptyArray())
        errorBody?.let {
            converter.convert(it)?.let { errorModel ->
                return DomainThrowable(errorModel)
            }
        }
    }
    return error
}

@Keep
data class ErrorModelNW(
    @SerializedName("error")
    val errorCode: Int,
    @SerializedName("status")
    val errorStatus: Int,
    @SerializedName("message")
    val errorMessage: String
)

class DomainThrowable(
    val errorModelNW: ErrorModelNW
) : Throwable()
package com.glogachev.twitchtopgames.data.retrofit

import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import okhttp3.Request
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type

class CustomAdapterFactory private constructor(
    private val rxAdapterFactory: CallAdapter.Factory
) : CallAdapter.Factory() {

    companion object {
        fun createWithScheduler(
            scheduler: Scheduler
        ): CallAdapter.Factory {
            return CustomAdapterFactory(
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
                    val errorReturn = errorConverter(
                        retrofit,
                        error,
                        call.request()
                    )
                    Single.error(
                        errorReturn
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
            else -> {
                throw IllegalStateException("unknown reactive return type in adapter: $adaptedValue")
            }
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
                return NetworkThrowable(errorModelNW = errorModel)
            }
        }
    }
    return error
}
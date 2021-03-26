package com.glogachev.twitchtopgames.utils

import com.glogachev.twitchtopgames.data.mappers.toDomain
import com.glogachev.twitchtopgames.data.retrofit.NetworkThrowable
import com.glogachev.twitchtopgames.domain.StoreResult
import com.glogachev.twitchtopgames.domain.model.sortingError
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T : Any> Single<T>.toNetworkResult(): Single<StoreResult<T>> {
    return map<StoreResult<T>> { result ->
        StoreResult.SuccessResult(result)
    }
        .onErrorReturn {
            val error = when (it) {
                is NetworkThrowable -> {
                    val errorModelDomain = it.errorModelNW.toDomain()
                    errorModelDomain
                }
                else -> {
                    sortingError(it)
                }
            }
            StoreResult.Error(error = error)
        }
}

fun <T> Single<T>.schedule() = scheduling(this)

private fun <T> scheduling(single: Single<T>): Single<T> {
    return single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}
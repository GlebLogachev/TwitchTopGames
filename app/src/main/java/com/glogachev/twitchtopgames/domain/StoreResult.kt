package com.glogachev.twitchtopgames.domain

import com.glogachev.twitchtopgames.domain.model.ErrorModelDomain

sealed class StoreResult<out T> {
    data class SuccessResult<T>(val data: T) : StoreResult<T>()
    data class Error(val error: ErrorModelDomain) : StoreResult<Nothing>()
    object Loading : StoreResult<Nothing>()

    fun isSuccess(): Boolean = this is SuccessResult
    fun isFault(): Boolean = this is Error
}
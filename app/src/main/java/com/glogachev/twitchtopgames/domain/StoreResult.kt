package com.glogachev.twitchtopgames.domain

sealed class StoreResult<out T> {
    data class SuccessResult<T>(val data: T) : StoreResult<T>()
    data class Error(val error: Throwable) : StoreResult<Nothing>()
    object Loading : StoreResult<Nothing>()

    fun isSuccess(): Boolean = this is SuccessResult
    fun isFault(): Boolean = this is Error
}
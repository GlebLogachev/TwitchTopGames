package com.glogachev.twitchtopgames.domain.model

import java.net.UnknownHostException

data class ErrorModelDomain(
    val error: String,
    val message: String,
    val status: Int
)

fun sortingError(error: Throwable): ErrorModelDomain {
    return when (error) {
        is UnknownHostException -> ErrorModelDomain(
            error = "network problem",
            message = "Проблемы с сетью",
            status = 402
        )

        else -> {
            ErrorModelDomain(
                error = "unknown exception",
                message = "Неизвестная ошибка",
                status = 288
            )
        }
    }
}
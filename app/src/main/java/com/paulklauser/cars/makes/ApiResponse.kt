package com.paulklauser.cars.makes

import timber.log.Timber

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data object Error : ApiResponse<Nothing>()

    companion object {
        @Suppress("TooGenericExceptionCaught")
        suspend fun <T> handleApiResponse(apiCall: suspend () -> T): ApiResponse<T> {
            return try {
                Success(apiCall())
            } catch (e: Exception) {
                Timber.e(e)
                Error
            }
        }
    }
}
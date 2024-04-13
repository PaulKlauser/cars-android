package com.paulklauser.cars.makes

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data object Error : ApiResponse<Nothing>()
}
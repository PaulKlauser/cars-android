package com.paulklauser.cars.makes

import timber.log.Timber
import javax.inject.Inject

class MakesRepository @Inject constructor(
    private val makesService: MakesService
) {

    @Suppress("TooGenericExceptionCaught")
    suspend fun getMakes(): ApiResponse<List<Make>> {
        return try {
            ApiResponse.Success(makesService.getMakes().data.map { Make.fromApi(it) })
        } catch (e: Exception) {
            Timber.e(e)
            ApiResponse.Error
        }
    }

}
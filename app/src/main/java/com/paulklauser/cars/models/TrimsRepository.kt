package com.paulklauser.cars.models

import com.paulklauser.cars.commonapi.CarService
import javax.inject.Inject

class TrimsRepository @Inject constructor(
    private val carService: CarService
) {

    suspend fun getTrims(makeModelId: String, year: String): List<Trim> {
        return carService.getTrims(makeModelId, year).data.map { Trim.fromApiTrim(it) }
    }

}
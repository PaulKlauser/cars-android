package com.paulklauser.cars.makes

import com.paulklauser.cars.models.ModelsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CarService {
    @GET("api/makes")
    suspend fun getMakes(): MakesResponse

    @GET("api/models")
    suspend fun getModels(@Query("make_id") makeId: String, @Query("year") year: String): ModelsResponse
}
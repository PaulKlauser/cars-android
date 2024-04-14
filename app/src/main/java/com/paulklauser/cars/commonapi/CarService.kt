package com.paulklauser.cars.commonapi

import com.paulklauser.cars.makes.MakesResponse
import com.paulklauser.cars.modeldetail.TrimResponse
import com.paulklauser.cars.models.ModelsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CarService {
    @GET("api/makes")
    suspend fun getMakes(): MakesResponse

    @GET("api/models")
    suspend fun getModels(@Query("make_id") makeId: String, @Query("year") year: String): ModelsResponse

    @GET("api/trims/{trimId}")
    suspend fun getTrim(@Path("trimId") trimId: String): TrimResponse
}
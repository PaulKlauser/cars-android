package com.paulklauser.cars.makes

import retrofit2.http.GET

interface MakesService {
    @GET("api/makes")
    suspend fun getMakes(): MakesResponse
}
package com.paulklauser.cars.commonapi

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object CarServiceModule {
    @Provides
    fun carService(retrofit: Retrofit): CarService {
        return retrofit.create(CarService::class.java)
    }
}
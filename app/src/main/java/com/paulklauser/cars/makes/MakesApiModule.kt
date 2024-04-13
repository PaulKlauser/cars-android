package com.paulklauser.cars.makes

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object MakesApiModule {
    @Provides
    fun makesService(retrofit: Retrofit): MakesService {
        return retrofit.create(MakesService::class.java)
    }
}
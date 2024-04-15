package com.paulklauser.cars

import com.paulklauser.cars.commonapi.CarService
import com.paulklauser.cars.commonapi.CarServiceModule
import com.paulklauser.cars.commonapi.FakeCarService
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CarServiceModule::class]
)
object TestCarServiceModule {
    @Provides
    fun makesService(): CarService {
        return FakeCarService()
    }
}
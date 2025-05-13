package com.ali.smartlight.di

import com.ali.smartlight.data.SmartLightRepositoryImpl
import com.ali.smartlight.domain.repository.SmartLightRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class InterfaceModule {

    @Binds
    abstract fun bindSmartLightRepository(
        smartLightRepositoryImpl: SmartLightRepositoryImpl
    ): SmartLightRepository
}
package com.ali.smartlight.di

import android.content.Context
import com.ali.smartlight.data.SmartLightRepositoryImpl
import com.ali.smartlight.data.mqtt.MqttClientManager
import com.ali.smartlight.domain.repository.SmartLightRepository
import com.ali.smartlight.domain.usecase.ToggleLightUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMqttClientManager(@ApplicationContext context: Context): MqttClientManager {
        return MqttClientManager(context)
    }

    @Provides
    @Singleton
    fun provideSmartLightRepositoryImpl(mqttClientManager: MqttClientManager): SmartLightRepositoryImpl {
        return SmartLightRepositoryImpl(mqttClientManager)
    }

    @Provides
    @Singleton
    fun provideToggleLightUseCase(repository: SmartLightRepository): ToggleLightUseCase {
        return ToggleLightUseCase(repository)
    }
}
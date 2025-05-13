package com.ali.smartlight.di

import com.ali.smartlight.data.SmartLightRepositoryImpl
import com.ali.smartlight.data.mqtt.MqttClientManager
import com.ali.smartlight.domain.repository.SmartLightRepository
import com.ali.smartlight.domain.usecase.ConnectUseCase
import com.ali.smartlight.domain.usecase.DisconnectUseCase
import com.ali.smartlight.domain.usecase.PublishBrightnessUseCase
import com.ali.smartlight.domain.usecase.ToggleLightUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMqttClientManager(): MqttClientManager {
        return MqttClientManager()
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

    @Provides
    @Singleton
    fun providePublishBrightnessUseCase(repository: SmartLightRepository): PublishBrightnessUseCase {
        return PublishBrightnessUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideConnectUseCase(repository: SmartLightRepository): ConnectUseCase {
        return ConnectUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDisconnectUseCase(repository: SmartLightRepository): DisconnectUseCase {
        return DisconnectUseCase(repository)
    }
}
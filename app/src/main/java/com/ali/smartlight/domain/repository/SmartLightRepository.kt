package com.ali.smartlight.domain.repository

import com.ali.smartlight.domain.model.LightState
import kotlinx.coroutines.flow.Flow

interface SmartLightRepository {
    fun toggleLight(isOn: Boolean)
    fun setBrightness(brightness: Int)
    fun observeLightState(): Flow<LightState>
}
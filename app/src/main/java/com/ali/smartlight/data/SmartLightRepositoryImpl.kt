package com.ali.smartlight.data

import com.ali.smartlight.data.mqtt.MqttClientManager
import com.ali.smartlight.domain.model.LightState
import com.ali.smartlight.domain.repository.SmartLightRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class SmartLightRepositoryImpl @Inject constructor(
    private val mqttClientManager: MqttClientManager
) : SmartLightRepository {

    override fun toggleLight(isOn: Boolean) {
        mqttClientManager.publishState(isOn)
    }

    override fun setBrightness(brightness: Int) {
        mqttClientManager.publishBrightness(brightness)
    }

    override fun observeLightState(): Flow<LightState> = combine(
        mqttClientManager.lightState,
        mqttClientManager.brightness
    ) { isOn, brightness ->
        LightState(isOn, brightness)
    }
}
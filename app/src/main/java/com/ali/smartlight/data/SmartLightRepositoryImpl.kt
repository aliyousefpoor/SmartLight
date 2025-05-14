package com.ali.smartlight.data

import com.ali.smartlight.data.mqtt.MqttClientManager
import com.ali.smartlight.domain.repository.SmartLightRepository
import javax.inject.Inject

class SmartLightRepositoryImpl @Inject constructor(
    private val mqttClientManager: MqttClientManager,
) : SmartLightRepository {
    override val isConnected = mqttClientManager.isConnected
    override val lightState = mqttClientManager.lightState
    override val brightness = mqttClientManager.brightness

    override fun connect() = mqttClientManager.connect()
    override fun publishState(isOn: Boolean) = mqttClientManager.publishState(isOn)
    override fun publishBrightness(value: Int) = mqttClientManager.publishBrightness(value)
    override fun disconnect() = mqttClientManager.disconnect()
}
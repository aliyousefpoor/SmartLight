package com.ali.smartlight.data.mqtt

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MqttClientManager @Inject constructor() {

    private val brokerUrl = "tcp://192.168.33.16:1883"
    private val clientId = MqttClient.generateClientId()
    private val mqttClient = MqttClient(brokerUrl, clientId, MemoryPersistence())

    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    private val _lightState = MutableStateFlow(false)
    val lightState = _lightState.asStateFlow()

    private val _brightness = MutableStateFlow(0)
    val brightness = _brightness.asStateFlow()

    fun connect() {
        val options = MqttConnectOptions().apply {
            isCleanSession = true
        }

        mqttClient.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                _isConnected.value = false
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                message?.toString()?.let { payload ->
                    when (topic) {
                        "smartlight/status/state" -> _lightState.value = payload == "on"
                        "smartlight/status/brightness" -> _brightness.value = payload.toIntOrNull() ?: 0
                    }
                }
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {}
        })

        try {
            mqttClient.connect(options)
            _isConnected.value = true
            mqttClient.subscribe("smartlight/status/state", 1)
            mqttClient.subscribe("smartlight/status/brightness", 1)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun publishState(isOn: Boolean) {
        val msg = if (isOn) "on" else "off"
        publish("smartlight/control/state", msg)
    }

    fun publishBrightness(value: Int) {
        publish("smartlight/control/brightness", value.toString())
    }

    private fun publish(topic: String, message: String) {
        try {
            val mqttMessage = MqttMessage()
            mqttMessage.payload = message.toByteArray()
            mqttClient.publish(topic, mqttMessage)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun disconnect() {
        if (mqttClient.isConnected) {
            mqttClient.disconnect()
        }
    }
}
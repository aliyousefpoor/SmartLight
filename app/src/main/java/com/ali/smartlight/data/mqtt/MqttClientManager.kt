package com.ali.smartlight.data.mqtt

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MqttClientManager @Inject constructor(
    private val context: Context
) {

    private val brokerUrl = "tcp://broker.hivemq.com:1883"
    private val clientId = MqttClient.generateClientId()

    private val topicState = "smartlight/status/state"
    private val topicBrightness = "smartlight/status/brightness"

    private val topicControlState = "smartlight/control/state"
    private val topicControlBrightness = "smartlight/control/brightness"

    private lateinit var mqttClient: MqttAndroidClient

    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    private val _lightState = MutableStateFlow(false)
    val lightState = _lightState.asStateFlow()

    private val _brightness = MutableStateFlow(0)
    val brightness = _brightness.asStateFlow()

    fun connect() {
        mqttClient = MqttAndroidClient(context, brokerUrl, clientId)
        val options = MqttConnectOptions()
        options.isCleanSession = true

        mqttClient.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                _isConnected.value = false
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                message?.toString()?.let { payload ->
                    when (topic) {
                        topicState -> _lightState.value = payload == "on"
                        topicBrightness -> _brightness.value = payload.toIntOrNull() ?: 0
                    }
                }
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {}
        })

        mqttClient.connect(options, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                _isConnected.value = true
                subscribe(topicState)
                subscribe(topicBrightness)
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.e("MQTT", "Connection failed", exception)
            }
        })
    }

    private fun subscribe(topic: String) {
        mqttClient.subscribe(topic, 1)
    }

    fun publishState(isOn: Boolean) {
        val msg = if (isOn) "on" else "off"
        publish(topicControlState, msg)
    }

    fun publishBrightness(value: Int) {
        publish(topicControlBrightness, value.toString())
    }

    private fun publish(topic: String, message: String) {
        try {
            val mqttMessage = MqttMessage()
            mqttMessage.payload = message.toByteArray()
            mqttClient.publish(topic, mqttMessage)
        } catch (e: Exception) {
            Log.e("MQTT", "Publish failed", e)
        }
    }

    fun disconnect() {
        if (mqttClient.isConnected) {
            mqttClient.disconnect()
        }
    }
}
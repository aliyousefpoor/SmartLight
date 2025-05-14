package com.ali.smartlight.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface SmartLightRepository {
    val isConnected: StateFlow<Boolean>
    val lightState: StateFlow<Boolean>
    val brightness: StateFlow<Int>

    fun connect()
    fun publishState(isOn: Boolean)
    fun publishBrightness(value: Int)
    fun disconnect()
}
package com.ali.smartlight.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ali.smartlight.domain.repository.SmartLightRepository
import com.ali.smartlight.domain.usecase.ConnectUseCase
import com.ali.smartlight.domain.usecase.DisconnectUseCase
import com.ali.smartlight.domain.usecase.PublishBrightnessUseCase
import com.ali.smartlight.domain.usecase.ToggleLightUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SmartLightViewModel @Inject constructor(
    private val repository: SmartLightRepository,
    private val toggleLightUseCase: ToggleLightUseCase,
    private val connectUseCase: ConnectUseCase,
    private val publishBrightnessUseCase: PublishBrightnessUseCase,
    private val disconnectUseCase: DisconnectUseCase
) : ViewModel() {

    val isConnected: StateFlow<Boolean> = repository.isConnected
    val lightState: StateFlow<Boolean> = repository.lightState
    val brightness: StateFlow<Int> = repository.brightness

    fun connect() {
        viewModelScope.launch {
            connectUseCase.invoke()
        }
    }

    fun toggleLight() {
        toggleLightUseCase.invoke(!lightState.value)
    }

    fun setBrightness(value: Int) {
        publishBrightnessUseCase.invoke(value)
    }

    override fun onCleared() {
        disconnectUseCase.invoke()
        super.onCleared()
    }
}
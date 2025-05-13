package com.ali.smartlight.domain.usecase

import com.ali.smartlight.domain.repository.SmartLightRepository

class ToggleLightUseCase(private val repository: SmartLightRepository) {
    operator fun invoke(isOn: Boolean) = repository.publishState(isOn)
}
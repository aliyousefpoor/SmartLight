package com.ali.smartlight.domain.usecase

import com.ali.smartlight.domain.repository.SmartLightRepository

class DisconnectUseCase(private val repository: SmartLightRepository) {
    operator fun invoke() = repository.disconnect()
}
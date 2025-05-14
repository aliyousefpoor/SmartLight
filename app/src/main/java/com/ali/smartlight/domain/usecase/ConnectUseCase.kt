package com.ali.smartlight.domain.usecase

import com.ali.smartlight.domain.repository.SmartLightRepository

class ConnectUseCase(private val repository: SmartLightRepository) {
    operator fun invoke() = repository.connect()
}
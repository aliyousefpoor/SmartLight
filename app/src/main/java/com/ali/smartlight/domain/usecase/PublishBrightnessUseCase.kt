package com.ali.smartlight.domain.usecase

import com.ali.smartlight.domain.repository.SmartLightRepository

class PublishBrightnessUseCase(private val repository: SmartLightRepository) {
    operator fun invoke(value: Int) = repository.publishBrightness(value)
}
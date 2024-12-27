package com.credibanco.sdk.repository

import com.credibanco.sdk.domain.dto.PeripheralDTO

interface PeripheralManagementRepository {
    suspend operator fun invoke(): PeripheralDTO
}
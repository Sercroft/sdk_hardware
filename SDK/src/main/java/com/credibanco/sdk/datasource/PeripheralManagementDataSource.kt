package com.credibanco.sdk.datasource

import com.credibanco.sdk.domain.dto.PeripheralDTO

interface PeripheralManagementDataSource {
    suspend operator fun invoke() : PeripheralDTO
}
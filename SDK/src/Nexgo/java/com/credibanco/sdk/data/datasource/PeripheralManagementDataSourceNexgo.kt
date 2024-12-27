package com.credibanco.sdk.data.datasource

import com.credibanco.sdk.domain.dto.PeripheralDTO

interface PeripheralManagementDataSourceNexgo {
    suspend operator fun invoke() : PeripheralDTO
}
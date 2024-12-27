package com.credibanco.sdk.repository.impl

import com.credibanco.sdk.datasource.PeripheralManagementDataSource
import com.credibanco.sdk.domain.dto.PeripheralDTO
import com.credibanco.sdk.repository.PeripheralManagementRepository

class PeripheralManagementRepositoryImpl (
    private val dataSource: PeripheralManagementDataSource
) : PeripheralManagementRepository {
    override suspend fun invoke(): PeripheralDTO {
        return dataSource.invoke()
    }
}
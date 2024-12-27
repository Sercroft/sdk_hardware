package com.credibanco.sdk.datasource

interface DeviceDataSource {
    suspend fun enableUsdCDC()
    suspend fun disableUsdCDC()
}
package com.credibanco.sdk.main.data.datasource.impl

import com.credibanco.sdk.datasource.DeviceDataSource

class DeviceDataSourceIngenicoImpl: DeviceDataSource {
    override suspend fun enableUsdCDC() {/* This isn't need for Ingenico */ }

    override suspend fun disableUsdCDC() {/* This isn't need for Ingenico */}
}
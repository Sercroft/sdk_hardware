package com.credibanco.sdk.data.datasource.impl

import com.credibanco.sdk.datasource.PeripheralManagementDataSource
import com.credibanco.sdk.domain.dto.PeripheralDTO
import com.credibanco.sdk.util.Constants.N6
import com.credibanco.sdk.util.Constants.N6S
import com.credibanco.sdk.util.Constants.N86
import com.credibanco.sdk.util.Constants.UN20
import com.nexgo.oaf.apiv3.DeviceEngine
import javax.inject.Inject

class PeripheralManagementDataSourceNexgoImpl @Inject constructor(
    private val deviceEngine: DeviceEngine
): PeripheralManagementDataSource {
    override suspend fun invoke(): PeripheralDTO {
        val deviceInfo = deviceEngine.deviceInfo
        val peripheralDTO = PeripheralDTO("UK")

        when (deviceInfo.model) {
            N86 -> {
                peripheralDTO.apply {
                    model       = deviceInfo.model
                    flash       = false
                    band        = true
                    camera      = true
                    frontCamera = false
                    printer     = true
                    chip        = true
                    nfc         = true
                }
            }

            N6 -> {
                peripheralDTO.apply {
                    model       = deviceInfo.model
                    flash       = true
                    band        = true
                    camera      = true
                    frontCamera = true
                    printer     = false
                    chip        = true
                    nfc         = true
                }
            }

            N6S -> {
                peripheralDTO.apply {
                    model       = deviceInfo.model
                    flash       = true
                    band        = true
                    camera      = true
                    frontCamera = true
                    printer     = false
                    chip        = true
                    nfc         = true
                }
            }

            UN20 -> {
                peripheralDTO.apply {
                    model       = deviceInfo.model
                    flash       = true
                    band        = true
                    camera      = false
                    frontCamera = true
                    printer     = false
                    chip        = true
                    nfc         = true
                }
            }
        }

        return peripheralDTO
    }
}
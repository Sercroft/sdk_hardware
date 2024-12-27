package com.credibanco.sdk.main.data.datasource

import android.os.Build
import com.credibanco.sdk.datasource.PeripheralManagementDataSource
import com.credibanco.sdk.domain.dto.PeripheralDTO
import com.credibanco.sdk.util.Constants.DX4000
import javax.inject.Inject

class PeripheralManagementDataSourceIngenicoImpl @Inject constructor() :
    PeripheralManagementDataSource {
    override suspend fun invoke(): PeripheralDTO {
        val modelDevice = Build.MODEL
        val peripheralDTO = PeripheralDTO(DX4000)

        when(modelDevice) {
            DX4000 -> {
                peripheralDTO.apply {
                    model       = modelDevice
                    flash       = true
                    band        = true
                    camera      = false
                    frontCamera = false
                    printer     = true
                    chip        = true
                    nfc         = true
                }
            }
        }
        return peripheralDTO
    }
}
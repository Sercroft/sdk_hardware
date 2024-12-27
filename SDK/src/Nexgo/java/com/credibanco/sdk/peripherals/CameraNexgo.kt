package com.credibanco.sdk.peripherals

import android.content.Context
import com.credibanco.sdk.main.IntegrationPeripheralsNexgoSDK
import com.credibanco.sdk.main.ResultHardwareSDK

class CameraNexgo(
    private val context: Context,
    private val result: ResultHardwareSDK
) {
    fun startCamera(hashCode: String) {
        IntegrationPeripheralsNexgoSDK.getSmartPosInstancePeripheralsNexgo().startCamera(
            context,
            true,
            true,
            true,
            true,
            true,
            "TÍTULO",
            10,
            10,
            "Scan tip",
            hashCode,
            resultHardwareSDK = result
        )
    }
}
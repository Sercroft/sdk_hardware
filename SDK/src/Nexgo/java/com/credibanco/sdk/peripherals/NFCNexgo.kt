package com.credibanco.sdk.peripherals.nfc

import android.content.Context
import com.credibanco.sdk.main.IntegrationPeripheralsNexgoSDK
import com.credibanco.sdk.main.ResultHardwareSDK

class NFCNexgo(
    private val context: Context,
    private val result: ResultHardwareSDK
) {
    fun startNfc(isUid: Boolean, hashCode: String) {
        IntegrationPeripheralsNexgoSDK.getSmartPosInstancePeripheralsNexgo().startNfc(
            context,
            isUid,
            hashCode,
            resultHardwareSDK = result
        )
    }
}
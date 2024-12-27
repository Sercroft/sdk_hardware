package com.credibanco.sdk.peripherals

import android.content.Context
import com.credibanco.sdk.main.IntegrationPeripheralsNexgoSDK
import com.credibanco.sdk.main.ResultHardwareSDK

class BluetoothNexgo(
    private val context: Context,
    private val result: ResultHardwareSDK
){
    fun startBluetooth(isBluetooth: Boolean, hashCode: String){
        IntegrationPeripheralsNexgoSDK.getSmartPosInstancePeripheralsNexgo().startBluetooth(
            context,
            isBluetooth,
            hashCode,
            resultHardwareSDK = result
        )
    }
}
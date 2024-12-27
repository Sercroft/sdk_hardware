package com.credibanco.sdk.main.main

import android.content.Context
import android.os.Build
import com.credibanco.sdk.main.ResultHardwareSDK

interface IntegrationPeripheralsIngenicoSDK {
    fun startNfc(
        context: Context,
        uid: Boolean,
        hashCode: String,
        resultHardwareSDK: ResultHardwareSDK
    )

    fun startPrint(
        context: Context,
        typeFace: String,
        letterSpacing: Int,
        grayLevel: String,
        hashCode: String,
        valuesToSend: ArrayList<String>,
        resultHardwareSDK: ResultHardwareSDK
    )

    fun startCamera(
        context: Context,
        showBar: Boolean,
        showBack: Boolean,
        showTitle: Boolean,
        showSwitch: Boolean,
        showMenu: Boolean,
        title: String,
        titleSize: Int,
        tipSize: Int,
        scanTip: String,
        hashCode: String,
        resultHardwareSDK: ResultHardwareSDK
    )

    fun startBluetooth(
        context: Context,
        stateBluetooth: Boolean,
        hashCode: String,
        resultHardwareSDK: ResultHardwareSDK
    )

    companion object {

        fun getSmartposInstancePeripherals(): IntegrationPeripheralsIngenicoSDK {
            val modelDevice = Build.MODEL

            return IntegrationPeripheralsSDKIngenicoImpl()
        }

    }
}
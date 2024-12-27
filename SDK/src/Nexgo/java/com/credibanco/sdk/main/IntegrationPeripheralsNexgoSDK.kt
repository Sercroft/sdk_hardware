package com.credibanco.sdk.main

import android.content.Context
import android.os.Build

interface IntegrationPeripheralsNexgoSDK {

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
        fun getSmartPosInstancePeripheralsNexgo(): IntegrationPeripheralsNexgoSDK {
            return IntegrationPeripheralsSDKNexgoImpl()
        }
    }
}
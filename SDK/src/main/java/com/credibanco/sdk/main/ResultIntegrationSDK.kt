package com.credibanco.sdk.main

import androidx.activity.result.ActivityResult

interface ResultHardwareSDK {

    fun resultActivity(activityResult: ActivityResult)

    companion object {
        var information: ResultHardwareSDK? = null

        fun getSmartPosInstancePeripherals(resultHardwareSDK: ResultHardwareSDK): ResultHardwareSDK {
            information = resultHardwareSDK
            return resultHardwareSDK
        }
    }
}
package com.credibanco.sdk.main.main

import android.content.Context
import android.content.Intent
import com.credibanco.sdk.main.ResultHardwareSDK
import com.credibanco.sdk.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntegrationPeripheralsSDKIngenicoImpl @Inject constructor() : IntegrationPeripheralsIngenicoSDK {

    override fun startNfc(
        context: Context,
        uid: Boolean,
        hashCode: String,
        resultHardwareSDK: ResultHardwareSDK
    ) {
        val shareIntent = Intent(context, MainActivityIngenicoSDK::class.java)
        val packageName = context.packageName
        ResultHardwareSDK.getSmartPosInstancePeripherals(resultHardwareSDK)

        shareIntent.putExtra(Constants.UID, uid)
        shareIntent.putExtra(Constants.HASH_CODE, hashCode)
        shareIntent.putExtra(Constants.PACKAGE_NAME, packageName)
        shareIntent.putExtra(Constants.TYPE_INTEGRATION, Constants.NFC)

        context.startActivity(shareIntent)
    }

    override fun startPrint(
        context: Context,
        typeFace: String,
        letterSpacing: Int,
        grayLevel: String,
        hashCode: String,
        valuesToSend: ArrayList<String>,
        resultHardwareSDK: ResultHardwareSDK
    ) {
        val shareIntent = Intent(context, MainActivityIngenicoSDK::class.java)
        val packageName = context.packageName
        ResultHardwareSDK.getSmartPosInstancePeripherals(resultHardwareSDK)

        shareIntent.putExtra(Constants.TYPEFACE, typeFace)
        shareIntent.putExtra(Constants.LETTER_SPACING, letterSpacing)
        shareIntent.putExtra(Constants.GRAY_LEVEL, grayLevel)
        shareIntent.putExtra(Constants.PACKAGE_NAME, packageName)
        shareIntent.putExtra(Constants.HASH_CODE, hashCode)
        shareIntent.putExtra(Intent.EXTRA_STREAM, valuesToSend)
        shareIntent.putExtra(Constants.TYPE_INTEGRATION, Constants.PRINT)

        context.startActivity(shareIntent)
    }

    override fun startCamera(
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
    ) {
        val shareIntent = Intent(context, MainActivityIngenicoSDK::class.java)
        val packageName = context.packageName
        ResultHardwareSDK.getSmartPosInstancePeripherals(resultHardwareSDK)

        shareIntent.putExtra(Constants.SHOWBAR, showBar)
        shareIntent.putExtra(Constants.SHOWBACK, showBack)
        shareIntent.putExtra(Constants.SHOWTITLE, showTitle)
        shareIntent.putExtra(Constants.SHOWSWITCH, showSwitch)
        shareIntent.putExtra(Constants.SHOWMENU, showMenu)
        shareIntent.putExtra(Constants.TITLE, title)
        shareIntent.putExtra(Constants.TITLESIZE, titleSize)
        shareIntent.putExtra(Constants.TIPSIZE, tipSize)
        shareIntent.putExtra(Constants.SCANTIP, scanTip)
        shareIntent.putExtra(Constants.HASH_CODE, hashCode)
        shareIntent.putExtra(Constants.PACKAGE_NAME, packageName)
        shareIntent.putExtra(Constants.TYPE_INTEGRATION, Constants.CAMERA)

        context.startActivity(shareIntent)
    }

    override fun startBluetooth(
        context: Context,
        stateBluetooth: Boolean,
        hashCode: String,
        resultHardwareSDK: ResultHardwareSDK
    ) {
        val shareIntent = Intent(context, MainActivityIngenicoSDK::class.java)
        val packageName = context.packageName
        ResultHardwareSDK.getSmartPosInstancePeripherals(resultHardwareSDK)

        shareIntent.putExtra(Constants.STATE_BLUETOOTH, stateBluetooth)
        shareIntent.putExtra(Constants.PACKAGE_NAME, packageName)
        shareIntent.putExtra(Constants.HASH_CODE, hashCode)
        shareIntent.putExtra(Constants.TYPE_INTEGRATION, Constants.BLUETOOTH)

        context.startActivity(shareIntent)
    }
}
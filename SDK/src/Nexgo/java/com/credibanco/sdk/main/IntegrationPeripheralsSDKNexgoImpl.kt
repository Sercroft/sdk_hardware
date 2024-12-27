package com.credibanco.sdk.main

import android.content.Context
import android.content.Intent
import com.credibanco.sdk.util.Constants.BLUETOOTH
import com.credibanco.sdk.util.Constants.CAMERA
import com.credibanco.sdk.util.Constants.GRAY_LEVEL
import com.credibanco.sdk.util.Constants.HASH_CODE
import com.credibanco.sdk.util.Constants.LETTER_SPACING
import com.credibanco.sdk.util.Constants.NFC
import com.credibanco.sdk.util.Constants.PACKAGE_NAME
import com.credibanco.sdk.util.Constants.PRINT
import com.credibanco.sdk.util.Constants.SCANTIP
import com.credibanco.sdk.util.Constants.SHOWBACK
import com.credibanco.sdk.util.Constants.SHOWBAR
import com.credibanco.sdk.util.Constants.SHOWMENU
import com.credibanco.sdk.util.Constants.SHOWSWITCH
import com.credibanco.sdk.util.Constants.SHOWTITLE
import com.credibanco.sdk.util.Constants.STATE_BLUETOOTH
import com.credibanco.sdk.util.Constants.TIPSIZE
import com.credibanco.sdk.util.Constants.TITLE
import com.credibanco.sdk.util.Constants.TITLESIZE
import com.credibanco.sdk.util.Constants.TYPEFACE
import com.credibanco.sdk.util.Constants.TYPE_INTEGRATION
import com.credibanco.sdk.util.Constants.UID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntegrationPeripheralsSDKNexgoImpl @Inject constructor() : IntegrationPeripheralsNexgoSDK {

    override fun startNfc(
        context: Context,
        uid: Boolean,
        hashCode: String,
        resultHardwareSDK: ResultHardwareSDK
    ) {
        val shareIntent = Intent(context, MainActivityNexgoSDK::class.java)
        val packageName = context.packageName
        ResultHardwareSDK.getSmartPosInstancePeripherals(resultHardwareSDK)

        shareIntent.putExtra(UID, uid)
        shareIntent.putExtra(HASH_CODE, hashCode)
        shareIntent.putExtra(PACKAGE_NAME, packageName)
        shareIntent.putExtra(TYPE_INTEGRATION, NFC)

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
        val shareIntent = Intent(context, MainActivityNexgoSDK::class.java)
        val packageName = context.packageName
        ResultHardwareSDK.getSmartPosInstancePeripherals(resultHardwareSDK)

        shareIntent.putExtra(TYPEFACE, typeFace)
        shareIntent.putExtra(LETTER_SPACING, letterSpacing)
        shareIntent.putExtra(GRAY_LEVEL, grayLevel)
        shareIntent.putExtra(PACKAGE_NAME, packageName)
        shareIntent.putExtra(HASH_CODE, hashCode)
        shareIntent.putExtra(Intent.EXTRA_STREAM, valuesToSend)
        shareIntent.putExtra(TYPE_INTEGRATION, PRINT)

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
        val shareIntent = Intent(context, MainActivityNexgoSDK::class.java)
        val packageName = context.packageName
        ResultHardwareSDK.getSmartPosInstancePeripherals(resultHardwareSDK)

        shareIntent.putExtra(SHOWBAR, showBar)
        shareIntent.putExtra(SHOWBACK, showBack)
        shareIntent.putExtra(SHOWTITLE, showTitle)
        shareIntent.putExtra(SHOWSWITCH, showSwitch)
        shareIntent.putExtra(SHOWMENU, showMenu)
        shareIntent.putExtra(TITLE, title)
        shareIntent.putExtra(TITLESIZE, titleSize)
        shareIntent.putExtra(TIPSIZE, tipSize)
        shareIntent.putExtra(SCANTIP, scanTip)
        shareIntent.putExtra(HASH_CODE, hashCode)
        shareIntent.putExtra(PACKAGE_NAME, packageName)
        shareIntent.putExtra(TYPE_INTEGRATION, CAMERA)

        context.startActivity(shareIntent)
    }

    override fun startBluetooth(
        context: Context,
        stateBluetooth: Boolean,
        hashCode: String,
        resultHardwareSDK: ResultHardwareSDK
    ) {
        val shareIntent = Intent(context, MainActivityNexgoSDK::class.java)
        val packageName = context.packageName
        ResultHardwareSDK.getSmartPosInstancePeripherals(resultHardwareSDK)

        shareIntent.putExtra(STATE_BLUETOOTH, stateBluetooth)
        shareIntent.putExtra(PACKAGE_NAME, packageName)
        shareIntent.putExtra(HASH_CODE, hashCode)
        shareIntent.putExtra(TYPE_INTEGRATION, BLUETOOTH)

        context.startActivity(shareIntent)
    }
}
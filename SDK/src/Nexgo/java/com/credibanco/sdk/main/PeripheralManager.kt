package com.credibanco.sdk.main

import android.content.Context
import com.credibanco.sdk.peripherals.BluetoothNexgo
import com.credibanco.sdk.peripherals.CameraNexgo
import com.credibanco.sdk.peripherals.PrintNexgo
import com.credibanco.sdk.peripherals.nfc.NFCNexgo

object PeripheralManager {

    fun startNfc(context: Context, isUid: Boolean, hashCode: String, result: ResultHardwareSDK) {
        val nfcNexgo = NFCNexgo(context, result)
        nfcNexgo.startNfc(isUid, hashCode)
    }

    fun startPrint(context: Context, hashCode: String, valuesToSend: ArrayList<String>, result: ResultHardwareSDK) {
        val printNexgo = PrintNexgo(context, result)
        printNexgo.startPrint(hashCode, valuesToSend)
    }

    fun startCamera(context: Context, hashCode: String, result: ResultHardwareSDK) {
        val cameraNexgo = CameraNexgo(context, result)
        cameraNexgo.startCamera(hashCode)
    }

    fun startBluetooth(context: Context, isBluetooth: Boolean, hashCode: String, result: ResultHardwareSDK) {
        val bluetoothNexgo = BluetoothNexgo(context, result)
        bluetoothNexgo.startBluetooth(isBluetooth, hashCode)
    }
}
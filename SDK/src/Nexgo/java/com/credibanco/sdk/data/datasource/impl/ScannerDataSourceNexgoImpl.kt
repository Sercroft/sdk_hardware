package com.credibanco.sdk.data.datasource.impl

import android.os.Bundle
import com.credibanco.sdk.datasource.ScannerDataSourceGeneral
import com.credibanco.sdk.domain.model.ScannerCallbackObject
import com.credibanco.sdk.util.NexgoPrintingConstants
import com.nexgo.oaf.apiv3.DeviceEngine
import com.nexgo.oaf.apiv3.SdkResult
import com.nexgo.oaf.apiv3.device.scanner.OnScannerListener
import com.nexgo.oaf.apiv3.device.scanner.ScannerCfgEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScannerDataSourceNexgoImpl @Inject constructor(
    deviceEngine: DeviceEngine
) : ScannerDataSourceGeneral,
    OnScannerListener {

    private var onScannerListener: OnScannerListener? = this
    val scanner = deviceEngine.scanner
    var toReturn = "NODATA"
    private lateinit var scannerCallbackObject: (callbackObjectType: ScannerCallbackObject) -> Unit
    override suspend operator fun invoke(bundle: Bundle?, scannerCallback: (callbackObjectType: ScannerCallbackObject) -> Unit ){

        val cfgEntity = ScannerCfgEntity()
        cfgEntity.isAutoFocus = true
        cfgEntity.isUsedFrontCcd = false
        if(bundle!=null) {
            cfgEntity.customBundle = bundle
        }

        withContext(Dispatchers.Default) {
            scannerCallbackObject = scannerCallback
            scanner.initScanner(cfgEntity,onScannerListener)
        }

    }

    override fun onInitResult(retCode: Int) {
        if (retCode == SdkResult.Success) {
            scanner?.startScan(60, onScannerListener)
        }
    }

    override fun onScannerResult(retCode: Int, data: String?) {
        when (retCode) {
            SdkResult.Success -> toReturn = data.toString()
            SdkResult.TimeOut -> toReturn = NexgoPrintingConstants.TIME_OUT
            SdkResult.Scanner_Customer_Exit -> toReturn = NexgoPrintingConstants.CUSTOMER_EXIT
            else -> toReturn = NexgoPrintingConstants.ERROR_SCAN
        }
        scannerCallbackObject?.invoke(ScannerCallbackObject.ScannerResultCallback(toReturn))
    }
}
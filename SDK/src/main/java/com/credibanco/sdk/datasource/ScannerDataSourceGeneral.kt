package com.credibanco.sdk.datasource

import android.os.Bundle
import com.credibanco.sdk.domain.model.ScannerCallbackObject

interface ScannerDataSourceGeneral {
    suspend operator fun invoke(bundle: Bundle?, scannerCallback: (callbackObjectType: ScannerCallbackObject) -> Unit)
}
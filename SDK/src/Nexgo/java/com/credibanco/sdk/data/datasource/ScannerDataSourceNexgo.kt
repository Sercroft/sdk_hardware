package com.credibanco.sdk.data.datasource.impl

import android.os.Bundle
import com.credibanco.sdk.domain.model.ScannerCallbackObject

interface ScannerDataSourceNexgo {
    suspend operator fun invoke(bundle: Bundle?, scannerCallback: (callbackObjectType: ScannerCallbackObject) -> Unit)
}
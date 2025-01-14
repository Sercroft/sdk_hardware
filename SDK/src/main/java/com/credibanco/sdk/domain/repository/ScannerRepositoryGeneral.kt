package com.credibanco.sdk.domain.repository

import android.os.Bundle
import com.credibanco.sdk.domain.model.ScannerCallbackObject

interface ScannerRepositoryGeneral {
    suspend operator fun invoke(bundle: Bundle?, scannerCallback: (callbackObjectType: ScannerCallbackObject) -> Unit)
}
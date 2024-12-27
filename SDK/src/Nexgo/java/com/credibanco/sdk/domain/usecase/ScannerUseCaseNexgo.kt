package com.credibanco.sdk.domain.usecase

import android.os.Bundle
import com.credibanco.sdk.domain.model.ScannerCallbackObject

interface ScannerUseCaseNexgo {
    suspend operator fun invoke(bundle: Bundle?, scannerCallback: (callbackObjectType: ScannerCallbackObject) -> Unit)
}
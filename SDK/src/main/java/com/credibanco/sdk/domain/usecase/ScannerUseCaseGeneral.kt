package com.credibanco.sdk.domain.usecase

import android.os.Bundle
import com.credibanco.sdk.domain.model.ScannerCallbackObject
import com.credibanco.sdk.domain.repository.ScannerRepositoryGeneral
import javax.inject.Inject

interface ScannerUseCaseGeneral {
    suspend operator fun invoke(bundle: Bundle?, scannerCallback: (callbackObjectType: ScannerCallbackObject) -> Unit)
}
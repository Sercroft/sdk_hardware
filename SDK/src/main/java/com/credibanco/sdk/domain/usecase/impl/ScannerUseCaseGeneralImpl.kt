package com.credibanco.sdk.domain.usecase.impl

import android.os.Bundle
import com.credibanco.sdk.domain.model.ScannerCallbackObject
import com.credibanco.sdk.domain.repository.ScannerRepositoryGeneral
import com.credibanco.sdk.domain.usecase.ScannerUseCaseGeneral
import javax.inject.Inject

class ScannerUseCaseGeneralImpl @Inject constructor(
    private val scannerRepository: ScannerRepositoryGeneral
) : ScannerUseCaseGeneral {
    override suspend fun invoke(bundle: Bundle?, scannerCallback: (callbackObjectType: ScannerCallbackObject) -> Unit) {
        scannerRepository.invoke(bundle, scannerCallback)
    }
}
package com.credibanco.sdk.domain.usecase.impl

import android.os.Bundle
import com.credibanco.sdk.domain.model.ScannerCallbackObject
import com.credibanco.sdk.domain.repository.ScannerRepositoryNexgo
import com.credibanco.sdk.domain.usecase.ScannerUseCaseNexgo
import javax.inject.Inject

class ScannerUseCaseNexgoImpl @Inject constructor(
    private val scannerRepository: ScannerRepositoryNexgo
) : ScannerUseCaseNexgo {
    override suspend fun invoke(bundle: Bundle?, scannerCallback: (callbackObjectType: ScannerCallbackObject) -> Unit) {
        scannerRepository.invoke(bundle, scannerCallback)
    }
}
package com.credibanco.sdk.main.data.repository.impl

import android.os.Bundle
import com.credibanco.sdk.datasource.ScannerDataSourceGeneral
import com.credibanco.sdk.domain.model.ScannerCallbackObject
import com.credibanco.sdk.domain.repository.ScannerRepositoryGeneral
import javax.inject.Inject

class ScannerRepositoryIngenicoImpl @Inject constructor(
    private val scannerDataSource: ScannerDataSourceGeneral
) : ScannerRepositoryGeneral {
    override suspend fun invoke(bundle: Bundle?, scannerCallback: (callbackObjectType: ScannerCallbackObject) -> Unit) {
        return scannerDataSource.invoke(bundle, scannerCallback)
    }
}
package com.credibanco.sdk.data.repository.impl

import android.os.Bundle
import com.credibanco.sdk.data.datasource.impl.ScannerDataSourceNexgo
import com.credibanco.sdk.domain.model.ScannerCallbackObject
import com.credibanco.sdk.domain.repository.ScannerRepositoryNexgo
import javax.inject.Inject

class ScannerRepositoryNexgoImpl @Inject constructor(
    private val scannerDataSource: ScannerDataSourceNexgo
) : ScannerRepositoryNexgo {
    override suspend fun invoke(bundle: Bundle?, scannerCallback: (callbackObjectType: ScannerCallbackObject) -> Unit) {
        return scannerDataSource.invoke(bundle, scannerCallback)
    }
}
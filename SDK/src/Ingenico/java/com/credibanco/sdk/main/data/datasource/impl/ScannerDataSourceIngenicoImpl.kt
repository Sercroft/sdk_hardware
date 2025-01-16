package com.credibanco.sdk.main.data.datasource.impl

import android.os.Bundle
import com.credibanco.sdk.datasource.ScannerDataSourceGeneral
import com.credibanco.sdk.domain.model.ScannerCallbackObject
import javax.inject.Inject

class ScannerDataSourceIngenicoImpl @Inject constructor() : ScannerDataSourceGeneral {
    override suspend operator fun invoke(bundle: Bundle?, scannerCallback: (callbackObjectType: ScannerCallbackObject) -> Unit ){}
}
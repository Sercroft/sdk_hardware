package com.credibanco.sdk.domain.model

sealed class ScannerCallbackObject {
    data class ScannerResultCallback(
        val scannerResult: String
    ) : ScannerCallbackObject()
}
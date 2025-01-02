package com.credibanco.sdk.main.util.printer

import com.usdk.apiservice.aidl.printer.ECLevel

data class QRCodeConfig(
    val content: String,
    val size: Int = 240,
    val errorCorrectionLevel: Int = ECLevel.ECLEVEL_H
)

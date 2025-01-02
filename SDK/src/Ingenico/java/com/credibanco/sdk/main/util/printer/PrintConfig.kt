package com.credibanco.sdk.main.util.printer

import com.usdk.apiservice.aidl.printer.ASCScale
import com.usdk.apiservice.aidl.printer.ASCSize
import com.usdk.apiservice.aidl.printer.AlignMode
import com.usdk.apiservice.aidl.printer.HZScale
import com.usdk.apiservice.aidl.printer.HZSize

data class PrintConfig(
    val linesToPrint: ArrayList<String>,
    val alignment: Int = AlignMode.LEFT,
    val ascScale: Int = ASCScale.SC1x1,
    val ascSize: Int = ASCSize.DOT24x12,
    val hzScale: Int = HZScale.SC1x1,
    val hzSize: Int = HZSize.DOT24x24,
    val grayLevel: Int? = null,
    val qrCode: QRCodeConfig? = null,
    val barCode: BarCodeConfig? = null,
    val bmpPath: String? = null
)

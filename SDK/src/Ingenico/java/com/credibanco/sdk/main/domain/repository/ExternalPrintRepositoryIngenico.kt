package com.credibanco.sdk.main.domain.repository

import android.content.res.Resources
import com.credibanco.sdk.main.util.printer.BarCodeConfig
import com.credibanco.sdk.main.util.printer.PrintConfig
import com.credibanco.sdk.main.util.printer.QRCodeConfig
import com.usdk.apiservice.aidl.printer.ASCScale
import com.usdk.apiservice.aidl.printer.ASCSize
import com.usdk.apiservice.aidl.printer.AlignMode
import com.usdk.apiservice.aidl.printer.HZScale
import com.usdk.apiservice.aidl.printer.HZSize

interface ExternalPrintRepositoryIngenico {
    suspend operator fun invoke(
        linesToPrint: ArrayList<String>,
        packageName: String,
        resources: Resources,
        typeface: String? = null,
        letterSpacing: Int? = null,
        grayLevel: String? = null
    ): Int
}
package com.credibanco.sdk.main.domain.usecase.impl

import android.content.res.Resources
import com.credibanco.sdk.domain.ExternalPrintUseCaseGeneral
import com.credibanco.sdk.main.domain.repository.ExternalPrintRepositoryIngenico
import com.credibanco.sdk.main.domain.usecase.ExternalPrintUseCaseIngenico
import com.credibanco.sdk.main.util.printer.BarCodeConfig
import com.credibanco.sdk.main.util.printer.PrintConfig
import com.credibanco.sdk.main.util.printer.QRCodeConfig
import com.usdk.apiservice.aidl.printer.ASCScale
import com.usdk.apiservice.aidl.printer.ASCSize
import com.usdk.apiservice.aidl.printer.AlignMode
import com.usdk.apiservice.aidl.printer.HZScale
import com.usdk.apiservice.aidl.printer.HZSize
import javax.inject.Inject

class ExternalPrintUseCaseIngenicoImpl @Inject constructor(
    private val externalPrintRepository: ExternalPrintRepositoryIngenico
): ExternalPrintUseCaseGeneral {
    override suspend operator fun invoke(
        linesToPrint: ArrayList<String>,
        packageName: String,
        resources: Resources,
        typeface: String?,
        letterSpacing: Int?,
        grayLevel: String?
    ) = externalPrintRepository(
        linesToPrint,
        packageName,
        resources,
        typeface,
        letterSpacing,
        grayLevel
    )
}
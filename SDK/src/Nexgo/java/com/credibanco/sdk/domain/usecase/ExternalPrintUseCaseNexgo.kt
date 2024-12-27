package com.credibanco.sdk.domain.usecase

import android.content.res.Resources

interface ExternalPrintUseCaseNexgo {
    suspend operator fun invoke(
        linesToPrint: ArrayList<String>,
        packageName: String,
        resources: Resources,
        typeface: String?,
        letterSpacing: Int?,
        grayLevel: String?
    ): Int
}
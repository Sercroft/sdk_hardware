package com.credibanco.sdk.domain

import android.content.res.Resources

interface ExternalPrintUseCaseGeneral {
    suspend operator fun invoke(
        linesToPrint: ArrayList<String>,
        packageName: String,
        resources: Resources,
        typeface: String? = null,
        letterSpacing: Int? = null,
        grayLevel: String? = null
    ): Int
}
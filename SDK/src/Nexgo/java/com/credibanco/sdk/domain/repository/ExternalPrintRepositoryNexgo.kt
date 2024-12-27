package com.credibanco.sdk.domain.repository

import android.content.res.Resources

interface ExternalPrintRepositoryNexgo {
    suspend operator fun invoke(
        linesToPrint: ArrayList<String>,
        packageName: String,
        resources: Resources,
        typeface: String?,
        letterSpacing: Int?,
        grayLevel: String?
    ): Int
}
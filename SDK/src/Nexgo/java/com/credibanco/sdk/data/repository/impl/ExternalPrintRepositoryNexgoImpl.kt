package com.credibanco.sdk.data.repository.impl

import android.content.res.Resources
import com.credibanco.sdk.datasource.PrintDataSource
import com.credibanco.sdk.domain.repository.ExternalPrintRepositoryNexgo
import javax.inject.Inject

class ExternalPrintRepositoryNexgoImpl @Inject constructor(
    private val externalPrintDataSource: PrintDataSource
) : ExternalPrintRepositoryNexgo {
    override suspend operator fun invoke(
        linesToPrint: ArrayList<String>,
        packageName: String,
        resources: Resources,
        typeface: String?,
        letterSpacing: Int?,
        grayLevel: String?
    ) = externalPrintDataSource(
        linesToPrint,
        packageName,
        resources,
        typeface,
        letterSpacing,
        grayLevel
    )
}
package com.credibanco.sdk.domain.usecase.impl

import android.content.res.Resources
import com.credibanco.sdk.domain.repository.ExternalPrintRepositoryNexgo
import com.credibanco.sdk.domain.usecase.ExternalPrintUseCaseNexgo
import javax.inject.Inject

class ExternalPrintUseCaseNexgoImpl @Inject constructor(
    private val externalPrintRepository: ExternalPrintRepositoryNexgo
) : ExternalPrintUseCaseNexgo {
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
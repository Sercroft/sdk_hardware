package com.credibanco.sdk.main.data.repository.impl

import android.content.res.Resources
import com.credibanco.sdk.datasource.ExternalPrintDataSourceGeneral
import com.credibanco.sdk.domain.KernelAvailableStateCallBackObjectSDK
import com.credibanco.sdk.main.domain.repository.ExternalPrintRepositoryIngenico
import javax.inject.Inject

class ExternalPrintRepositoryIngenicoImpl @Inject constructor(
    private val externalPrintDataSource: ExternalPrintDataSourceGeneral
): ExternalPrintRepositoryIngenico {
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
        grayLevel,
    )


    override fun isKernelAvailableState(kernelAvailableCallback: (callbackObjectType: KernelAvailableStateCallBackObjectSDK) -> Unit) {
        return externalPrintDataSource.isKernelAvailableState(kernelAvailableCallback)
    }
    override suspend fun isKernelRun(): Boolean {
        return externalPrintDataSource.isKernelRun()
    }

}
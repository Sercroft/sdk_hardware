package com.credibanco.sdk.datasource

import android.content.res.Resources
import com.credibanco.sdk.domain.KernelAvailableStateCallBackObjectSDK

interface ExternalPrintDataSourceGeneral {

    suspend fun isKernelRun(): Boolean

    suspend operator fun invoke(
        linesToPrint: ArrayList<String>,
        packageName: String,
        resources: Resources,
        typeface: String?,
        letterSpacing: Int?,
        grayLevel: String?
    ): Int

    fun isKernelAvailableState(kernelAvailableCallback: (callbackObjectType: KernelAvailableStateCallBackObjectSDK) -> Unit)
}
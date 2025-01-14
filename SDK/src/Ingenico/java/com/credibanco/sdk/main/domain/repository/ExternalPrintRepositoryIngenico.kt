package com.credibanco.sdk.main.domain.repository

import android.content.res.Resources
import com.credibanco.sdk.domain.KernelAvailableStateCallBackObjectSDK

interface ExternalPrintRepositoryIngenico {

    suspend fun isKernelRun(): Boolean
    suspend operator fun invoke(
        linesToPrint: ArrayList<String>,
        packageName: String,
        resources: Resources,
        typeface: String? = null,
        letterSpacing: Int? = null,
        grayLevel: String? = null
    ): Int

    fun isKernelAvailableState(kernelAvailableCallback: (callbackObjectType: KernelAvailableStateCallBackObjectSDK) -> Unit)

}
package com.credibanco.sdk.datasource

import com.credibanco.sdk.domain.EmvCallbackObjectSDK
import com.credibanco.sdk.domain.KernelAvailableStateCallBackObjectSDK
import com.credibanco.sdk.domain.ReadCardResponse
import com.credibanco.sdk.domain.dto.CertEmv
import com.credibanco.sdk.domain.dto.IdEmv

interface EmvDataSource {

    suspend fun isKernelRun(): Boolean

    suspend operator fun invoke(
        idEmvList: List<IdEmv>,
        totalAmount: Double,
        systemTrace: String,
        codTerm: String,
        codEsta: String,
        changeTagToDCC: Boolean = false,
        transactionCurrencyCode: String? = "",
        isDcc: Boolean = false,
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    )

    suspend fun startEmv()

    suspend fun startEmv(
        idEmvList: List<IdEmv>,
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    )

    suspend fun onPinInputSuccess(retCode: Int,
                                  emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit)

    suspend fun onPinInputFail(
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    )

    suspend fun onSetConfirmCardNoResponse(
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    )

    fun onOnlineResponse(
        field55Response: String?, responseCode: String?, authCode: String?,
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit,
    )

    fun cancelEmvProcess(
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    )

    fun stopSearchCard()

    fun onSetSelAppResponse(selResult: Int?,
                            emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit)

    fun isChipCardInserted(): Boolean

    suspend fun detectCard(
        fallback: Boolean?, idEmvList: List<IdEmv>?, certEmvList: List<CertEmv>?
    ): ReadCardResponse

    suspend fun getRSAKey(): String

    fun getDeviceSerial(): String

    fun getEntryModeType():String

    fun isKernelAvailableState(kernelAvailableCallback: (callbackObjectType: KernelAvailableStateCallBackObjectSDK) -> Unit)

    fun getVersionSDK(): String
}
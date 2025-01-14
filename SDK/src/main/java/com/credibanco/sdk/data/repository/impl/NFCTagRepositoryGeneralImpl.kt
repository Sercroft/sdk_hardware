package com.credibanco.sdk.data.repository.impl

import com.credibanco.sdk.datasource.NFCTagDataSourceGeneral
import com.credibanco.sdk.domain.EmvCallbackObjectSDK
import com.credibanco.sdk.domain.KernelAvailableStateCallBackObjectSDK
import com.credibanco.sdk.domain.ReadCardResponse
import com.credibanco.sdk.domain.dto.CertEmv
import com.credibanco.sdk.domain.dto.IdEmv
import com.credibanco.sdk.domain.repository.NFCTagRepositoryGeneral
import javax.inject.Inject

class NFCTagRepositoryGeneralImpl @Inject constructor(
    private val nfcTagDataSource: NFCTagDataSourceGeneral
) : NFCTagRepositoryGeneral {

    override suspend fun invokeNexgo(isRequiredUID: Boolean?): String {
        return nfcTagDataSource.invokeNexgo(isRequiredUID)
    }

    override suspend fun invokeIngenico(
        idEmvList: List<IdEmv>,
        totalAmount: Double,
        systemTrace: String,
        codTerm: String,
        codEsta: String,
        changeTagToDCC: Boolean,
        transactionCurrencyCode: String?,
        isDcc: Boolean,
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) {
        nfcTagDataSource.invokeIngenico(
            idEmvList = idEmvList,
            totalAmount = totalAmount,
            systemTrace = systemTrace,
            codTerm = codTerm,
            codEsta = codEsta,
            changeTagToDCC = changeTagToDCC,
            transactionCurrencyCode = transactionCurrencyCode ,
            isDcc = isDcc,
            emvCallback = emvCallback,
        )
    }


    override suspend fun startEmv(
        idEmvList: List<IdEmv>,
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) = nfcTagDataSource.startEmv( idEmvList, emvCallback)

    override suspend fun onPinInputSuccess(
        retCode: Int, emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) = nfcTagDataSource.onPinInputSuccess(
        retCode,
        emvCallback
    )

    override suspend fun onPinInputFail(
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) = nfcTagDataSource.onPinInputFail(
        emvCallback
    )

    override suspend fun onSetConfirmCardNoResponse(
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) = nfcTagDataSource.onSetConfirmCardNoResponse(
        emvCallback
    )

    override fun onOnlineResponse(
        field55Response: String?,
        responseCode: String?,
        authCode: String?,
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) = nfcTagDataSource.onOnlineResponse(
        field55Response,
        responseCode,
        authCode,
        emvCallback
    )

    override fun cancelEmvProcess(
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) = nfcTagDataSource.cancelEmvProcess(
        emvCallback
    )

    override fun stopSearchCard() = nfcTagDataSource.stopSearchCard()

    override fun onSetSelAppResponse(selApp: Int,
                                     emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit)
            = nfcTagDataSource.onSetSelAppResponse(
        selApp,
        emvCallback)

    override fun isChipCardInserted(): Boolean {
        return nfcTagDataSource.isChipCardInserted()
    }

    override suspend fun detectCard(
        fallback: Boolean?,
        idEmvList: List<IdEmv>?,
        certEmvList: List<CertEmv>?,
    ): ReadCardResponse {
        return nfcTagDataSource.detectCard(fallback, idEmvList, certEmvList)
    }

    override suspend fun isKernelRun(): Boolean {
        return nfcTagDataSource.isKernelRun()
    }

    override fun getDeviceSerial(): String {
        return nfcTagDataSource.getDeviceSerial()
    }

    override fun getEntryModeType(): String {
        return nfcTagDataSource.getEntryModeType()
    }

    override suspend fun getRSAKey(): String {
        return nfcTagDataSource.getRSAKey()
    }

    override fun isKernelAvailableState(kernelAvailableCallback: (callbackObjectType: KernelAvailableStateCallBackObjectSDK) -> Unit) {
        return nfcTagDataSource.isKernelAvailableState(kernelAvailableCallback)
    }

    override fun getVersionSDK(): String {
        return nfcTagDataSource.getVersionSDK()
    }
}
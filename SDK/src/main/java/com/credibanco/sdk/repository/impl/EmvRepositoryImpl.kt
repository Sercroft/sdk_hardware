package com.credibanco.sdk.repository.impl

import com.credibanco.sdk.datasource.EmvDataSource
import com.credibanco.sdk.domain.EmvCallbackObjectSDK
import com.credibanco.sdk.domain.KernelAvailableStateCallBackObjectSDK
import com.credibanco.sdk.domain.ReadCardResponse
import com.credibanco.sdk.domain.dto.CertEmv
import com.credibanco.sdk.domain.dto.IdEmv
import com.credibanco.sdk.repository.EmvRepository
import javax.inject.Inject

class EmvRepositoryImpl @Inject constructor(private val emvDataSource: EmvDataSource) :
    EmvRepository {

    override suspend operator fun invoke(
        idEmvList: List<IdEmv>,
        totalAmount: Double,
        systemTrace: String,
        codTerm: String,
        codEsta: String,
        changeTagToDCC: Boolean,
        transactionCurrencyCode: String?,
        isDcc: Boolean,
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    )
    {
        emvDataSource(
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
    ) = emvDataSource.startEmv( idEmvList, emvCallback)

    override suspend fun onPinInputSuccess(
        retCode: Int, emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) = emvDataSource.onPinInputSuccess(
        retCode,
        emvCallback
    )

    override suspend fun onPinInputFail(
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) = emvDataSource.onPinInputFail(
        emvCallback
    )

    override suspend fun onSetConfirmCardNoResponse(
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) = emvDataSource.onSetConfirmCardNoResponse(
        emvCallback
    )

    override fun onOnlineResponse(
        field55Response: String?,
        responseCode: String?,
        authCode: String?,
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) = emvDataSource.onOnlineResponse(
        field55Response,
        responseCode,
        authCode,
        emvCallback
    )

    override fun cancelEmvProcess(
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) = emvDataSource.cancelEmvProcess(
        emvCallback
    )

    override fun stopSearchCard() = emvDataSource.stopSearchCard()

    override fun onSetSelAppResponse(selApp: Int,
                                     emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit)
            = emvDataSource.onSetSelAppResponse(
        selApp,
        emvCallback)

    override fun isChipCardInserted(): Boolean {
        return emvDataSource.isChipCardInserted()
    }

    override suspend fun detectCard(
        fallback: Boolean?,
        idEmvList: List<IdEmv>?,
        certEmvList: List<CertEmv>?,
    ): ReadCardResponse {
        return emvDataSource.detectCard(fallback, idEmvList, certEmvList)
    }

    override suspend fun isKernelRun(): Boolean {
        return emvDataSource.isKernelRun()
    }

    override fun getDeviceSerial(): String {
        return emvDataSource.getDeviceSerial()
    }

    override fun getEntryModeType(): String {
        return emvDataSource.getEntryModeType()
    }

    override suspend fun getRSAKey(): String {
        return emvDataSource.getRSAKey()
    }

    override fun isKernelAvailableState(kernelAvailableCallback: (callbackObjectType: KernelAvailableStateCallBackObjectSDK) -> Unit) {
        return emvDataSource.isKernelAvailableState(kernelAvailableCallback)
    }

    override fun getVersionSDK(): String {
        return emvDataSource.getVersionSDK()
    }
}
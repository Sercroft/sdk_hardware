package com.credibanco.sdk.domain.usecase.impl

import com.credibanco.sdk.domain.EmvCallbackObjectSDK
import com.credibanco.sdk.domain.KernelAvailableStateCallBackObjectSDK
import com.credibanco.sdk.domain.ReadCardResponse
import com.credibanco.sdk.domain.dto.CertEmv
import com.credibanco.sdk.domain.dto.IdEmv
import com.credibanco.sdk.domain.repository.NFCTagRepositoryGeneral
import com.credibanco.sdk.domain.usecase.NFCTagUseCaseGeneral
import javax.inject.Inject

class NFCTagUseCaseGeneralImpl @Inject constructor(
    private val nfcTagRepository: NFCTagRepositoryGeneral
) : NFCTagUseCaseGeneral {

    override suspend fun invokeNexgo(isRequiredUID: Boolean?): String {
        return nfcTagRepository.invokeNexgo(isRequiredUID)
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
        if (isKernelRun()) {
            nfcTagRepository.invokeIngenico(
                idEmvList = idEmvList,
                totalAmount = totalAmount,
                systemTrace = systemTrace,
                codTerm = codTerm,
                codEsta = codEsta,
                changeTagToDCC = changeTagToDCC,
                transactionCurrencyCode = transactionCurrencyCode,
                isDcc = isDcc,
                emvCallback = emvCallback
            )
        }
    }

    override suspend fun startEmv(
        idEmvList: List<IdEmv>,
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit,
    ) {
        if (isKernelRun()) {
            nfcTagRepository.startEmv(idEmvList, emvCallback)
        }
    }

    override suspend fun onPinInputSuccess(
        retCode: Int,
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) {
        if (isKernelRun()) {
            nfcTagRepository.onPinInputSuccess(
                retCode,
                emvCallback
            )
        }
    }

    override suspend fun onPinInputFail(
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) {
        if (isKernelRun()) {
            nfcTagRepository.onPinInputFail(emvCallback)
        }
    }

    override suspend fun onSetConfirmCardNoResponse(
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) {
        if (isKernelRun()) {
            nfcTagRepository.onSetConfirmCardNoResponse(emvCallback)
        }
    }

    override fun onOnlineResponse(
        field55Response: String?,
        responseCode: String?,
        authCode: String?, emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) = nfcTagRepository.onOnlineResponse(
        field55Response,
        responseCode,
        authCode,
        emvCallback
    )

    override fun cancelEmvProcess(
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) = nfcTagRepository.cancelEmvProcess(
        emvCallback
    )

    override fun stopSearchCard() = nfcTagRepository.stopSearchCard()

    override fun onSetSelAppResponse(
        selApp: Int,
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) = nfcTagRepository.onSetSelAppResponse(
        selApp,
        emvCallback
    )

    override fun isChipCardInserted(): Boolean {
        return nfcTagRepository.isChipCardInserted()
    }

    override suspend fun detectCard(
        fallback: Boolean?,
        idEmvList: List<IdEmv>?,
        certEmvList: List<CertEmv>?
    ): ReadCardResponse {
        return if (isKernelRun()) {
            nfcTagRepository.detectCard(fallback, idEmvList, certEmvList)
        } else {
            ReadCardResponse.ReadFailed
        }
    }

    override suspend fun isKernelRun(): Boolean {
        return nfcTagRepository.isKernelRun()
    }

    override suspend fun getRSAKey(): String {
        return if (isKernelRun()) {
            nfcTagRepository.getRSAKey()
        } else {
            "Kernel Instance Failed"
        }
    }

    override fun getDeviceSerial(): String {
        return nfcTagRepository.getDeviceSerial()
    }

    override fun getEntryModeType(): String {
        return nfcTagRepository.getEntryModeType()
    }

    override fun isKernelAvailableState(kernelAvailableCallback: (callbackObjectType: KernelAvailableStateCallBackObjectSDK) -> Unit) {
        return nfcTagRepository.isKernelAvailableState(kernelAvailableCallback)
    }

    override fun getVersionSDK(): String {
        return nfcTagRepository.getVersionSDK()
    }
}
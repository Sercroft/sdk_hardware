package com.credibanco.sdk.data.datasource.impl

import android.util.Log
import com.credibanco.sdk.datasource.NFCTagDataSourceGeneral
import com.credibanco.sdk.domain.EmvCallbackObjectSDK
import com.credibanco.sdk.domain.KernelAvailableStateCallBackObjectSDK
import com.credibanco.sdk.domain.ReadCardResponse
import com.credibanco.sdk.domain.dto.CertEmv
import com.credibanco.sdk.domain.dto.IdEmv
import com.nexgo.common.ByteUtils
import com.nexgo.oaf.apiv3.DeviceEngine
import com.nexgo.oaf.apiv3.SdkResult
import com.nexgo.oaf.apiv3.card.mifare.AuthEntity
import com.nexgo.oaf.apiv3.card.mifare.BlockEntity
import com.nexgo.oaf.apiv3.card.mifare.M1CardHandler
import com.nexgo.oaf.apiv3.card.mifare.M1CardOperTypeEnum
import com.nexgo.oaf.apiv3.card.mifare.M1KeyTypeEnum
import com.nexgo.oaf.apiv3.card.ultralight.UltralightEV1CardHandler
import com.nexgo.oaf.apiv3.device.reader.CardSlotTypeEnum
import com.nexgo.oaf.apiv3.device.reader.RfCardTypeEnum
import javax.inject.Inject

class NFCTagDataSourceNexgoImpl @Inject constructor(
    private val deviceEngine: DeviceEngine
) : NFCTagDataSourceGeneral {

    private val TAG: String = "Read Data"
    private val SUCESSFULL: String = "Successfull: "
    override suspend fun invokeNexgo(isRequiredUID: Boolean?): String {
        var toReturn = "NODATA"

        val ultralightCCardHandler = deviceEngine.ultralightEV1CardHandler
        val m1CCardHandler = deviceEngine.m1CardHandler
        val cardReader = deviceEngine.cardReader
        cardReader.open(CardSlotTypeEnum.RF)
        val rfCardTypeEnum = cardReader.getRfCardType(CardSlotTypeEnum.RF)
        if (rfCardTypeEnum == RfCardTypeEnum.ULTRALIGHT) {
            toReturn = getUltralight(ultralightCCardHandler, m1CCardHandler, isRequiredUID)
        } else if (rfCardTypeEnum == RfCardTypeEnum.S50 || rfCardTypeEnum == RfCardTypeEnum.S70) {
            toReturn = getMifare(rfCardTypeEnum, m1CCardHandler, isRequiredUID)
        }
        cardReader.close(CardSlotTypeEnum.RF)
        return toReturn
    }

    override suspend fun isKernelRun(): Boolean {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override suspend fun startEmv() {
        TODO("Not yet implemented")
    }

    override suspend fun startEmv(
        idEmvList: List<IdEmv>,
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun onPinInputSuccess(
        retCode: Int,
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun onPinInputFail(emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun onSetConfirmCardNoResponse(emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun onOnlineResponse(
        field55Response: String?,
        responseCode: String?,
        authCode: String?,
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun cancelEmvProcess(emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun stopSearchCard() {
        TODO("Not yet implemented")
    }

    override fun onSetSelAppResponse(
        selResult: Int?,
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun isChipCardInserted(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun detectCard(
        fallback: Boolean?,
        idEmvList: List<IdEmv>?,
        certEmvList: List<CertEmv>?
    ): ReadCardResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getRSAKey(): String {
        TODO("Not yet implemented")
    }

    override fun getDeviceSerial(): String {
        TODO("Not yet implemented")
    }

    override fun getEntryModeType(): String {
        TODO("Not yet implemented")
    }

    override fun isKernelAvailableState(kernelAvailableCallback: (callbackObjectType: KernelAvailableStateCallBackObjectSDK) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getVersionSDK(): String {
        TODO("Not yet implemented")
    }

    private fun getUltralight(
        ultralightCCardHandler: UltralightEV1CardHandler,
        m1CardHandler: M1CardHandler,
        isRequiredUidUltraLight: Boolean?
    ): String {

        var toReturn: String = ""
        var readblock: Int = 0
        var sb: StringBuilder = StringBuilder()
        var readData: ByteArray?
        val uid: String = m1CardHandler.readUid()
        while (readblock < (16 * 4)) {
            readData = ultralightCCardHandler.read(readblock.toByte())
            sb.append(ByteUtils.byteArray2HexString(readData))
            readblock += 4
        }
        val finalData = sb.toString()

        if (isRequiredUidUltraLight == true) {
            toReturn = "$uid;$finalData"
        } else {
            toReturn = finalData
        }

        return toReturn
    }


    private fun getMifare(
        rfCardTypeEnum: RfCardTypeEnum,
        m1CCardHandler: M1CardHandler,
        isRequiredUidMiFare: Boolean?
    ): String {

        var toReturn: String = ""

        Log.d("Mifare Type", rfCardTypeEnum.toString())
        //step 3 , read UID
        var ret = -1
        var finalData = ""
        //step 3 , read UID
        val uid: String = m1CCardHandler.readUid()
        Log.d("UID", uid)
        //step 4, block authority

        //step 4, block authority
        val authEntity = AuthEntity()
        authEntity.setUid(uid)
        authEntity.setKeyType(M1KeyTypeEnum.KEYTYPE_B) //keytype A or B

        authEntity.setBlkNo(4)
        authEntity.setPwd(ByteUtils.hexString2ByteArray("FFFFFFFFFFFF"))
        ret = m1CCardHandler.authority(authEntity)
        if (ret == SdkResult.Success) {
            Log.d("Auth", "Successfull")
            var blockEntity5 = BlockEntity()
            blockEntity5.setOperType(M1CardOperTypeEnum.INCREMENT)
            blockEntity5.setBlkNo(4)
            ret = m1CCardHandler.readBlock(blockEntity5)
            if (ret != SdkResult.Success) {
                Log.d(TAG, "Unsuccessfull")
            } else {
                finalData = String(blockEntity5.blkData)
                Log.d(TAG, SUCESSFULL + finalData)
            }

            blockEntity5 = BlockEntity()
            blockEntity5.setOperType(M1CardOperTypeEnum.INCREMENT)
            blockEntity5.setBlkNo(5)
            ret = m1CCardHandler.readBlock(blockEntity5)
            if (ret != SdkResult.Success) {
                Log.d(TAG, "Unsuccessfull")
            } else {
                finalData = finalData.plus(String(blockEntity5.blkData))
                Log.d(TAG, SUCESSFULL + finalData)
            }

            blockEntity5 = BlockEntity()
            blockEntity5.setOperType(M1CardOperTypeEnum.INCREMENT)
            blockEntity5.setBlkNo(6)
            ret = m1CCardHandler.readBlock(blockEntity5)
            if (ret != SdkResult.Success) {
                Log.d(TAG, "Unsuccessfull")
            } else {
                finalData = finalData.plus(String(blockEntity5.blkData))
                Log.d(TAG, SUCESSFULL + finalData)
            }
        } else {
            Log.d("Auth", "Unsuccessfull")
        } //step 5 read block

        if (isRequiredUidMiFare == true) {
            toReturn = "$uid;$finalData"
        } else {
            toReturn = finalData

        }
        return toReturn
    }
}
package com.credibanco.sdk.data.datasource.impl

import android.util.Log
import com.credibanco.sdk.data.datasource.NFCTagDataSourceNexgo
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
) : NFCTagDataSourceNexgo {

    private val TAG: String = "Read Data"
    private val SUCESSFULL: String = "Successfull: "
    override suspend fun invoke(isRequiredUID: Boolean?): String {
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
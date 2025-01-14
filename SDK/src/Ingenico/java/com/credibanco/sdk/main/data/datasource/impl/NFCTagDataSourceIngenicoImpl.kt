package com.credibanco.sdk.main.data.datasource.impl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.RemoteException
import android.text.TextUtils
import com.credibanco.sdk.datasource.InitializationDataSource
import com.credibanco.sdk.datasource.NFCTagDataSourceGeneral
import com.credibanco.sdk.domain.EmvCallbackObjectSDK
import com.credibanco.sdk.domain.KernelAvailableStateCallBackObjectSDK
import com.credibanco.sdk.domain.ReadCardResponse
import com.credibanco.sdk.domain.dto.CertEmv
import com.credibanco.sdk.domain.dto.IdEmv
import com.credibanco.sdk.domain.type.CardSlotType
import com.credibanco.sdk.domain.type.EntryModeType
import com.credibanco.sdk.main.data.datasource.CardOption
import com.credibanco.sdk.main.data.datasource.EMVOption
import com.credibanco.sdk.main.data.datasource.TLV
import com.credibanco.sdk.main.data.datasource.general.MapperIngenico
import com.credibanco.sdk.main.util.PinUtil
import com.credibanco.sdk.util.BytesUtil
import com.credibanco.sdk.util.Constants
import com.credibanco.sdk.util.DataConversionUtil
import com.credibanco.sdk.util.DateTimeUtil
import com.credibanco.sdk.util.SerialCommUtil
import com.credibanco.sdk.util.TagsEmvUtil
import com.credibanco.sdk.util.TagsEmvUtils
import com.credibanco.sdk.util.TefUtil
import com.usdk.apiservice.aidl.DeviceServiceData
import com.usdk.apiservice.aidl.UDeviceService
import com.usdk.apiservice.aidl.beeper.UBeeper
import com.usdk.apiservice.aidl.constants.RFDeviceName
import com.usdk.apiservice.aidl.data.StringValue
import com.usdk.apiservice.aidl.device.UDeviceManager
import com.usdk.apiservice.aidl.emv.ACType
import com.usdk.apiservice.aidl.emv.ActionFlag
import com.usdk.apiservice.aidl.emv.CAPublicKey
import com.usdk.apiservice.aidl.emv.CVMFlag
import com.usdk.apiservice.aidl.emv.CVMMethod
import com.usdk.apiservice.aidl.emv.CandidateAID
import com.usdk.apiservice.aidl.emv.CardRecord
import com.usdk.apiservice.aidl.emv.EMVData
import com.usdk.apiservice.aidl.emv.EMVError
import com.usdk.apiservice.aidl.emv.EMVEventHandler
import com.usdk.apiservice.aidl.emv.EMVTag
import com.usdk.apiservice.aidl.emv.FinalData
import com.usdk.apiservice.aidl.emv.KernelINS
import com.usdk.apiservice.aidl.emv.OfflinePinVerifyResult
import com.usdk.apiservice.aidl.emv.SearchCardListener
import com.usdk.apiservice.aidl.emv.TlvResponse
import com.usdk.apiservice.aidl.emv.TransData
import com.usdk.apiservice.aidl.emv.UEMV
import com.usdk.apiservice.aidl.icreader.DriverID
import com.usdk.apiservice.aidl.icreader.UICCpuReader
import com.usdk.apiservice.aidl.led.Light
import com.usdk.apiservice.aidl.led.ULed
import com.usdk.apiservice.aidl.pinpad.DeviceName
import com.usdk.apiservice.aidl.pinpad.KAPId
import com.usdk.apiservice.aidl.pinpad.KeySystem
import com.usdk.apiservice.aidl.pinpad.OfflinePinVerify
import com.usdk.apiservice.aidl.pinpad.PinPublicKey
import com.usdk.apiservice.aidl.pinpad.PinVerifyResult
import com.usdk.apiservice.aidl.pinpad.UPinpad
import com.usdk.apiservice.aidl.serialport.SerialPortError
import com.usdk.apiservice.aidl.serialport.USerialPort
import kotlinx.coroutines.suspendCancellableCoroutine
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.schedule
import kotlin.math.roundToInt

class NFCTagDataSourceIngenicoImpl @Inject constructor(
    private val context: Context,
):
    NFCTagDataSourceGeneral,
    ServiceConnection,
    InitializationDataSource
{
    companion object {
        private const val RF_DEVICE_NAME = RFDeviceName.INNER
        private const val COLOMBIA_CURRENCY_CODE = "170"
        private const val AMOUNT_EMV_LENGTH = 12
        private const val ZERO_NUMBER_PADDING = '0'
        private const val BEEPER_TIME = 500
        private const val DECIMALS_AMOUNT_STRING = "00"
        private const val ONE_DECIMAL_AMOUNT_STRING = "0"
        private const val MAX_SERIAL_NUMBER_DIGITS = 12
        private const val LENGTH = 4
        private const val ZERO_PADDING = '0'
        private val START_FRAME = byteArrayOf(0x2)
        private val END_FRAME = byteArrayOf(0x3)
        private const val CONFIRMATION_CODE = 0x6.toByte()
        private const val CONFIRMATION_CODE_LENGTH = 1
        private const val BUFFER_MAX_SIZE = 2048
        private const val TIME_OUT = 50000L

        private const val LENGTH_FIELDS_SIZE = 4
        private const val PRIME1_LENGTH_INITIAL_POSITION = 5
        private const val PRIME1_LENGTH_FINAL_POSITION =
            PRIME1_LENGTH_INITIAL_POSITION + LENGTH_FIELDS_SIZE
        private const val PRIME1_INITIAL_POSITION = 9
        private const val onlyApprovalOnline = "9F350122"
        private const val transactionType = "00"
        private const val TAG_9F03_DEFAULT_TIP = "000000000000"
        private const val entryModeBandCode = "02"
        private const val entryModeContactCode = "05"
        private const val entryModeContactlessCode = "07"

        private const val TAG_DF8123 = "DF8123"
        private const val TAG_DF8126 = "DF8126"
        private const val TAG_9F02 = "9F02"
        private const val TAG_9A = "9A"
        private const val TAG_9F21 = "9F21"
        private const val TAG_9F1A = "9F1A"
        private const val TAG_5F2A = "5F2A"
        private const val TAG_9F1B = "9F1B"
        private const val TAG_9C = "9C"
        private const val TAG_DF918111 = "DF918111"
        private const val TAG_DF918112 = "DF918112"
        private const val TAG_DF918110 = "DF918110"
        private const val TAG_DF918205 = "DF918205"
        private const val TAG_DF918206 = "DF918206"
        private const val TAG_DF8124 = "DF8124"
        private const val TAG_DF8125 = "DF8125"
        private const val TAG_9F1E = "9F1E"
        private const val TAG_9F03 = "9F03"
        private const val TAG_9F39 = "9F39"
        private const val TAG_5F28_DEFAULT = "5F28020170"
        private const val TAG_9F6D_DEFAULT = "9F6D01C0"
        private const val TAG_9F6E_ENHANCED = "9F6E04D8E00000"

        private const val SUCCESS_EVENT = 1.toByte()
        private const val CANCEL_EVENT = 0.toByte()
        private const val CANCEL_PIN_PROCESS = -8020
        private const val CANCEL_AAC_PROCESS = -8025
        private const val EMV_FALLBACK = -8014
        private const val ARPC_CODE = -8026
    }

    private enum class DebitoEnum {
        ONLINEPIN, OFFLINEPIN, NULL
    }


    private var kernelRunState = false
    private var kernelAvailableState = false
    private val MAX_RETRY_COUNT = 3
    private val RETRY_INTERVALS: Long = 3000
    private var retry = 0
    private var TIMEOUT = 45
    private lateinit var emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    private lateinit var kernelAvailableCallback: (callbackObjectType: KernelAvailableStateCallBackObjectSDK) -> Unit
    private var isKernelFunctionCall: Boolean = false
    private var cancelProcessEmv: Boolean = false
    private var deviceService: UDeviceService? = null
    private var pinpad: UPinpad? = null
    protected var emv: UEMV? = null
    protected var emvOption: EMVOption? = null
    protected var cardOption: CardOption? = null
    private var idEmvList: List<IdEmv>? = null
    private var certEmvList: List<CertEmv>? = null
    private var listCandidateApp: List<CandidateAID>? = null
    private var codTerm: String = ""
    private var codEsta: String = ""
    private var systemTrace: String = Constants.EMPTY_STRING
    private var amountEmv = 0L
    private var amountEmvString = Constants.EMPTY_STRING
    private var totalAmount: Double = 0.0
    private var changeTagForDcc: Boolean = false
    private var isDcc: Boolean = false
    private var transactionCurrencyCode: String? = ""
    private var applicationId = ""
    private var shouldDeclineForAidNotFound = false
    private var mExistSlot: CardSlotType? = null
    private var bestAidFound = ""
    private var ctaclesscvm: Long = 0L
    private var tagCountryCode = ""
    private var tag57 = ""
    private var track2 = ""
    private var tagCryptogram = ""
    private var tagTsi = ""
    private var tagTvr = ""
    private var tagDFName = ""
    private var tagCardHolderName = ""
    private var emvString = ""
    private var pinBlock: String = Constants.EMPTY_STRING

    private var deviceManager: UDeviceManager? = null
    private var serialPort: USerialPort? = null
    private var keyComponents: ByteArray? = null
    private var positionHelper = 0

    private var cardNo = ""
    private var pinBlockTypeCard = "NOPIN" //: String? = null to do
    private var isContactEntryMode = false
    private var tag84 = ""
    private var cardFranchise = ""
    private var tag82 = ""
    private var arpcValidationIsRequiredForCard = true

    private var kernerWantsGoOnlineRequest = false
    private var setConfirmCardNoResponse = false
    private var emvTagsObjectOnline: EmvCallbackObjectSDK.EmvOnlineRequestCallbackSDK? = null
    private var isProcessCard = false
    private var isContactlessCard = false
    private var debitoType = DebitoEnum.NULL
    private var isPinOffline = false
    private var isPinOfflineCancel = false
    private var isAacDecline = false
    private var isOnEndProcess = false
    private var fallbackResponseCode: Int = 60930
    private var arpcErrorCode: Int = 60941
    private var errorResponseCode: Boolean = false
    private var errorCodeForMissingTags: Int = 67108865// Error code due to missing Tags in configuration
    private var isErrorCodeForMissingTags = false
    private var onEndProcessResultFail :Int = 0

    init {
        emvOption = EMVOption.create()
        cardOption = CardOption.create()
        bindService()
    }

    override suspend fun invokeNexgo(isRequiredUID: Boolean?): String {
        return ""
    }


    override suspend fun isKernelRun(): Boolean {

        if (kernelRunState  && deviceService != null){
            return kernelRunState
        }else{
            //startkernel to do
            retry = 0
            return bindService()
        }

    }

    private fun bindService(): Boolean {
        if (kernelRunState) {
            return kernelRunState
        }
        val service = Intent("com.usdk.apiservice")
        service.setPackage("com.usdk.apiservice")
        kernelRunState= context.bindService(service, this, Context.BIND_AUTO_CREATE)

        // If the binding fails, it is rebinded
        if (!kernelRunState && retry++ < MAX_RETRY_COUNT) {
            Handler().postDelayed({ bindService() }, RETRY_INTERVALS)
        }
        return kernelRunState
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
        this.idEmvList = idEmvList
        this.codTerm = codTerm
        this.codEsta = codEsta
        this.systemTrace = systemTrace
        this.totalAmount = totalAmount
        this.emvCallback = emvCallback
        changeTagForDcc = changeTagToDCC
        this.isDcc = isDcc
        if (changeTagForDcc) {
            this.transactionCurrencyCode = transactionCurrencyCode
        } else {
            this.transactionCurrencyCode = COLOMBIA_CURRENCY_CODE
        }

        pinpad = this.getPinPad(
            KAPId(0, 0),
            KeySystem.KS_MKSK,
            DeviceName.IPP,
        )
        pinpad?.open()

        validateAndInitEmv()
    }

    private suspend fun validateAndInitEmv() {

        if (changeTagForDcc) {
            if (totalAmount.toString().isNotEmpty()) {
                val amountRound: Double = (totalAmount * 100.0).roundToInt() / 100.0
                val amountSplit = amountRound.toString().split(Constants.SEPARATOR_DECIMAL)
                val amountInt = amountSplit[0]
                amountEmvString = if (amountSplit.size >= 2) {
                    if (amountSplit[1].length >= 2) {
                        val amountDecimal = amountSplit[1].substring(0, 2)
                        amountInt
                            .padStart(10, ZERO_NUMBER_PADDING) + amountDecimal
                    } else {
                        val amountDecimal = amountSplit[1].substring(0, 1)
                        amountInt
                            .padStart(
                                10,
                                ZERO_NUMBER_PADDING
                            ) + amountDecimal + ONE_DECIMAL_AMOUNT_STRING
                    }
                } else {
                    amountInt
                        .padStart(10,
                            ZERO_NUMBER_PADDING
                        ) + DECIMALS_AMOUNT_STRING
                }
            }
        } else {
            amountEmv = totalAmount.toLong()
            amountEmvString = amountEmv.toString()
                .padStart(
                    AMOUNT_EMV_LENGTH,
                    ZERO_NUMBER_PADDING
                ) + DECIMALS_AMOUNT_STRING
        }
        startEmv()
    }

    override suspend fun startEmv() {
        emv?.startEMV(emvOption?.toBundle(), emvEventHandler)
        cancelProcessEmv = false
    }

    override suspend fun startEmv(
        idEmvList: List<IdEmv>,
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) {
        this.emvCallback = emvCallback
        this.idEmvList = idEmvList
        emv?.startEMV(emvOption?.toBundle(), emvEventHandler)
        cancelProcessEmv = false
    }

    override suspend fun onPinInputSuccess(
        retCode: Int,
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) {
        this.emvCallback = emvCallback
        if (isContactlessCard && debitoType != DebitoEnum.NULL){
            doOnlineProcess()
        } else {
            respondCVMResult(SUCCESS_EVENT)
        }
    }

    override suspend fun onPinInputFail(emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit) {
        isPinOfflineCancel = true
        this.emvCallback = emvCallback
        respondCVMResult(CANCEL_EVENT)
        if(isOnEndProcess){
            emvCallback.invoke(EmvCallbackObjectSDK.EmvSDKFinishedCallback(EmvCallbackObjectSDK.EMV_FINISHED_CALLBACK, CANCEL_PIN_PROCESS))
            isOnEndProcess = false
            cancelEmvProcess(emvCallback)
        }
    }

    override suspend fun onSetConfirmCardNoResponse(emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit) {
        setConfirmCardNoResponse = true
        if (isContactlessCard && debitoType == DebitoEnum.NULL && !isAacDecline){
            doOnlineProcess()
        } else if (isContactlessCard && debitoType != DebitoEnum.NULL){
            when(debitoType){
                DebitoEnum.ONLINEPIN ->{
                    doPinProcess(true, 0)
                }
                DebitoEnum.OFFLINEPIN ->{
                    isPinOffline = true
                    doPinProcess(false, 0)
                }
                else -> {}
            }
        } else if(isAacDecline){
            emvCallback.invoke(EmvCallbackObjectSDK.EmvSDKFinishedCallback(EmvCallbackObjectSDK.EMV_FINISHED_CALLBACK, CANCEL_AAC_PROCESS))
            isAacDecline = false
        }else if(isErrorCodeForMissingTags)
            emvCallback.invoke(EmvCallbackObjectSDK.EmvSDKFinishedCallback(EmvCallbackObjectSDK.EMV_FINISHED_CALLBACK, onEndProcessResultFail))
        else{
            emv?.respondEvent(null)
        }
    }

    override fun onOnlineResponse(
        field55Response: String?,
        responseCode: String?,
        authCode: String?,
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) {
        val onlineResult = StringBuffer()
        onlineResult.append(EMVTag.DEF_TAG_ONLINE_STATUS).append("01").append(responseCode)
        if (field55Response == "" && responseCode == "00") {
            onlineResult.append(EMVTag.EMV_TAG_TM_ARC).append("02").append("3030")
        } else{
            onlineResult.append(EMVTag.EMV_TAG_TM_ARC).append("02").append(field55Response?.takeLast(4))
        }
        val onlineApproved = true
        onlineResult.append(EMVTag.DEF_TAG_AUTHORIZE_FLAG).append("01").append(if (onlineApproved) "01" else "00")
        onlineResult.append(
            TLV.fromData(EMVTag.DEF_TAG_HOST_TLVDATA, BytesUtil.hexString2Bytes(field55Response)).toString()
        )
        emv!!.respondEvent(onlineResult.toString())
    }

    override fun cancelEmvProcess(emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit) {
        this.emvCallback = emvCallback
        errorResponseCode = false
        isPinOffline = false
        isContactlessCard = false
        cancelProcessEmv = true
        try {
            isProcessCard = false
            emv?.stopEMV()
            emv?.stopProcess()
        } catch (e: Exception) {
        }
    }

    override fun stopSearchCard() {
        isProcessCard = false
        isPinOffline = false
        emv?.stopSearch()
    }

    override fun onSetSelAppResponse(
        selResult: Int?,
        emvCallback: (callbackObjectType: EmvCallbackObjectSDK) -> Unit
    ) {
        listCandidateApp?.let {
            selResult?.let { int ->
                respondAID(it[ int - 1].aid)
                listCandidateApp = null
            }
        }
    }

    override fun isChipCardInserted(): Boolean {
        val cardReader: UICCpuReader? = getICCpuReader()
        return cardReader?.isCardIn ?: false
    }

    private fun settingCertificationAndAid(){
        idEmvList?.let { setAidEmv(it) }
        certEmvList?.let{ setCertEmvDataSecond(it)}
    }

    protected var emvEventHandler: EMVEventHandler = object : EMVEventHandler.Stub() {
        @Throws(RemoteException::class)
        override fun onInitEMV() {
        }

        @Throws(RemoteException::class)
        override fun onWaitCard(flag: Int) {
            //doWaitCard(flag)
        }

        @Throws(RemoteException::class)
        override fun onCardChecked(cardType: Int) {
            // Only happen when use startProcess()
            //doCardChecked(cardType) to do
        }

        @Throws(RemoteException::class)
        override fun onAppSelect(reSelect: Boolean, list: List<CandidateAID>) {
            listCandidateApp = list
            if (list.size > 1) {
                val aidInfoList: MutableList<String> = java.util.ArrayList()
                for (candAid in list) {
                    aidInfoList.add(String(candAid.appLabel))
                }
                emvCallback.invoke(
                    EmvCallbackObjectSDK.EmvSelectAppCallbackSDK(
                        EmvCallbackObjectSDK.EMV_SELECT_APP_CALLBACK,
                        aidInfoList,
                        MapperIngenico.candidateAppInfoEntityToListCandidateAppInfoIngenico(list),
                        reSelect
                    )
                )
            }else {
                respondAID(list[0].aid)
            }
        }

        @Throws(RemoteException::class)
        override fun onFinalSelect(finalData: FinalData) {
            doFinalSelect(finalData)
        }

        @Throws(RemoteException::class)
        override fun onReadRecord(cardRecord: CardRecord) {
            if(isProcessCard.not() && takeDataFromOnReadRecord()){
                isProcessCard = onCardInfo()
            } else {
                binByRangeCallback(tagCountryCode)
            }
        }

        @Throws(RemoteException::class)
        override fun onCardHolderVerify(cvmMethod: CVMMethod) {
            when (cvmMethod.cvm) {
                CVMFlag.EMV_CVMFLAG_ONLINEPIN.toByte() -> {
                    doPinProcess(true, BytesUtil.bytesToInt(
                        byteArrayOf(cvmMethod.pinTimes)
                    ))
                }

                CVMFlag.EMV_CVMFLAG_OFFLINEPIN.toByte() -> {
                    isPinOffline = true
                    doPinProcess(false, BytesUtil.bytesToInt(
                        byteArrayOf(cvmMethod.pinTimes)
                    ))
                }
            }

        }

        @Throws(RemoteException::class)
        override fun onOnlineProcess(transData: TransData) {
            if(isPinOffline && isPinOfflineCancel){
                isPinOfflineCancel = false
                emvCallback.invoke(EmvCallbackObjectSDK.EmvSDKFinishedCallback(EmvCallbackObjectSDK.EMV_FINISHED_CALLBACK, CANCEL_PIN_PROCESS))
            } else {
                doOnlineProcess()
            }
        }

        @Throws(RemoteException::class)
        override fun onEndProcess(result: Int, transData: TransData) {
            isOnEndProcess = true
            if (result == EMVError.SUCCESS) {
                when (transData.cvm.toInt()) {
                    CVMFlag.EMV_CVMFLAG_NOCVM -> {
                        if (!cancelProcessEmv && !isContactlessCard) {
                            emvCallback.invoke(
                                EmvCallbackObjectSDK.EmvSDKFinishedCallback(
                                    EmvCallbackObjectSDK.EMV_FINISHED_CALLBACK,
                                    result
                                )
                            )
                        } else if (transData.acType.toInt() == ACType.EMV_ACTION_AAC) {
                            isAacDecline = true
                        } else {
                            isContactlessCard = true
                        }
                    }

                    CVMFlag.EMV_CVMFLAG_OFFLINEPIN -> {
                        debitoType = DebitoEnum.OFFLINEPIN
                    }

                    CVMFlag.EMV_CVMFLAG_ONLINEPIN -> {
                        debitoType = DebitoEnum.ONLINEPIN
                    }

                    CVMFlag.EMV_CVMFLAG_SIGNATURE -> {
                        if (!cancelProcessEmv && !isContactlessCard) {
                            emvCallback.invoke(
                                EmvCallbackObjectSDK.EmvSDKFinishedCallback(
                                    EmvCallbackObjectSDK.EMV_FINISHED_CALLBACK,
                                    result
                                )
                            )
                        } else {
                            isContactlessCard = true
                        }
                    }

                    else -> {
                        emvCallback.invoke(
                            EmvCallbackObjectSDK.Error(
                                callBacktype = EmvCallbackObjectSDK.ERROR_CALLBACK
                            )
                        )
                    }
                }
            } else if (result == fallbackResponseCode) {
                emvCallback.invoke(
                    EmvCallbackObjectSDK.EmvSDKFinishedCallback(
                        EmvCallbackObjectSDK.EMV_FINISHED_CALLBACK,
                        EMV_FALLBACK
                    )
                )
            } else if (result == arpcErrorCode) {
                emvCallback.invoke(
                    EmvCallbackObjectSDK.EmvSDKFinishedCallback(
                        EmvCallbackObjectSDK.EMV_FINISHED_CALLBACK,
                        ARPC_CODE
                    )
                )
            } else if(result == errorCodeForMissingTags){
                isErrorCodeForMissingTags = true
                onEndProcessResultFail = result
            }
            else {
                isContactlessCard = false
                errorResponseCode = true
                emvCallback.invoke(
                    EmvCallbackObjectSDK.EmvSDKFinishedCallback(
                        EmvCallbackObjectSDK.EMV_FINISHED_CALLBACK, result
                    )
                )
            }
        }

        @Throws(RemoteException::class)
        override fun onVerifyOfflinePin(
            flag: Int,
            random: ByteArray,
            caPublicKey: CAPublicKey,
            offlinePinVerifyResult: OfflinePinVerifyResult
        ) {
            doVerifyOfflinePin(flag, random, caPublicKey, offlinePinVerifyResult)
        }

        fun doVerifyOfflinePin(
            flag: Int, random: ByteArray, caPublicKey: CAPublicKey,
            offlinePinVerifyResult: OfflinePinVerifyResult,
        ) {
            val icToken = 0
            val cmdFmt: Byte = OfflinePinVerify.VCF_DEFAULT
            val offlinePinVerify = OfflinePinVerify(
                flag.toByte(), icToken, cmdFmt, random,
            )
            val pinVerifyResult = PinVerifyResult()
            val ret: Boolean = pinpad?.verifyOfflinePin(
                offlinePinVerify, getPinPublicKey(caPublicKey = caPublicKey), pinVerifyResult,
            ) ?: false

            if (ret.not()) {
                stopEmv()
                return
            }

            offlinePinVerifyResult.apply {
                setSW(pinVerifyResult.sW1.toInt(), pinVerifyResult.sW2.toInt())
                result = pinVerifyResult.apduRet.toInt()
            }
        }

        private fun stopEmv() {
            emv?.stopEMV()
            closePinPad()
        }

        private fun closePinPad() {
            pinpad?.close()
        }

        private fun getPinPublicKey(caPublicKey: CAPublicKey?): PinPublicKey? {
            return caPublicKey?.let {
                val pinPublicKey = PinPublicKey()
                pinPublicKey.rid = caPublicKey.rid
                pinPublicKey.exp = caPublicKey.exp
                pinPublicKey.expiredDate = caPublicKey.expDate
                pinPublicKey.hash = caPublicKey.hash
                pinPublicKey.index = caPublicKey.index
                pinPublicKey.mod = caPublicKey.mod
                pinPublicKey
            }
        }

        @Throws(RemoteException::class)
        override fun onObtainData(ins: Int, data: ByteArray) {

            //to do
            /*outputText(
                "=> onObtainData: instruction is 0x" + Integer.toHexString(ins) + ", data is " + BytesUtil.bytes2HexString(
                    data
                )
            )*/
        }

        @Throws(RemoteException::class)
        override fun onSendOut(ins: Int, data: ByteArray) {
            if (ins == KernelINS.CLOSE_RF) {
                emv?.halt()
            }

            if(isProcessCard.not() && takeDataFromOnSendOut()) {
                isProcessCard = onCardInfo()
            }
        }
    }

    @Throws(RemoteException::class)
    fun doFinalSelect(finalData: FinalData) {

        bestAidFound = getAid()

        setParameters(finalData.kernelID)

        val tagsList = TagsEmvUtil.tagsEmv()
        var tlvList = ""
        for (i in tagsList.indices) {
            if (tagsList[i].isNotEmpty()){
                tlvList += tagsList[i]
            }
        }
    }

    private fun doOnlineProcess(){
        emvTagsObjectOnline = setOnlineRequestCallback()
        if (emvTagsObjectOnline != null) {
            kernerWantsGoOnlineRequest = true
            setConfirmCardNoResponseAndIsOnlineRequest(emvTagsObjectOnline!!)
        }
    }

    private fun doPinProcess(isOnline: Boolean, leftTimes: Int){
        cardNo.let { card ->
            emvCallback.invoke(
                EmvCallbackObjectSDK.EmvPinProcessCallbackSDK(
                    EmvCallbackObjectSDK.EMV_PIN_PROCESS_CALLBACK, isOnline, leftTimes, card
                )
            )
        }
    }

    fun respondAID(aid: ByteArray?) {
        try {
            val tmAid = TLV.fromData(EMVTag.EMV_TAG_TM_AID, aid)
            emv?.respondEvent(tmAid.toString())
        } catch (_: java.lang.Exception) { }
    }

    private fun respondCVMResult(result: Byte) {
        try {
            val chvStatus = TLV.fromData(EMVTag.DEF_TAG_CHV_STATUS, byteArrayOf(result))
            emv?.respondEvent(chvStatus.toString())
        } catch (_: Exception) {
        }
    }

    private fun setConfirmCardNoResponseAndIsOnlineRequest(emvTagsObjectOnline: EmvCallbackObjectSDK.EmvOnlineRequestCallbackSDK){
        if (kernerWantsGoOnlineRequest  &&  setConfirmCardNoResponse && !errorResponseCode){
            emvCallback.invoke(emvTagsObjectOnline)
            isProcessCard = false
            setConfirmCardNoResponse = false
            kernerWantsGoOnlineRequest = false
            isContactlessCard = false
            debitoType = DebitoEnum.NULL
        } else if (errorResponseCode){
            errorResponseCode = false
        }
    }

    private fun getTagList(): String{
        var tagList = ""
        var countryTag5F28 = ""
        var countryTag9F5A = ""
        val tagArray = TagsEmvUtil.tagsEmv().toTypedArray()
        val tags: MutableList<String> = ArrayList()
        for (i in tagArray.indices) {
            val t = tagArray[i].trim { it <= ' ' }
            if (!TextUtils.isEmpty(t)) {
                tags.add(t)
            }
        }
        val list: List<TlvResponse> = ArrayList()
        emv?.getKernelDataList(tags, list)
        for (i in list.indices) {
            val info = list[i]

            if (info.result == 0) {
                if (info.value != null && info.value.isNotEmpty()) {
                    tagList += if (BytesUtil.bytes2HexString(info.tag) == TagsEmvUtils.EMV_TAG_9F1E) {
                        setCompleteTag(
                            BytesUtil.bytes2HexString(info.tag),
                            TefUtil.stringToHex(BytesUtil.bytes2HexString(info.value)),
                        )
                    } else {
                        setCompleteTag(
                            BytesUtil.bytes2HexString(info.tag),
                            BytesUtil.bytes2HexString(info.value)
                        )
                    }
                }
            }

            if (BytesUtil.bytes2HexString(info.tag) == EMVTag.EMV_TAG_IC_DFNAME){
                tag84 = setCompleteTag(BytesUtil.bytes2HexString(info.tag), BytesUtil.bytes2HexString(info.value))

                tagDFName = tag84
            }else if (BytesUtil.bytes2HexString(info.tag) == EMVTag.EMV_TAG_IC_AIP){
                tag82 = setCompleteTag(BytesUtil.bytes2HexString(info.tag), BytesUtil.bytes2HexString(info.value))
            }else if (BytesUtil.bytes2HexString(info.tag) == EMVTag.EMV_TAG_IC_ISSCOUNTRYCODE){
                countryTag5F28 = BytesUtil.bytes2HexString(info.value)
            }else if (BytesUtil.bytes2HexString(info.tag) == EMVTag.V_TAG_IC_9F5A){
                countryTag9F5A = setCompleteTag(BytesUtil.bytes2HexString(info.tag), BytesUtil.bytes2HexString(info.value))
            }else if (BytesUtil.bytes2HexString(info.tag) == EMVTag.EMV_TAG_IC_TRACK2DATA){
                cardNo = ""
                tag57 = setCompleteTag(BytesUtil.bytes2HexString(info.tag), BytesUtil.bytes2HexString(info.value))
                if (tag57.contains("D")){
                    val tag57Split = tag57.split("D")
                    val tag57Data = if (tag57Split[0].length % 2 == 0){
                        getTagInformation(tag57Split[0])
                    }else{
                        getTagInformationForOddNumber(tag57Split[0])
                    }
                    tag57Data.forEach {
                        cardNo += it
                    }
                }
                track2 = if (tag57.endsWith("F")) {
                    if (tag57.length >= 4) tag57.substring(4, tag57.length - 1) else ""
                } else {
                    if (tag57.length >= 4)tag57.substring(4, tag57.length) else ""
                }
            }else if (BytesUtil.bytes2HexString(info.tag) == EMVTag.EMV_TAG_IC_AC){
                tagCryptogram = setCompleteTag(BytesUtil.bytes2HexString(info.tag), BytesUtil.bytes2HexString(info.value))
            }else if (BytesUtil.bytes2HexString(info.tag) == EMVTag.EMV_TAG_TM_TSI){
                tagTsi = setCompleteTag(BytesUtil.bytes2HexString(info.tag), BytesUtil.bytes2HexString(info.value))
            }else if (BytesUtil.bytes2HexString(info.tag) == EMVTag.EMV_TAG_TM_TVR){
                tagTvr = setCompleteTag(BytesUtil.bytes2HexString(info.tag), BytesUtil.bytes2HexString(info.value))
            }else if (BytesUtil.bytes2HexString(info.tag) == EMVTag.EMV_TAG_IC_CHNAME){
                tagCardHolderName = setCompleteTag(BytesUtil.bytes2HexString(info.tag), BytesUtil.bytes2HexString(info.value))
            }

            cardFranchise = validateFranchise(tag84)
        }
        tagCountryCode = getCountryCode(countryTag5F28, countryTag9F5A)

        if (bestAidFound.isVisa().not() && isContactLess()) {
            // You might think this line of code does nothing, but if you remove it, the
            // franchise certification breaks. Thank you.
            emv?.respondEvent(null)
        }
        return tagList
    }

    private fun setCompleteTag(tagName: String, data: String): String{
        return generateTag(tagName, data, data.length / 2) ?: ""
    }

    private fun loadEmvTags(): Boolean{
        tagCountryCode = ""
        tag57 = ""
        track2 = ""
        tagCryptogram = ""
        tagTsi = ""
        tagTvr = ""
        tagDFName = ""
        tagCardHolderName = ""
        emvString = ""
        tag84 = ""
        cardFranchise = ""
        tag82 = ""
        cardNo= ""

        emv?.let {
            emvString = getTagList()
        }


        //return true if all fields are filled
        return tagCountryCode.isNotEmpty() &&
                tag57.isNotEmpty()&&
                track2.isNotEmpty()&&
                tagCryptogram.isNotEmpty() &&
                tagTsi.isNotEmpty() &&
                tagTvr.isNotEmpty() &&
                tagDFName.isNotEmpty() &&
                emvString.isNotEmpty() &&
                cardNo.isNotEmpty()
    }

    private fun validateFranchise(tag84String: String?): String {

        if (!tag84String.isNullOrEmpty()) {

            val tag84InformationArray = getTagInformation(tag84String)
            //A0 00 00 00 03 visa
            //A0 00 00 00 04 mastercard
            //A0 00 00 00 25 amex

            if (tag84InformationArray[0] == "A0" &&
                tag84InformationArray[1] == "00" &&
                tag84InformationArray[2] == "00" &&
                tag84InformationArray[3] == "00" &&
                tag84InformationArray[4] == "03"
            ) {
                return Constants.VISA_FRANCHISE
            } else if (tag84InformationArray[0] == "A0" &&
                tag84InformationArray[1] == "00" &&
                tag84InformationArray[2] == "00" &&
                tag84InformationArray[3] == "00" &&
                tag84InformationArray[4] == "04"
            ) {
                return Constants.MASTERCARD_FRANCHISE
            } else if (tag84InformationArray[0] == "A0" &&
                tag84InformationArray[1] == "00" &&
                tag84InformationArray[2] == "00" &&
                tag84InformationArray[3] == "00" &&
                tag84InformationArray[4] == "25"
            ) {
                return Constants.AMEX_FRANCHISE
            } else {
                return Constants.UNKNOWN_FRANCHISE
            }
        }

        return ""
    }

    private fun setOnlineRequestCallback(): EmvCallbackObjectSDK.EmvOnlineRequestCallbackSDK? {
        return emv?.let {
            emvString = getTagList()
            EmvCallbackObjectSDK.EmvOnlineRequestCallbackSDK(
                EmvCallbackObjectSDK.EMV_ONLINE_REQUEST_CALLBACK,
                pinBlock,
                track2,
                tag57,
                tagCryptogram,
                tagTsi,
                tagTvr,
                tagDFName,
                tagCardHolderName,
                emvString,
                tagCountryCode,
                false,
                shouldInfoEmvForDcc = changeTagForDcc,
                isDcc = this.isDcc
            )
        }

    }
    private fun getAid(): String {

        applicationId = ""
        val aid = emv?.getTLV(EMVTag.EMV_TAG_IC_AID)

        if ((mExistSlot == CardSlotType.RF || mExistSlot == CardSlotType.ICC1) && aid != null) {

            val hexString = aid.uppercase()

            idEmvList?.let { allIdEmv ->
                val idEmvListLength = allIdEmv.size ?: 0
                for (i in 0 until idEmvListLength) {
                    val applicationIdTemp = allIdEmv[i].applicationId ?: ""
                    if (hexString.contains(applicationIdTemp) && applicationIdTemp.length > applicationId.length) {
                        applicationId = applicationIdTemp
                    }
                }
            }

            shouldDeclineForAidNotFound = applicationId == ""

        }

        return applicationId
    }

    private fun setParameters(kernelId : Byte) {

        idEmvList?.forEach{
            if (it.applicationId == bestAidFound) {

                val tlvList = java.lang.StringBuilder()

                // Set terminal capabilities
                val tagTerminalCapabilities = setTerminalCapabilities(it)
                tlvList.append(tagTerminalCapabilities)

                //set Dol
                emv?.setDOL(ActionFlag.ADD, it.aidDdol)

                //DF8123 - ctlessLowerFloorLimit
                val tagDF8123 = generateTag(TAG_DF8123, it.ctlessLowerFloorLimit.toString(), 6)
                tlvList.append(tagDF8123)

                //Set DF8126 - contactless Floor Limit
                val tagDF8126 = generateTag(TAG_DF8126, it.ctlessCvmFloorLimit.toString(), 6)
                tlvList.append(tagDF8126)

                //Set 9F02v-amount
                val tag9F02 = generateTag(TAG_9F02, amountEmvString, 6)
                tlvList.append(tag9F02)

                //Set 9A - transactionDate
                val transactionDate = SimpleDateFormat("yyMMdd", Locale.getDefault()).format(Date())
                if (!transactionDate.isNullOrEmpty()){
                    val tag9A = generateTag(TAG_9A, transactionDate, 3)
                    tlvList.append(tag9A)
                }

                //Set 9F21 - transactionTime
                val transactionTime = SimpleDateFormat("hhmmss", Locale.getDefault()).format(Date())
                if (!transactionTime.isNullOrEmpty()){
                    val tag9F21 = generateTag(TAG_9F21, transactionTime, 3)
                    tlvList.append(tag9F21)
                }

                //Set 9F1A - terminal country code
                val tag9F1A = generateTag(TAG_9F1A, it.terminalCountryCode.toString(), 2)
                tlvList.append(tag9F1A)

                //Set 5F2A -transaction currency code
                val tag5F2A = transactionCurrencyCode?.let { it1 -> generateTag(TAG_5F2A, it1, 2) }
                tlvList.append(tag5F2A)

                //Set Only Online approval
                tlvList.append(onlyApprovalOnline)

                //Set 9F1B - terminal floor limit
                val tag9F1B = generateTag(TAG_9F1B, it.ctlessLowerFloorLimit.toString(), 4)
                tlvList.append(tag9F1B)

                //Set 9C - transactionType
                val tag9C = generateTag(TAG_9C, transactionType, 1)
                tlvList.append(tag9C)

                //Set DF918111 -terminal action code decline
                val tagDF918111 = generateTag(TAG_DF918111, it.tacDenial.toString(),5)
                tlvList.append(tagDF918111)

                //Set DF918112 - terminal action code online
                val tagDF918112 = generateTag(TAG_DF918112, it.tacOnline.toString(), 5)
                tlvList.append(tagDF918112)

                //Set DF918110 - terminal action code default
                val tagDF918110 = generateTag(TAG_DF918110, it.tacDefault.toString(), 5)
                tlvList.append(tagDF918110)

                //  Set DF918205 - CVM Capability (CVM Required)
                val tagDF918205 = generateTag(TAG_DF918205, "F0", 1)
                tlvList.append(tagDF918205)

                //  Set DF918206 - CVM Capability (CVM Required)
                val tagDF918206 = generateTag(TAG_DF918206, "08", 1)
                tlvList.append(tagDF918206)

                //Set DF8124 - Terminal Contactless Transaction Limit
                val tagDF8124 = generateTag(TAG_DF8124, it.ctlessUpperFloorLimit.toString(),6)
                tlvList.append(tagDF8124)

                //Set DF8125 - CDCVM Transaction Limit
                val tagDF8125 = generateTag(TAG_DF8125, it.ctlessCvmFloorLimit.toString() ,6)
                tlvList.append(tagDF8125)

                //Set 9F1E - IFDSerialNo
                val tag9F1E = generateTag(TAG_9F1E, getDeviceSerial(), 8)
                tlvList.append(tag9F1E)

                //Set 9F03 - AmountOtherNum "Here is the tip but we always send the data in zeros"
                val tag9F03 = generateTag(TAG_9F03, TAG_9F03_DEFAULT_TIP, 6)
                tlvList.append(tag9F03)

                //Set 9F39 - POSEntryMode
                val tag9F39 = generateTag(TAG_9F39, getCodeEntryModeType(), 1)
                tlvList.append(tag9F39)

                if (bestAidFound.isAmex()) {

                    // Set 9F6D - Contactless Reader Capabilities
                    tlvList.append(TAG_9F6D_DEFAULT)

                    // Set 9F6E - Enhanced Contactless Reader Capabilities
                    tlvList.append(TAG_9F6E_ENHANCED)
                }

                // TODO: validate if these tags are neccesaries to set
                /*.append("DF9182010102")
                .append("DF9182020100")
                .append("DF9181150100")
                .append("DF9182070120")
                .append("DF9182080120").toString()*/

                //Set parameters
                emv?.setTLVList(kernelId.toInt(), tlvList.toString())
                emv?.respondEvent(null)
            }
        }
    }

    private fun setTerminalCapabilities(idEmv: IdEmv): String{

        var terminalCapabilitiesToReturn = resetTerminalCapabilities(idEmv)

        if (mExistSlot == CardSlotType.RF && idEmv.applicationId != null){
            this.bestAidFound = getAid()
            val isVisaContactless = bestAidFound.isVisa()
            if (isVisaContactless){
                terminalCapabilitiesToReturn = resetTerminalCapabilitiesVisaContactless(idEmv)
            }
            idEmv?.let {
                terminalCapabilitiesToReturn =
                    configTerminalCapabilitiesContactless(idEmv, isVisaContactless)
            }
            when {
                bestAidFound.isMaster() -> {
                    terminalCapabilitiesToReturn += configMastercardContactless(bestAidFound, idEmv)
                }
            }
        }

        return terminalCapabilitiesToReturn
    }

    private fun configMastercardContactless(aid: String, idEmv: IdEmv): String {
        getCtaclesscvm()
        if (totalAmount > ctaclesscvm) {
            if (aid.uppercase().isMaestro()) {
                //maestro only support online pin
                return generateTag("DF8118", "40", 1) + generateTag("DF8119", "08", 1)
            }
        }
        return ""
    }

    private fun configTerminalCapabilitiesContactless(idEmv: IdEmv, isVisaContactless: Boolean): String {
        getCtaclesscvm()
        return if(isVisaContactless) {
            if (totalAmount > ctaclesscvm) {
                setByte1TtqToFixDccContactless(idEmv)
            } else {
                resetTerminalCapabilitiesVisaContactless(idEmv)
            } + resetTerminalCapabilities(idEmv)
        } else {
            resetTerminalCapabilities(idEmv)
        }
    }

    private fun setByte1TtqToFixDccContactless(idEmv: IdEmv): String{
        val ctless9f66Length = idEmv.ctless9f66?.length ?: 0
        val byte1 = if (ctless9f66Length >= 6) idEmv.ctless9f66?.substring(0, 2) else ""
        val byte2 = if (ctless9f66Length >= 6) idEmv.ctless9f66?.substring(2, 4) else ""
        val byte3 = if (ctless9f66Length >= 6) idEmv.ctless9f66?.substring(4, 6) else ""
        val byte4 = if (ctless9f66Length >= 6) idEmv.ctless9f66?.substring(6, 8) else ""

        val ttqModified = byte1 + byte2 + byte3 + byte4
        return generateTag("9F66", ttqModified, 4)
    }

    private fun getCtaclesscvm() {
        getIdEmvById(bestAidFound)?.ctlessCvmFloorLimit?.toLong()?.let {
            ctaclesscvm = it/100
        }
    }

    private fun getIdEmvById(idEmv: String): IdEmv? {
        return idEmvList?.find { it.applicationId == idEmv }
    }

    private fun resetTerminalCapabilitiesVisaContactless(idEmv: IdEmv): String{
        return idEmv?.ctless9f66?.let { generateTag("9F66", it, 4) } ?: ""
    }

    private fun resetTerminalCapabilities(idEmv: IdEmv):String {
        this.bestAidFound = getAid()
        return generateTag("9F33", idEmv?.terminalCapabilities.toString() , 3)
    }

    private fun generateTag(tag: String, data: String, maxBytesTagLength: Int): String {
        val maxBytesTagLengthToSet: Int = maxBytesTagLength * 2
        return if (data.length < maxBytesTagLengthToSet) {
            val dataToSet = data.padStart(maxBytesTagLengthToSet, ZERO_NUMBER_PADDING)
            val dataToSetBytesLength: Int? = dataToSet?.length?.div(2)
            val dataToSetBytesLengthHEX =
                dataToSetBytesLength?.let { Integer.toHexString(it).padStart (2, ZERO_NUMBER_PADDING) }
            "$tag$dataToSetBytesLengthHEX$dataToSet"
        }else{
            val dataToFill = data.substring(data.length - maxBytesTagLengthToSet, data.length)
            val dataToSet = dataToFill.padStart(maxBytesTagLengthToSet, ZERO_NUMBER_PADDING)
            val dataToSetBytesLength: Int? = dataToSet?.length?.div(2)
            val dataToSetBytesLengthHEX =
                dataToSetBytesLength?.let { Integer.toHexString(it).padStart (2, ZERO_NUMBER_PADDING) }
            "$tag$dataToSetBytesLengthHEX$dataToSet"
        }
    }

    private fun configureVisaParameters(idEmv: IdEmv): String {


        val ctlessLowerFloorLimit = idEmv?.ctlessLowerFloorLimit?.toString()?.padEnd (12, ZERO_NUMBER_PADDING)


        //Set DF8126 Contactless Floor Limit
        val ctlessCvmLimit = idEmv?.ctlessCvmFloorLimit


        return "DF812306$ctlessLowerFloorLimit"
    }

    /*private fun configureMasterCardParameters(idEmv: IdEmv): String {

        // Set ctlessCvmFloorLimit
        val cvmCtlessFloorLimitBytesLength = idEmv?.ctlessCvmFloorLimit?.toString()?.length?.div(2)
        var ctlessCvmFloorLimitData = ""
        if (cvmCtlessFloorLimitBytesLength == 4) {
            ctlessCvmFloorLimitData = "0000" + idEmv.ctlessCvmFloorLimit
        } else if (cvmCtlessFloorLimitBytesLength == 5) {
            ctlessCvmFloorLimitData = "00" + idEmv.ctlessCvmFloorLimit
        } else if (cvmCtlessFloorLimitBytesLength != null) {
            if (cvmCtlessFloorLimitBytesLength >= 6) {
                ctlessCvmFloorLimitData = idEmv.ctlessCvmFloorLimit.toString().substring(0, 12)
            }
        }
        if (cvmCtlessFloorLimitBytesLength >= 0 && cvmCtlessFloorLimitBytesLength <)

        return "DF81230$cvmCtlessFloorLimitBytesLength$idEmv?.ctlessCvmFloorLimit?.toString()"
    }*/


    private fun onCardInfo(): Boolean{
        if (loadEmvTags()){
            activeBeepAndLed()
            pinBlockTypeCard = PinUtil.getPinBlockType(null).toString()
            pinBlockTypeCard?.let {
                emvCallback.invoke(EmvCallbackObjectSDK.PinSDKBlockCallback(EmvCallbackObjectSDK.PIN_BLOCK_CALLBACK, it))
            }

            var entryMode = ""

            if (mExistSlot == CardSlotType.RF){
                isContactEntryMode = false
                entryMode = EntryModeType.CONTACTLESS.toString()
            }else{
                isContactEntryMode = true
                entryMode = EntryModeType.CHIP.toString()
            }

            emvCallback.invoke(EmvCallbackObjectSDK.EntrySDKModeCallback(EmvCallbackObjectSDK.ENTRY_MODE_CALLBACK, entryMode))
            if (isContactlessCard || takeDataFromOnReadRecord()) binByRangeCallback(tagCountryCode)
            return true
        }else{
            emvCallback.invoke(
                EmvCallbackObjectSDK.Error(
                callBacktype = EmvCallbackObjectSDK.ERROR_CALLBACK
            ))
            return false
        }

    }

    private fun binByRangeCallback(tagCountryCodeString: String){
        applicationId = getAid()

        // TODO: CHANGE THIS TRUE FOR PROD
        arpcValidationIsRequiredForCard = tag82?.let { isTag82Byte1Bit3(it) } == true

        emv?.let {
            emvString = getTagList()
        }
        val emvTagDfName = tag84

        emvCallback.invoke(
            EmvCallbackObjectSDK.BinByRangeCallbackSDK(
                EmvCallbackObjectSDK.BIN_BY_RANGE_CALLBACK,
                cardNo,
                tagCountryCode,
                cardFranchise,
                arpcValidationIsRequiredForCard,
                emvTagDfName
            )
        )

    }

    private fun isTag82Byte1Bit3(tag82String: String): Boolean {

        if (!tag82String.isNullOrEmpty()) {
            val tag82InformationArray = getTagInformation(tag82String)
            val tag82Byte1String = tag82InformationArray[tag82InformationArray.size - 2] ?: ""
            val tag82Byte1Int = DataConversionUtil.hexToInt(tag82Byte1String) ?: 0
            var tag82Byte1Binary = DataConversionUtil.intToBinary(tag82Byte1Int, 8)
            val tag82Byte1Bit3 = tag82Byte1Binary[tag82Byte1Binary.length - 3].toString()
            val result = tag82Byte1Bit3 == "1"
            return tag82Byte1Bit3 == "1"
        }
        return false
    }

    private fun getTagInformation(tag: String): Array<String> {
        val tagBytesCountHex = if (tag.length >= 4) tag.substring(2, 4) else ""
        val tagBytesCountInt = DataConversionUtil.hexToInt(tagBytesCountHex) ?: 0
        val tagInformationArray = Array<String>(tagBytesCountInt) { "" }

        for (i in 2 until tagBytesCountInt + 2) {
            val byte = if (tag.length >= (i * 2) + 2) tag.substring(i * 2, (i * 2) + 2) else ""
            tagInformationArray[i - 2] = byte
        }

        return tagInformationArray
    }

    private fun getTagInformationForOddNumber(tag: String): Array<String> {
        val tagBytesCountHex = if (tag.length >= 4) tag.substring(2, 4) else ""
        val tagBytesCountInt = DataConversionUtil.hexToInt(tagBytesCountHex) ?: 0
        val tagInformationArray = ArrayList<String>()

        for (i in 2 until tagBytesCountInt + 2) {
            val startIndex = i * 2
            if (startIndex < tag.length) {
                val endIndex = minOf(startIndex + 2, tag.length)
                val byte = tag.substring(startIndex, endIndex)
                tagInformationArray.add(byte)
            }
        }

        return tagInformationArray.toTypedArray()
    }

    override suspend fun detectCard(
        fallback: Boolean?,
        idEmvList: List<IdEmv>?,
        certEmvList: List<CertEmv>?,
    ): ReadCardResponse {
        this.idEmvList = idEmvList
        this.certEmvList = certEmvList
        settingCertificationAndAid()
        val param = Bundle()
        if(fallback == true) {
            param.putBoolean(EMVData.SUPPORT_MAG_CARD, true)
            param.putBoolean(EMVData.SUPPORT_IC_CARD, false)
            param.putBoolean(EMVData.SUPPORT_RF_CARD, false)
        }else{
            param.putBoolean(EMVData.SUPPORT_MAG_CARD, true)
            param.putBoolean(EMVData.SUPPORT_IC_CARD, true)
            param.putBoolean(EMVData.SUPPORT_RF_CARD, true)
        }

        return suspendCancellableCoroutine { cont ->
            emv?.searchCard(param, TIMEOUT,
                object : SearchCardListener.Stub() {
                    override fun onCardPass(cardType: Int) {
                        isContactlessCard = true
                        //val ret = emv?.startEMV(emvOption?.toBundle(), emvEventHandler)
                        mExistSlot = CardSlotType.RF
                        val cardInfo = ReadCardResponse.CardInfo(
                            ReadCardResponse.CARD_INFO,
                            null,
                            CardSlotType.RF,
                            null,
                            true,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null
                        )
                        emv?.stopSearch()
                        if (cont.isActive) {
                            cont.resumeWith(Result.success(cardInfo))
                        }
                    }

                    override fun onCardInsert() {
                        mExistSlot = CardSlotType.ICC1
                        val cardInfo = ReadCardResponse.CardInfo(
                            ReadCardResponse.CARD_INFO,
                            null,
                            CardSlotType.ICC1,
                            null,
                            true,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null
                        )
                        emv?.stopSearch()
                        if (cont.isActive) {
                            cont.resumeWith(Result.success(cardInfo))
                        }
                    }

                    override fun onCardSwiped(track: Bundle) {
                        track.getIntArray(EMVData.TRACK_STATES)

                        mExistSlot = CardSlotType.SWIPE

                        val cardInfo = ReadCardResponse.CardInfo(
                            ReadCardResponse.CARD_INFO,
                            track.getString(EMVData.PAN),
                            CardSlotType.SWIPE,
                            null,
                            false,
                            null,
                            track.getString(EMVData.TRACK1),
                            track.getString(EMVData.TRACK2),
                            track.getString(EMVData.TRACK3),
                            track.getString(EMVData.EXPIRED_DATE),
                            track.getString(EMVData.SERVICE_CODE),
                            null,
                            null,
                            null
                        )
                        emv?.stopSearch()
                        if (cont.isActive) {
                            cont.resumeWith(Result.success(cardInfo))
                        }
                    }

                    override fun onTimeout() {}

                    override fun onError(code: Int, message: String) {
                        if (cont.isActive) {
                            cont.resumeWith(Result.success(ReadCardResponse.ReadFailed))
                        }
                    }
                }
            )
        }
    }

    override suspend fun getRSAKey(): String {
        deviceManager = this.getDeviceManager()
        serialPort = this.getSerialPort(com.usdk.apiservice.aidl.serialport.DeviceName.USBD)
        val serialNum = deviceManager?.deviceInfo?.serialNo
        val frame = serialNum?.let { getFrameToSend(it) }
        val retOpen: Int? = serialPort?.open()
        if (retOpen == SerialPortError.SUCCESS){
            var ret: Int? = serialPort?.init(4, 78, 8)
            if (ret == SerialPortError.SUCCESS) {

                val retLoong = frame?.size?.let { serialPort?.write(frame, it) }

                if (retLoong != null) {
                    if (retLoong > 0) {
                        if (listenConnectionStatus() == CONFIRMATION_CODE) {
                            return listenForKey()
                        }
                    }else{
                        return "Connecting Serial FAIL"
                    }
                } else{
                    return "Connecting Serial FAIL"
                }
            } else {
                return "Connecting Serial FAIL"
            }
        } else {
            return "Connecting Serial FAIL"
        }


        serialPort?.close()
        return "Connecting Serial Disconnect"
    }

    override fun getDeviceSerial(): String {
        deviceManager = this.getDeviceManager()
        return deviceManager?.deviceInfo?.serialNo!!
    }

    override fun getEntryModeType(): String {
        return when (mExistSlot){
            CardSlotType.ICC1 -> EntryModeType.CHIP.toString()
            CardSlotType.RF -> EntryModeType.CONTACTLESS.toString()
            else -> EntryModeType.BANDCARD.toString()
        }
    }

    private fun getCodeEntryModeType(): String {
        return when (mExistSlot){
            CardSlotType.ICC1 -> entryModeContactCode
            CardSlotType.RF -> entryModeContactlessCode
            else -> entryModeBandCode
        }
    }

    override fun isKernelAvailableState(kernelAvailableCallback: (callbackObjectType: KernelAvailableStateCallBackObjectSDK) -> Unit) {
        this.kernelAvailableCallback =  kernelAvailableCallback
        isKernelFunctionCall = true
        if(this.kernelRunState && this.kernelAvailableState) kernelAvailableCallback.invoke(
            KernelAvailableStateCallBackObjectSDK.AvailableKernel(
                KernelAvailableStateCallBackObjectSDK.AVAILABLE_KERNEL))
    }

    override fun getVersionSDK(): String {
        return "" //BuildConfig.VERSION_SDK
    }

    @Throws(IllegalStateException::class)
    fun getEMV(): UEMV? {
        val iBinder: IBinder = object : IBinderCreator() {
            override fun create(): IBinder {
                return deviceService?.emv!!
            }
        }.start()
        return UEMV.Stub.asInterface(iBinder)
    }

    @Throws(java.lang.IllegalStateException::class)
    fun getPinPad(kapId: KAPId?, keySystem: Int, deviceName: String?): UPinpad? {
        val iBinder: IBinder = object : IBinderCreator() {
            override fun create(): IBinder {
                return deviceService!!.getPinpad(kapId, keySystem, deviceName)
            }
        }.start()
        return UPinpad.Stub.asInterface(iBinder)
    }


    internal abstract class IBinderCreator {
        @Throws(java.lang.IllegalStateException::class)
        fun start(): IBinder {
            return create()
        }

        @Throws(RemoteException::class)
        abstract fun create(): IBinder
    }

    @Throws(java.lang.IllegalStateException::class)
    private fun register(useEpayModule: Boolean) {
        try {
            val param = Bundle()
            param.putBoolean(DeviceServiceData.USE_EPAY_MODULE, useEpayModule)
            deviceService?.register(param, Binder())
            kernelAvailableState = true
            if(isKernelFunctionCall) kernelAvailableCallback.invoke(
                KernelAvailableStateCallBackObjectSDK.AvailableKernel(
                    KernelAvailableStateCallBackObjectSDK.AVAILABLE_KERNEL))
        } catch (e: RemoteException) {
            e.printStackTrace()
            throw java.lang.IllegalStateException(e.message)
        } catch (e: SecurityException) {
            e.printStackTrace()
            throw java.lang.IllegalStateException(e.message)
        }
    }

    @Throws(java.lang.IllegalStateException::class)
    fun unregister() {
        try {
            deviceService?.unregister(null)
        } catch (e: RemoteException) {
            e.printStackTrace()
            throw java.lang.IllegalStateException(e.message)
        }
    }


    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

        try {
            deviceService = UDeviceService.Stub.asInterface(service)
            register(true)
            emv = getEMV()
            kernelRunState = emv != null

        } catch (e: java.lang.Exception) {
            kernelRunState = false
        }

    }

    private fun getKernelVersion() {
        try {
            val authInfo = StringValue()
            /*if (ret == EMVError.SUCCESS) {
                Log.d("TAG-1", "EMV kernel version: " + authInfo.data)
                Log.d("TAG-1", "EMV: " + DeviceHelper.me().getEMV().toString())
                //outputBlackText("EMV kernel version: " + authInfo.data)
            } else {
                //outputRedText("EMV kernel version: fail, ret = $ret")
            }*/
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }


    override fun onServiceDisconnected(name: ComponentName?) {
        kernelRunState = false
        /*Log.e(DeviceHelper.TAG, "=> onServiceDisconnected")
        deviceService = null
        isBinded = false
        DeviceServiceLimited.unbind(context)
        bindService()*/
    }

    override suspend fun setIdEmvData(idEmvs: List<IdEmv>) {
        setAidEmv(idEmvs)
    }

    private fun setAidEmv(idEmvs: List<IdEmv>){

        var aids = emptyArray<String>()
        idEmvList = idEmvs
        idEmvList?.toTypedArray()
        idEmvList?.forEach {
            aids += it.applicationId.toString()
        }

        for (aid in aids) {
            val ret = emv?.manageAID(ActionFlag.ADD, aid, true)
            //emv?.setDOL(ActionFlag.ADD, aid.aidDdol)
        }
    }

    private fun setCertEmvDataSecond(certEmvs: List<CertEmv>) {
        emv?.manageCAPubKey(ActionFlag.CLEAR, null)

        certEmvs.forEach { item ->
            val capKey = CAPublicKey()
            capKey.rid = item.publicKeyRid?.let { TefUtil.hexString2ByteArray(it) } ?: byteArrayOf()
            capKey.index = item.publicKeyIndex?.let { TefUtil.hexString2ByteArray(it)[0] } ?: 0x00
            capKey.expDate = DateTimeUtil.formatDateTimeForReceiptSDK(
                item.publicKeyExpiration.toString()
            ).let { TefUtil.hexString2ByteArray(it) }
            capKey.mod = item.publicKey?.let { TefUtil.hexString2ByteArray(it) } ?: byteArrayOf()

            capKey.exp = String.format("%02X", item.publicKeyExponent).let {
                TefUtil.hexString2ByteArray(it)
            }

            item.publicKeyHash?.let { hash ->
                if (hash.isNotEmpty()) {
                    capKey.hash = TefUtil.hexString2ByteArray(hash)
                    capKey.hashFlag = 0x01.toByte()
                } else {
                    capKey.hashFlag = 0x00.toByte()
                }
            } ?: run {
                capKey.hashFlag = 0x00.toByte()
            }

            val ret = emv?.manageCAPubKey(ActionFlag.ADD, capKey)
        }
    }

    override suspend fun setCertEmvData(certEmvs: List<CertEmv>) {
        setCertEmvDataSecond(certEmvs = certEmvs)
    }

    @Throws(java.lang.IllegalStateException::class)
    fun getDeviceManager(): UDeviceManager? {
        val iBinder = object : IBinderCreator() {
            @Throws(RemoteException::class)
            override fun create(): IBinder {
                return deviceService!!.deviceManager
            }
        }.start()
        return UDeviceManager.Stub.asInterface(iBinder)
    }

    @Throws(java.lang.IllegalStateException::class)
    fun getSerialPort(deviceName: String?): USerialPort? {
        val iBinder = object : IBinderCreator() {
            @Throws(RemoteException::class)
            override fun create(): IBinder {
                return deviceService!!.getSerialPort(deviceName)
            }
        }.start()
        return USerialPort.Stub.asInterface(iBinder)
    }

    @Throws(java.lang.IllegalStateException::class)
    fun getBeeper(): UBeeper? {
        val iBinder: IBinder = object : IBinderCreator() {
            @Throws(RemoteException::class)
            override fun create(): IBinder {
                return deviceService!!.beeper
            }
        }.start()
        return UBeeper.Stub.asInterface(iBinder)
    }

    @Throws(java.lang.IllegalStateException::class)
    fun getLed(dviceName: String?): ULed? {
        val iBinder: IBinder = object : IBinderCreator() {
            @Throws(RemoteException::class)
            override fun create(): IBinder {
                val param = Bundle()
                param.putString(DeviceServiceData.RF_DEVICE_NAME, dviceName)
                return deviceService!!.getLed(param)
            }
        }.start()
        return ULed.Stub.asInterface(iBinder)
    }

    @Throws(java.lang.IllegalStateException::class)
    fun getICCpuReader(): UICCpuReader? {
        val iBinder: IBinder = object : IBinderCreator() {
            @Throws(RemoteException::class)
            override fun create(): IBinder {
                return deviceService!!.getICReader(DriverID.ICCPU, null)
            }
        }.start()
        return UICCpuReader.Stub.asInterface(iBinder)
    }

    private fun activeBeepAndLed() {
        val beeper: UBeeper? = getBeeper()
        val ledDriver : ULed?  = getLed(RF_DEVICE_NAME)
        beeper?.startBeepNew(3000, BEEPER_TIME)

        ledDriver?.turnOn(Light.GREEN)
        Timer("SettingUp", false).schedule(1000) {
            ledDriver?.turnOff(Light.GREEN)
        }
    }

    private fun getFrameToSend(serialNum: String): ByteArray {
        val serialNumWithPad = serialNum.padStart(
            MAX_SERIAL_NUMBER_DIGITS,
            ZERO_PADDING
        )
        val serialLength = (serialNumWithPad.length).toString()
        val serialLengthWithPad = serialLength.padStart(
            LENGTH,
            ZERO_PADDING
        )

        val lengthPlusSerial = StringBuilder()
            .append(serialLengthWithPad)
            .append(serialNumWithPad)
            .toString()

        val lengthPlusSerialFrame = BytesUtil.string2ASCIIByteArray(lengthPlusSerial)
        val frameForXorCalculation = lengthPlusSerialFrame.plus(END_FRAME)
        val xorResult = SerialCommUtil.calculateFrameXor(frameForXorCalculation)

        return START_FRAME
            .plus(frameForXorCalculation)
            .plus(xorResult)
    }

    private fun listenConnectionStatus(): Byte {
        val rev = ByteArray(CONFIRMATION_CODE_LENGTH)
        var ret: Int?
        var counter = 0
        serialPort?.clearInputBuffer()
        do {
            ret = serialPort?.read(rev, 1000)
            counter += 1000
        } while (serialPort?.isBufferEmpty(true) == true && counter < TIME_OUT.toInt() && ret == 0)

        if (ret != null) {
            if (ret > 0) {
                return rev[0]
            }
        }else{
            serialPort?.close()
            return -1
        }
        return rev[0]
    }
    private fun listenForKey(): String {
        val bytesValue = ByteArray(BUFFER_MAX_SIZE)
        var counter = 0
        var ret: Int?
        serialPort?.clearInputBuffer()
        do {
            ret = serialPort?.read(bytesValue, 1000)
            counter += 1000
        } while (serialPort?.isBufferEmpty(true) == true && counter < TIME_OUT.toInt() && ret == 0)

        if (ret != null) {
            if (ret > 0) {
                serialPort?.close()
                return getKeyComponents(bytesValue)
            }else{
                return "Error"
            }
        }

        return "Error"
    }


    private fun getKeyComponents(rev: ByteArray): String {
        keyComponents = rev

        return StringBuilder()
            .append(getPrime1())
            .append(Constants.FILE_SEPARATOR)
            .append(getPrime2())
            .append(Constants.FILE_SEPARATOR)
            .append(getExponent1())
            .append(Constants.FILE_SEPARATOR)
            .append(getExponent2())
            .append(Constants.FILE_SEPARATOR)
            .append(getCoefficient())
            .append(Constants.FILE_SEPARATOR)
            .append(getEncryptedMessage())
            .toString()
    }

    private fun getPrime1(): String? {
        return keyComponents?.run {
            val prime1LengthByteArray = this.sliceArray(
                PRIME1_LENGTH_INITIAL_POSITION until PRIME1_LENGTH_FINAL_POSITION
            )

            val prime1Length: Int = String(prime1LengthByteArray).toInt()

            val prime1 = this.sliceArray(
                PRIME1_INITIAL_POSITION until PRIME1_INITIAL_POSITION + prime1Length
            )

            positionHelper = PRIME1_INITIAL_POSITION + prime1Length

            String(prime1)
        }
    }

    private fun getPrime2(): String? {
        return keyComponents?.run {
            val prime2InitialPosition = positionHelper + LENGTH_FIELDS_SIZE
            val prime2LengthByteArray = this.sliceArray(
                positionHelper until prime2InitialPosition
            )

            val prime2Length: Int = String(prime2LengthByteArray).toInt()

            val prime2 = this.sliceArray(
                prime2InitialPosition until prime2InitialPosition + prime2Length
            )

            positionHelper = prime2InitialPosition + prime2Length

            String(prime2)
        }
    }

    private fun getExponent1(): String? {
        return keyComponents?.run {
            val exp1InitialPosition = positionHelper + LENGTH_FIELDS_SIZE
            val exp1LengthByteArray = this.sliceArray(
                positionHelper until exp1InitialPosition
            )

            val exp1Length: Int = String(exp1LengthByteArray).toInt()

            val exp1 = this.sliceArray(
                exp1InitialPosition until exp1InitialPosition + exp1Length
            )

            positionHelper = exp1InitialPosition + exp1Length

            String(exp1)
        }
    }

    private fun getExponent2(): String? {
        return keyComponents?.run {
            val exp2InitialPosition = positionHelper + LENGTH_FIELDS_SIZE
            val exp2LengthByteArray = this.sliceArray(
                positionHelper until exp2InitialPosition
            )

            val exp2Length: Int = String(exp2LengthByteArray).toInt()

            val exp2 = this.sliceArray(
                exp2InitialPosition until exp2InitialPosition + exp2Length
            )

            positionHelper = exp2InitialPosition + exp2Length

            String(exp2)
        }
    }

    private fun getCoefficient(): String? {
        return keyComponents?.run {
            val coefInitialPosition = positionHelper + LENGTH_FIELDS_SIZE
            val coefLengthByteArray = this.sliceArray(
                positionHelper until coefInitialPosition
            )

            val exp2Length = String(coefLengthByteArray).toInt()

            val exp2 = this.sliceArray(
                coefInitialPosition until coefInitialPosition + exp2Length
            )

            positionHelper = coefInitialPosition + exp2Length

            String(exp2)
        }
    }

    private fun getEncryptedMessage(): String? {
        return keyComponents?.run {
            val encMessInitialPosition = positionHelper + LENGTH_FIELDS_SIZE
            val encMessLengthByteArray = this.sliceArray(
                positionHelper until encMessInitialPosition
            )

            val exp2Length = String(encMessLengthByteArray).toInt()

            val exp2 = this.sliceArray(
                encMessInitialPosition until encMessInitialPosition + exp2Length
            )

            positionHelper = encMessInitialPosition + exp2Length

            String(exp2)
        }
    }

    private fun getCountryCode(countryTag5F28: String, countryTag9F5A: String): String {
        return if (countryTag5F28.isEmpty()) {
            get5F28EmulationFrom9F5A(countryTag9F5A)
        } else {
            "5F2802$countryTag5F28"
        }
    }

    private fun get5F28EmulationFrom9F5A(countryTag9F5A: String): String {
        return if (countryTag9F5A.isEmpty()) {
            TAG_5F28_DEFAULT
        } else {
            val tag9F5AInformationArray = getTagInformation(countryTag9F5A)
            "5F2802" + tag9F5AInformationArray[2] + tag9F5AInformationArray[3]
        }
    }

    private fun String.isVisa(): Boolean {
        return this.contains("A000000003")
    }

    private fun String.isMaster(): Boolean {
        return this.contains("A000000004")
    }

    private fun String.isMaestro(): Boolean {
        return this.contains("A0000000043060")
    }

    private fun String.isAmex(): Boolean {
        return this.contains("A000000025")
    }

    private fun isContactLess(): Boolean {
        return mExistSlot == CardSlotType.RF
    }

    private fun takeDataFromOnReadRecord(): Boolean {
        return (bestAidFound.isMaster() || bestAidFound.isAmex() && mExistSlot == CardSlotType.RF)
    }

    private fun takeDataFromOnSendOut(): Boolean {
        return (bestAidFound.isMaster().not() || bestAidFound.isAmex() && mExistSlot != CardSlotType.RF)
    }
}
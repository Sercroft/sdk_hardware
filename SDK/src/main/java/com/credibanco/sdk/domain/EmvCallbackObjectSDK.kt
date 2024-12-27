package com.credibanco.sdk.domain

import android.os.Parcel
import android.os.Parcelable
import com.credibanco.sdk.domain.dto.CandidateAppInfo
import com.google.gson.annotations.SerializedName

sealed class EmvCallbackObjectSDK {

    companion object {
        const val PIN_BLOCK_CALLBACK = "PIN_BLOCK_CALLBACK"
        const val ENTRY_MODE_CALLBACK = "ENTRY_MODE_CALLBACK"
        const val BIN_BY_RANGE_CALLBACK = "BIN_BY_RANGE_CALLBACK"
        const val EMV_PIN_PROCESS_CALLBACK = "EMV_PIN_PROCESS_CALLBACK"
        const val EMV_ONLINE_REQUEST_CALLBACK = "EMV_ONLINE_REQUEST_CALLBACK"
        const val EMV_FINISHED_CALLBACK = "EMV_FINISHED_CALLBACK"
        const val EMV_SELECT_APP_CALLBACK = "EMV_SELECT_APP_CALLBACK"
        const val ERROR_CALLBACK = "ERROR_CALLBAK"
    }

    data class PinSDKBlockCallback(
        @SerializedName("callBacktype")
        val callBacktype: String? = PIN_BLOCK_CALLBACK,
        @SerializedName("pinBlockTypeCard")
        val pinBlockTypeCard: String?
    ) : EmvCallbackObjectSDK(), Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(callBacktype)
            parcel.writeString(pinBlockTypeCard)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<PinSDKBlockCallback> {
            override fun createFromParcel(parcel: Parcel): PinSDKBlockCallback {
                return PinSDKBlockCallback(parcel)
            }

            override fun newArray(size: Int): Array<PinSDKBlockCallback?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class EntrySDKModeCallback(
        @SerializedName("callBacktype")
        val callBacktype: String? = ENTRY_MODE_CALLBACK,
        @SerializedName("entryModeCard")
        val entryModeCard: String?
    ) : EmvCallbackObjectSDK(), Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(callBacktype)
            parcel.writeString(entryModeCard)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<EntrySDKModeCallback> {
            override fun createFromParcel(parcel: Parcel): EntrySDKModeCallback {
                return EntrySDKModeCallback(parcel)
            }

            override fun newArray(size: Int): Array<EntrySDKModeCallback?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class BinByRangeCallbackSDK(
        @SerializedName("callBacktype")
        val callBacktype: String? = BIN_BY_RANGE_CALLBACK,
        @SerializedName("cardNumber")
        val cardNumber: String?,
        @SerializedName("countryCode")
        val countryCode: String?,
        @SerializedName("franchiseCard")
        val franchiseCard: String?,
        @SerializedName("arpcValidationIsRequiredForCard")
        val arpcValidationIsRequiredForCard: Boolean = false,
        @SerializedName("emvTagDfName")
        val emvTagDfName: String?
    ) : EmvCallbackObjectSDK(), Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(callBacktype)
            parcel.writeString(cardNumber)
            parcel.writeString(countryCode)
            parcel.writeString(franchiseCard)
            parcel.writeByte(if (arpcValidationIsRequiredForCard) 1 else 0)
            parcel.writeString(emvTagDfName)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<BinByRangeCallbackSDK> {
            override fun createFromParcel(parcel: Parcel): BinByRangeCallbackSDK {
                return BinByRangeCallbackSDK(parcel)
            }

            override fun newArray(size: Int): Array<BinByRangeCallbackSDK?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class EmvPinProcessCallbackSDK(
        @SerializedName("callBacktype")
        val callBacktype: String? = EMV_PIN_PROCESS_CALLBACK,
        @SerializedName("isOnlinePin")
        val isOnlinePin: Boolean,
        @SerializedName("leftTimes")
        val leftTimes: Int,
        @SerializedName("cardNumber")
        val cardNumber: String?
    ) : EmvCallbackObjectSDK(), Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readInt(),
            parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(callBacktype)
            parcel.writeByte(if (isOnlinePin) 1 else 0)
            parcel.writeInt(leftTimes)
            parcel.writeString(cardNumber)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<EmvPinProcessCallbackSDK> {
            override fun createFromParcel(parcel: Parcel): EmvPinProcessCallbackSDK {
                return EmvPinProcessCallbackSDK(parcel)
            }

            override fun newArray(size: Int): Array<EmvPinProcessCallbackSDK?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class EmvOnlineRequestCallbackSDK(
        @SerializedName("callBacktype")
        val callBacktype: String? = EMV_ONLINE_REQUEST_CALLBACK,
        @SerializedName("pinBlock")
        val pinBlock: String?,
        @SerializedName("track2")
        val track2: String?,
        @SerializedName("emvTag57")
        val emvTag57: String?,
        @SerializedName("emvTagCryptogram")
        val emvTagCryptogram: String?,
        @SerializedName("emvTagTsi")
        val emvTagTsi: String?,
        @SerializedName("emvTagTvr")
        val emvTagTvr: String?,
        @SerializedName("emvTagDfName")
        val emvTagDfName: String?,
        @SerializedName("emvTagCardHolderName")
        val emvTagCardHolderName: String?,
        @SerializedName("emv")
        val emv: String?,
        @SerializedName("countryCode")
        val countryCode: String?,
        @SerializedName("shouldRequestAQPC")
        val shouldRequestAQPC: Boolean?,
        @SerializedName("shouldAskAccountType")
        val shouldAskAccountType: Boolean = false,
        @SerializedName("shouldInfoEmvForDcc")
        val shouldInfoEmvForDcc: Boolean = false,
        @SerializedName("isDcc")
        val isDcc: Boolean = false
    ) : EmvCallbackObjectSDK(), Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(callBacktype)
            parcel.writeString(pinBlock)
            parcel.writeString(track2)
            parcel.writeString(emvTag57)
            parcel.writeString(emvTagCryptogram)
            parcel.writeString(emvTagTsi)
            parcel.writeString(emvTagTvr)
            parcel.writeString(emvTagDfName)
            parcel.writeString(emvTagCardHolderName)
            parcel.writeString(emv)
            parcel.writeString(countryCode)
            parcel.writeValue(shouldRequestAQPC)
            parcel.writeByte(if (shouldAskAccountType) 1 else 0)
            parcel.writeByte(if (shouldInfoEmvForDcc) 1 else 0)
            parcel.writeByte(if (isDcc) 1 else 0)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<EmvOnlineRequestCallbackSDK> {
            override fun createFromParcel(parcel: Parcel): EmvOnlineRequestCallbackSDK {
                return EmvOnlineRequestCallbackSDK(parcel)
            }

            override fun newArray(size: Int): Array<EmvOnlineRequestCallbackSDK?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class EmvSDKFinishedCallback(
        @SerializedName("callBacktype")
        val callBacktype: String? = EMV_FINISHED_CALLBACK,
        @SerializedName("responseCode")
        val responseCode: Int
    ) : EmvCallbackObjectSDK(), Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(callBacktype)
            parcel.writeInt(responseCode)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<EmvSDKFinishedCallback> {
            override fun createFromParcel(parcel: Parcel): EmvSDKFinishedCallback {
                return EmvSDKFinishedCallback(parcel)
            }

            override fun newArray(size: Int): Array<EmvSDKFinishedCallback?> {
                return arrayOfNulls(size)
            }
        }
    }


    data class EmvSelectAppCallbackSDK(
        @SerializedName("callBacktype")
        val callBacktype: String? = EMV_SELECT_APP_CALLBACK,
        @SerializedName("appNameList")
        val appNameList: List<String>?,
        @SerializedName("appInfoList")
        val appInfoList: List<CandidateAppInfo>,
        @SerializedName("isFirstSelect")
        val isFirstSelect: Boolean
    ) : EmvCallbackObjectSDK(), Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.createStringArrayList(),
            TODO("appInfoList"),
            parcel.readByte() != 0.toByte()
        ) {}

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(callBacktype)
            parcel.writeStringList(appNameList)
            parcel.writeByte(if (isFirstSelect) 1 else 0)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<EmvSelectAppCallbackSDK> {
            override fun createFromParcel(parcel: Parcel): EmvSelectAppCallbackSDK {
                return EmvSelectAppCallbackSDK(parcel)
            }

            override fun newArray(size: Int): Array<EmvSelectAppCallbackSDK?> {
                return arrayOfNulls(size)
            }
        }
    }


    data class Error(
        @SerializedName("callBacktype")
        val callBacktype: String? = ERROR_CALLBACK
    ) : EmvCallbackObjectSDK(), Parcelable {
        constructor(parcel: Parcel) : this(parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(callBacktype)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Error> {
            override fun createFromParcel(parcel: Parcel): Error {
                return Error(parcel)
            }

            override fun newArray(size: Int): Array<Error?> {
                return arrayOfNulls(size)
            }
        }
    }
}

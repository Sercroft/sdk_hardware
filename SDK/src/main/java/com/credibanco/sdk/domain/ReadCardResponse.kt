package com.credibanco.sdk.domain

import android.os.Parcel
import android.os.Parcelable
import com.credibanco.sdk.domain.type.CardSlotType
import com.credibanco.sdk.domain.type.RfCardType
import com.credibanco.sdk.domain.type.TrackErrorType
import com.google.gson.annotations.SerializedName

sealed class ReadCardResponse() {

    companion object{
        const val CARD_INFO = "CARD_INFO"
        const val MULTIPLE_CARDS = "MULTIPLE_CARDS"
        const val READ_FAILED = "READ_FAILED"
    }
    data class CardInfo(
        @SerializedName("callBacktype")
        val callBacktype: String? = CARD_INFO,
        @SerializedName("cardNumber")
        var cardNumber: String?,
        @SerializedName("cardSlotType")
        val cardSlotType: CardSlotType?,
        @SerializedName("rfCardType")
        val rfCardType: RfCardType?,
        @SerializedName("isChipCard")
        val isChipCard: Boolean?,
        @SerializedName("cardSerialNumber")
        val cardSerialNumber: String?,
        @SerializedName("track1")
        val track1: String?,
        @SerializedName("track2")
        val track2: String?,
        @SerializedName("track3")
        val track3: String?,
        @SerializedName("expiredDate")
        val expiredDate: String?,
        @SerializedName("serviceCode")
        val serviceCode: String?,
        @SerializedName("trackErrorNum1")
        val trackErrorNum1: TrackErrorType?,
        @SerializedName("trackErrorNum2")
        val trackErrorNum2: TrackErrorType?,
        @SerializedName("trackErrorNum3")
        val trackErrorNum3: TrackErrorType?
    ) : ReadCardResponse(), Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            TODO("cardSlotType"),
            TODO("rfCardType"),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            TODO("trackErrorNum1"),
            TODO("trackErrorNum2"),
            TODO("trackErrorNum3")
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(callBacktype)
            parcel.writeString(cardNumber)
            parcel.writeValue(isChipCard)
            parcel.writeString(cardSerialNumber)
            parcel.writeString(track1)
            parcel.writeString(track2)
            parcel.writeString(track3)
            parcel.writeString(expiredDate)
            parcel.writeString(serviceCode)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<CardInfo> {
            override fun createFromParcel(parcel: Parcel): CardInfo {
                return CardInfo(parcel)
            }

            override fun newArray(size: Int): Array<CardInfo?> {
                return arrayOfNulls(size)
            }
        }

    }

    object ReadFailed : ReadCardResponse(){
        @SerializedName("callBacktype")
        val callBacktype: String = READ_FAILED
    }
    object MultipleCards : ReadCardResponse(){
        @SerializedName("callBacktype")
        val callBacktype: String = MULTIPLE_CARDS
    }
}
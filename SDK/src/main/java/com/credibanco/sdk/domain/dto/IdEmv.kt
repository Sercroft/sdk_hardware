package com.credibanco.sdk.domain.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class IdEmv(
    @SerializedName( "id")
    val id: Int,

    @SerializedName( "indexGroup")
    val indexGroup: Int,

    @SerializedName( "indexAid")
    val indexAid: Int,

    @SerializedName( "applicationIdLength")
    val applicationIdLength: Int,

    @SerializedName( "applicationId")
    val applicationId: String?,

    @SerializedName( "aidFlootLid")
    val aidFlootLid: Int,

    @SerializedName( "aidVersionNumbers1")
    val aidVersionNumbers1: String?,

    @SerializedName( "aidVersionNumbers2")
    val aidVersionNumbers2: Int,

    @SerializedName( "aidVersionNumbers3")
    val aidVersionNumbers3: Int,

    @SerializedName( "aidTdol")
    val aidTdol: String?,

    @SerializedName( "aidDdol")
    val aidDdol: String?,

    @SerializedName( "offLineResponseCodesApproved")
    val offLineResponseCodesApproved: String?,

    @SerializedName( "offLineResponseCodesDeclined")
    val offLineResponseCodesDeclined: String?,

    @SerializedName( "unableResponseCodesApproved")
    val unableResponseCodesApproved: String?,

    @SerializedName( "unableResponseCodesApprovedDeclined")
    val unableResponseCodesApprovedDeclined: String?,

    @SerializedName( "skipEmvProcessing")
    val skipEmvProcessing: Int,

    @SerializedName( "useAdditionalTags")
    val useAdditionalTags: Int,

    @SerializedName( "easyEntryAllowed")
    val easyEntryAllowed: Int,

    @SerializedName( "goOnLineIfAar")
    val goOnLineIfAar: Int,

    @SerializedName( "specialProcessingArqc")
    val specialProcessingArqc: Int,

    @SerializedName( "userIssuerFlootLimit")
    val userIssuerFlootLimit: Int,

    @SerializedName( "selectAidIfExactMatch")
    val selectAidIfExactMatch: Int,

    @SerializedName( "targetPercentage")
    val targetPercentage: Int,

    @SerializedName( "maxTargetPercentage")
    val maxTargetPercentage: Int,

    @SerializedName( "thresholdAmount")
    val thresholdAmount: Int,

    @SerializedName( "tacDenial")
    val tacDenial: Int,

    @SerializedName( "tacDefault")
    val tacDefault: String?,

    @SerializedName( "tacOnline")
    val tacOnline: String?,

    @SerializedName( "transactionCategoryCode")
    val transactionCategoryCode: String?,

    @SerializedName( "merchantCategoryCode")
    val merchantCategoryCode: Int,

    @SerializedName( "terminalAdditionalCapabilities")
    val terminalAdditionalCapabilities: String?,

    @SerializedName( "terminalCapabilities")
    val terminalCapabilities: String?,

    @SerializedName( "terminalCountryCode")
    val terminalCountryCode: Int,

    @SerializedName( "terminalCurrencyCode")
    val terminalCurrencyCode: Int,

    @SerializedName( "terminalType")
    val terminalType: Int,

    @SerializedName( "ctless9f66")
    val ctless9f66: String?,

    @SerializedName( "ctlessTermCap")
    val ctlessTermCap: String? = null,

    @SerializedName( "ctlessAddTermCap")
    val ctlessAddTermCap: String? = null,

    @SerializedName( "ctlessLowerFloorLimit")
    val ctlessLowerFloorLimit: Int,

    @SerializedName( "ctlessCvmFloorLimit")
    val ctlessCvmFloorLimit: Int,

    @SerializedName( "ctlessUpperFloorLimit")
    val ctlessUpperFloorLimit: Int,

    @SerializedName( "ctlessTagDenial")
    val ctlessTagDenial: Int,

    @SerializedName( "ctlessTagOnline")
    val ctlessTagOnline: String? = null,

    @SerializedName( "ctlessTagDefault")
    val ctlessTagDefault: String? = null,

    @SerializedName( "ctlessNodcvLimit")
    val ctlessNodcvLimit: Int,

    @SerializedName( "ctlessDcvLimit")
    val ctlessDcvLimit: Int,

    @SerializedName( "ctlessConfigOptions")
    val ctlessConfigOptions: Int,

    @SerializedName( "ctlessTermRiskMngt")
    val ctlessTermRiskMngt: String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(indexGroup)
        parcel.writeInt(indexAid)
        parcel.writeInt(applicationIdLength)
        parcel.writeString(applicationId)
        parcel.writeInt(aidFlootLid)
        parcel.writeString(aidVersionNumbers1)
        parcel.writeInt(aidVersionNumbers2)
        parcel.writeInt(aidVersionNumbers3)
        parcel.writeString(aidTdol)
        parcel.writeString(aidDdol)
        parcel.writeString(offLineResponseCodesApproved)
        parcel.writeString(offLineResponseCodesDeclined)
        parcel.writeString(unableResponseCodesApproved)
        parcel.writeString(unableResponseCodesApprovedDeclined)
        parcel.writeInt(skipEmvProcessing)
        parcel.writeInt(useAdditionalTags)
        parcel.writeInt(easyEntryAllowed)
        parcel.writeInt(goOnLineIfAar)
        parcel.writeInt(specialProcessingArqc)
        parcel.writeInt(userIssuerFlootLimit)
        parcel.writeInt(selectAidIfExactMatch)
        parcel.writeInt(targetPercentage)
        parcel.writeInt(maxTargetPercentage)
        parcel.writeInt(thresholdAmount)
        parcel.writeInt(tacDenial)
        parcel.writeString(tacDefault)
        parcel.writeString(tacOnline)
        parcel.writeString(transactionCategoryCode)
        parcel.writeInt(merchantCategoryCode)
        parcel.writeString(terminalAdditionalCapabilities)
        parcel.writeString(terminalCapabilities)
        parcel.writeInt(terminalCountryCode)
        parcel.writeInt(terminalCurrencyCode)
        parcel.writeInt(terminalType)
        parcel.writeString(ctless9f66)
        parcel.writeString(ctlessTermCap)
        parcel.writeString(ctlessAddTermCap)
        parcel.writeInt(ctlessLowerFloorLimit)
        parcel.writeInt(ctlessCvmFloorLimit)
        parcel.writeInt(ctlessUpperFloorLimit)
        parcel.writeInt(ctlessTagDenial)
        parcel.writeString(ctlessTagOnline)
        parcel.writeString(ctlessTagDefault)
        parcel.writeInt(ctlessNodcvLimit)
        parcel.writeInt(ctlessDcvLimit)
        parcel.writeInt(ctlessConfigOptions)
        parcel.writeString(ctlessTermRiskMngt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IdEmv> {
        override fun createFromParcel(parcel: Parcel): IdEmv {
            return IdEmv(parcel)
        }

        override fun newArray(size: Int): Array<IdEmv?> {
            return arrayOfNulls(size)
        }
    }
}

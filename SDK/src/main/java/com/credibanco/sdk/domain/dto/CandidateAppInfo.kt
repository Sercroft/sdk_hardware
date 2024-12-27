package com.credibanco.sdk.domain.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CandidateAppInfo(
    @SerializedName("aid")
    val aid: ByteArray?,
    @SerializedName("appLabel")
    val appLabel: ByteArray?,
    @SerializedName("preferName")
    val preferName: ByteArray?,
    @SerializedName("priority")
    val priority: Byte = 0,
    @SerializedName("langPrefer")
    val langPrefer: ByteArray?,
    @SerializedName("icti")
    val icti: Byte = 0,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createByteArray(),
        parcel.createByteArray(),
        parcel.createByteArray(),
        parcel.readByte(),
        parcel.createByteArray(),
        parcel.readByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByteArray(aid)
        parcel.writeByteArray(appLabel)
        parcel.writeByteArray(preferName)
        parcel.writeByte(priority)
        parcel.writeByteArray(langPrefer)
        parcel.writeByte(icti)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CandidateAppInfo

        if (aid != null) {
            if (other.aid == null) return false
            if (!aid.contentEquals(other.aid)) return false
        } else if (other.aid != null) return false
        if (appLabel != null) {
            if (other.appLabel == null) return false
            if (!appLabel.contentEquals(other.appLabel)) return false
        } else if (other.appLabel != null) return false
        if (preferName != null) {
            if (other.preferName == null) return false
            if (!preferName.contentEquals(other.preferName)) return false
        } else if (other.preferName != null) return false
        if (priority != other.priority) return false
        if (langPrefer != null) {
            if (other.langPrefer == null) return false
            if (!langPrefer.contentEquals(other.langPrefer)) return false
        } else if (other.langPrefer != null) return false
        if (icti != other.icti) return false

        return true
    }

    override fun hashCode(): Int {
        var result = aid?.contentHashCode() ?: 0
        result = 31 * result + (appLabel?.contentHashCode() ?: 0)
        result = 31 * result + (preferName?.contentHashCode() ?: 0)
        result = 31 * result + priority
        result = 31 * result + (langPrefer?.contentHashCode() ?: 0)
        result = 31 * result + icti
        return result
    }

    companion object CREATOR : Parcelable.Creator<CandidateAppInfo> {
        override fun createFromParcel(parcel: Parcel): CandidateAppInfo {
            return CandidateAppInfo(parcel)
        }

        override fun newArray(size: Int): Array<CandidateAppInfo?> {
            return arrayOfNulls(size)
        }
    }
}

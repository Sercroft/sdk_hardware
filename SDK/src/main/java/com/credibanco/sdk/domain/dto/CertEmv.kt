package com.credibanco.sdk.domain.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CertEmv(

    @SerializedName( "id")
    var id: Int,

    @SerializedName( "idGroup")
    var idGroup: Int,

    @SerializedName("publicKeyIndex")
    var publicKeyIndex: String?,

    @SerializedName("publicKeyRid")
    var publicKeyRid: String?,

    @SerializedName("publicKeyLength")
    var publicKeyLength: String?,

    @SerializedName("publicKeyExpiration")
    var publicKeyExpiration: Int,

    @SerializedName("publicKeyExponent")
    var publicKeyExponent: Int,

    @SerializedName("publicKey")
    var publicKey: String?,

    @SerializedName("publicKeyHash")
    var publicKeyHash: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(idGroup)
        parcel.writeString(publicKeyIndex)
        parcel.writeString(publicKeyRid)
        parcel.writeString(publicKeyLength)
        parcel.writeInt(publicKeyExpiration)
        parcel.writeInt(publicKeyExponent)
        parcel.writeString(publicKey)
        parcel.writeString(publicKeyHash)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CertEmv> {
        override fun createFromParcel(parcel: Parcel): CertEmv {
            return CertEmv(parcel)
        }

        override fun newArray(size: Int): Array<CertEmv?> {
            return arrayOfNulls(size)
        }
    }
}

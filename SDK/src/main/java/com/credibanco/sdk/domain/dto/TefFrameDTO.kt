package com.credibanco.sdk.domain.dto

import android.os.Parcel
import android.os.Parcelable

data class TefFrameDTO(
    var transportHeader: String? = null,
    var presentationHeader: String? = null,
    var authorizationCode: String? = null, // 01
    val hash: String? = null, // 20
    var idClient: String? = null, // 21
    val crypto: String? = null, // 22
    val tvr: String? = null, // 23
    val tsi: String? = null, // 24
    val aid: String? = null, // 25
    val meanAcquisition: String? = null, // 26
    val dccInfo: String? = null, // 27
    val digitalSign: String? = null, // 28
    val withSign: String? = null, // 29
    var totalPurchase: String? = null, // 40
    var iva: String? = null, // 41
    var discountIva: String? = null, // 80
    var cashRegisterId: String? = null, // 42
    var invoiceId: String? = null, // 43
    var rrn: String? = null, // 44
    var terminalId: String? = null, // 45
    var trxDate: String? = null, // 46
    var trxTime: String? = null, // 47
    var answerCode: String? = null, // 48
    var franchise: String? = null, // 49
    var accountType: String? = null, // 50
    var installments: String? = null, // 51
    var transactionId: String? = null, // 53
    var last4digits: String? = null, // 54
    var typeOfDocument: String? = null, // 55
    var cardBin: String? = null, // 75
    var expirationCard: String? = null, // 76
    var commerceCode: String? = null, // 77
    var commerceAddress: String? = null, // 78
    var label: String? = null, // 79
    var tip: String? = null, // 81
    var iac: String? = null, // 82
    var cashierId: String? = null, // 83
    var filler: String? = null, // 84
    var filler2: String? = null, // 85
    var filler3: String? = null, // 86
    var filler4: String? = null, // 87
    var filler5: String? = null, // 88
    var filler6: String? = null, // 89
    var filler7: String? = null, // 90
    var pan: String? = null, // 95
    var serial: String? = null, // 96
) : Parcelable {
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
        parcel.readString(),
        parcel.readString()
    )

    fun getListDataAndProperties(): LinkedHashMap<String, String> {
        val properties = this::class.java.declaredFields
        val propertiesWithData = linkedMapOf<String, String>()

        for (paramsFrame in orderedParamsFrameList) {
            val property = properties.find { it.name == paramsFrame }

            if (property != null) {
                property.isAccessible = true
                val value = property.get(this)

                if (value != null && property.name !in ignoredCamps)
                    propertiesWithData[property.name] = value.toString()
            }
        }
        return propertiesWithData
    }

    fun allParamsIsNull(): Boolean {
        val properties = this::class.java.declaredFields

        for (property in properties) {
            property.isAccessible = true
            val value = property.get(this)
            if (value != null && property.name !in ignoredCamps)
                return false
        }
        return true
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(transportHeader)
        parcel.writeString(presentationHeader)
        parcel.writeString(authorizationCode)
        parcel.writeString(hash)
        parcel.writeString(idClient)
        parcel.writeString(crypto)
        parcel.writeString(tvr)
        parcel.writeString(tsi)
        parcel.writeString(aid)
        parcel.writeString(meanAcquisition)
        parcel.writeString(dccInfo)
        parcel.writeString(digitalSign)
        parcel.writeString(withSign)
        parcel.writeString(totalPurchase)
        parcel.writeString(iva)
        parcel.writeString(discountIva)
        parcel.writeString(cashRegisterId)
        parcel.writeString(invoiceId)
        parcel.writeString(rrn)
        parcel.writeString(terminalId)
        parcel.writeString(trxDate)
        parcel.writeString(trxTime)
        parcel.writeString(answerCode)
        parcel.writeString(franchise)
        parcel.writeString(accountType)
        parcel.writeString(installments)
        parcel.writeString(transactionId)
        parcel.writeString(last4digits)
        parcel.writeString(typeOfDocument)
        parcel.writeString(cardBin)
        parcel.writeString(expirationCard)
        parcel.writeString(commerceCode)
        parcel.writeString(commerceAddress)
        parcel.writeString(label)
        parcel.writeString(tip)
        parcel.writeString(iac)
        parcel.writeString(cashierId)
        parcel.writeString(filler)
        parcel.writeString(filler2)
        parcel.writeString(filler3)
        parcel.writeString(filler4)
        parcel.writeString(filler5)
        parcel.writeString(filler6)
        parcel.writeString(filler7)
        parcel.writeString(pan)
        parcel.writeString(serial)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TefFrameDTO> {
        override fun createFromParcel(parcel: Parcel): TefFrameDTO {
            return TefFrameDTO(parcel)
        }

        override fun newArray(size: Int): Array<TefFrameDTO?> {
            return arrayOfNulls(size)
        }

        val ignoredCamps = listOf(
            "Companion",
            "CREATOR",
            "orderedParamsFrameList",
            "ignoredCamps",
        )

        val orderedParamsFrameList = listOf(
            "transportHeader",
            "presentationHeader",
            "authorizationCode",
            "hash",
            "idClient",
            "crypto",
            "tvr",
            "tsi",
            "aid",
            "meanAcquisition",
            "dccInfo",
            "digitalSign",
            "withSign",
            "totalPurchase",
            "iva",
            "discountIva",
            "cashRegisterId",
            "invoiceId",
            "rrn",
            "terminalId",
            "trxDate",
            "trxTime",
            "answerCode",
            "franchise",
            "accountType",
            "installments",
            "transactionId",
            "last4digits",
            "typeOfDocument",
            "cardBin",
            "expirationCard",
            "commerceCode",
            "commerceAddress",
            "label",
            "tip",
            "iac",
            "cashierId",
            "filler",
            "filler2",
            "filler3",
            "filler4",
            "filler5",
            "filler6",
            "filler7",
            "pan",
            "serial",
        )
    }
}
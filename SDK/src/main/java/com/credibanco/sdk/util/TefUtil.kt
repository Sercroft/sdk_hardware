package com.credibanco.sdk.util

import com.credibanco.sdk.domain.dto.TefFrameDTO
import kotlin.experimental.xor

object TefUtil {
    const val ib = "02"
    const val fb = "03"
    const val ack = "06"
    const val sb = "08"
    const val nack = "15"
    const val transportHeader = "36 30 30 30 30 30 30 30 30 30"
    const val separator = "1C"
    const val startTransportHeader = 9
    const val endTransportHeader = 38
    const val startPresentationHeader = 39
    const val endPresentationHeader = 59
    const val valueNameParam = 5
    const val startLenHexa = 6
    const val endLenHexa = 11
    const val startValueOfParam = 12
    const val endTransportHeaderResponse = 41
    const val startOfPresentationtHeaderResponse = 42
    const val endOfPresentationtHeaderResponse = 62
    const val INVOCATION_FRAME = "02 00 17 36 30 30 30 30 30 30 30 30 30 31 30 30 30 20 20 30 03 23"

    enum class Fields {
        // 01
        AUTHORIZATIONCODE {
            override fun hexa() = "30 31"
            override fun lenght() = 6
            override fun isString() = false
        },

        // 20
        HASH {
            override fun hexa() = "32 30"
            override fun lenght() = 9
            override fun isString() = true
        },

        // 21
        IDCLIENT {
            override fun hexa() = "32 31"
            override fun lenght() = 27
            override fun isString() = true
        },

        // 22
        CRYPTO {
            override fun hexa() = "32 32"
            override fun lenght() = 16
            override fun isString() = true
        },

        // 23
        TVR {
            override fun hexa() = "32 33"
            override fun lenght() = 10
            override fun isString() = true
        },

        // 24
        TSI {
            override fun hexa() = "32 34"
            override fun lenght() = 4
            override fun isString() = true
        },

        // 25
        AID {
            override fun hexa() = "32 35"
            override fun lenght() = 14
            override fun isString() = true
        },

        // 26
        MEANACQUISITION {
            override fun hexa() = "32 36"
            override fun lenght() = 1
            override fun isString() = false
        },

        // 27
        DCCINFO {
            override fun hexa() = "32 37"
            override fun lenght() = 226
            override fun isString() = true
        },

        // 28
        DIGITALSIGN {
            override fun hexa() = "32 38"

            // Lenght of this field is variable
            override fun lenght() = 200
            override fun isString() = true
        },

        // 29
        WITHSIGN {
            override fun hexa() = "32 39"
            override fun lenght() = 1
            override fun isString() = false
        },

        // 40
        TOTALPURCHASE {
            override fun hexa() = "34 30"
            override fun lenght() = 12
            override fun isString() = false
        },

        // 41
        IVA {
            override fun hexa() = "34 31"
            override fun lenght() = 12
            override fun isString() = false
        },

        // 42
        CASHREGISTERID {
            override fun hexa() = "34 32"
            override fun lenght() = 10
            override fun isString() = true
        },

        // 43
        INVOICEID {
            override fun hexa() = "34 33"
            override fun lenght() = 6
            override fun isString() = false
        },

        // 44
        RRN {
            override fun hexa() = "34 34"
            override fun lenght() = 6
            override fun isString() = false
        },

        // 45
        TERMINALID {
            override fun hexa() = "34 35"
            override fun lenght() = 8
            override fun isString() = false
        },

        // 46
        TRXDATE {
            override fun hexa() = "34 36"
            override fun lenght() = 6
            override fun isString() = true
        },

        // 47
        TRXTIME {
            override fun hexa() = "34 37"
            override fun lenght() = 4
            override fun isString() = true
        },

        // 48
        ANSWERCODE {
            override fun hexa() = "34 38"
            override fun lenght() = 2
            override fun isString() = true
        },

        // 49
        FRANCHISE {
            override fun hexa() = "34 39"
            override fun lenght() = 10
            override fun isString() = true
        },

        // 50
        ACCOUNTTYPE {
            override fun hexa() = "35 30"
            override fun lenght() = 2
            override fun isString() = true
        },

        // 51
        INSTALLMENTS {
            override fun hexa() = "35 31"
            override fun lenght() = 2
            override fun isString() = false
        },

        // 53
        TRANSACTIONID {
            override fun hexa() = "35 33"
            override fun lenght() = 10
            override fun isString() = true
        },

        // 54
        LAST4DIGITS {
            override fun hexa() = "35 34"
            override fun lenght() = 4
            override fun isString() = false
        },

        // 55
        TYPEOFDOCUMENT {
            override fun hexa() = "35 35"
            override fun lenght() = 1
            override fun isString() = false
        },

        // 75
        CARDBIN {
            override fun hexa() = "37 35"
            override fun lenght() = 6
            override fun isString() = false
        },

        // 76
        EXPIRATIONCARD {
            override fun hexa() = "37 36"
            override fun lenght() = 4
            override fun isString() = false
        },

        // 77
        COMMERCECODE {
            override fun hexa() = "37 37"
            override fun lenght() = 23
            override fun isString() = true
        },

        // 78
        COMMERCEADDRESS {
            override fun hexa() = "37 38"
            override fun lenght() = 23
            override fun isString() = true
        },

        // 79
        LABEL {
            override fun hexa() = "37 39"
            override fun lenght() = 2
            override fun isString() = false
        },

        // 80
        DISCOUNTIVA {
            override fun hexa() = "38 30"
            override fun lenght() = 12
            override fun isString() = false
        },

        // 81
        TIP {
            override fun hexa() = "38 31"
            override fun lenght() = 12
            override fun isString() = false
        },

        // 82
        IAC {
            override fun hexa() = "38 32"
            override fun lenght() = 12
            override fun isString() = false
        },

        // 83
        CASHIERID {
            override fun hexa() = "38 33"
            override fun lenght() = 12
            override fun isString() = false
        },

        // 84
        FILLER {
            override fun hexa() = "38 34"
            override fun lenght() = 12
            override fun isString() = true
        },

        // 85
        FILLER2 {
            override fun hexa() = "38 35"
            override fun lenght() = 12
            override fun isString() = false
        },

        // 86
        FILLER3 {
            override fun hexa() = "38 36"
            override fun lenght() = 12
            override fun isString() = false
        },

        // 87
        FILLER4 {
            override fun hexa() = "38 37"
            override fun lenght() = 12
            override fun isString() = false
        },

        // 88
        FILLER5 {
            override fun hexa() = "38 38"
            override fun lenght() = 12
            override fun isString() = false
        },// 89
        FILLER6 {
            override fun hexa() = "38 39"
            override fun lenght() = 12
            override fun isString() = false
        },

        // 90
        FILLER7 {
            override fun hexa() = "39 30"
            override fun lenght() = 12
            override fun isString() = false
        },
        // 95

        // 95
        PAN {
            override fun hexa() = "39 35"
            override fun lenght() = 19
            override fun isString() = true
        },

        // 96
        SERIAL {
            override fun hexa() = "39 36"
            override fun lenght() = 19
            override fun isString() = false
        };

        abstract fun hexa(): String
        abstract fun lenght(): Int
        abstract fun isString(): Boolean
    }

    /*
    *  BUYWITHCARD = 31 30 30 30 20 20 30
    *  LASTTRANSACTION = 31 30 30 30 30 30 30
    *  ANNULMENT = 31 30 30 32 30 30 30
    *  CHEQUES = 31 30 30 33 30 30 30
    *  CHEQUES POSFECHADOS = 31 30 30 34 30 30 30
    *  RECARGA BONO REGALO = 31 30 30 36 30 30 30
    *  AVANCE EN EFECTIVO = 31 30 30 37 20 20 30
    *  PAGO IMPUESTOS = 31 30 30 38 20 20 30
    *  RECARGA CELULAR =  31 30 30 35 30 30 30
    * */

    enum class TransactionsStages {
        BUYWITHCARD {
            override fun hex() = "31 30 30 30 20 20 30"
            override fun amountRequest() =
                "02 00 62 36 30 30 30 30 30 30 30 30 30 31 30 30 30 20 20 30 1C 34 30 00 00 1C 34 31 00 00 1C 38 30 00 00 1C 34 32 00 00 1C 35 33 00 00 1C 38 31 00 00 1C 38 32 00 00 1C 38 33 00 00 1C 38 34 00 00 03 47"

            override fun confirmationFields() = "06"
        },
        FINALANSWER_BUYWITHCARD {
            override fun hex() = "31 32 30 30 30 30 30"
            override fun amountRequest() = ""
            override fun confirmationFields() =
                "30 31 34 30 34 31 38 30 34 33 34 34 34 35 34 36 34 37 34 38 34 39 35 30 35 31 35 34 37 35 37 36 37 37 37 38 37 39 38 35 38 36"
        },
        FINALANSWER_ANNULMENT {
            override fun hex() = "31 32 30 32 30 30 30"
            override fun amountRequest() = ""
            override fun confirmationFields() =
                "30 31 34 30 34 31 38 30 34 33 34 34 34 35 34 36 34 37 34 38 34 39 35 30 35 31 35 34 37 35 37 36 37 37 37 38 37 39 38 35 38 36"
        },
        ANNULMENT {
            override fun hex() = "31 30 30 32 20 20 30"
            override fun amountRequest() =
                "02 00 67 36 30 30 30 30 30 30 30 30 30 31 32 30 30 30 30 30 1C 34 30 00 00 1C 34 31 00 00 1C 38 30 00 00 1C 34 32 00 00 1C 35 33 00 00 1C 38 31 00 00 1C 38 32 00 00 1C 38 33 00 00 1C 38 34 00 00 03 47"

            override fun confirmationFields() =
                "30 31 34 30 34 31 38 30 34 33 34 34 34 35 34 36 34 37 34 38 34 39 35 30 35 31 35 34 37 35 37 36 37 37 37 38 37 39 38 39 39 30"
        },
        RECEIVE_ANNULMENT {
            override fun hex(): String = "31 30 30 32 30 30 30"
            override fun amountRequest(): String = ""
            override fun confirmationFields(): String = ""

        },
        RECEIVE_AMOUNTS {
            override fun hex(): String = "31 30 30 30 30 30 30"
            override fun amountRequest(): String = ""
            override fun confirmationFields(): String = ""

        };

        abstract fun hex(): String
        abstract fun amountRequest(): String
        abstract fun confirmationFields(): String

    }


    enum class AccountTypesTef {
        SAVINGSACCOUNT {
            override fun pref() = "DB"
        },
        NONE {
            override fun pref() = ""
        },
        CHECKINGACCOUNT {
            override fun pref() = "ER"
        },
        CREDITACCOUNT {
            override fun pref() = "CR"
        },
        PHONE {
            override fun pref() = ""
        },
        QR {
            override fun pref() = ""
        },
        TRANSFIYA {
            override fun pref() = ""
        };

        abstract fun pref(): String
    }

    fun getFieldByKey(key: String): Fields? {
        for (field in Fields.values()) {
            if (field.name.lowercase() == key.lowercase()) {
                return field
            }
        }
        return null
    }

    fun getStringFromHex(s: String): String {
        var result = ""
        val s2 = s.replace(" ", "")
        var i = 0
        while (i < s2.length) {
            val hexValue = s2.substring(i, i + 2)
            result += Integer.parseInt(hexValue, 16).toChar()
            i += 2
        }
        return result
    }

    // frameInHex should receive the frame without initial byte and without LCR byte.
    fun checkLCR(frameInHex: String): String {
        var checksum = 0x0.toByte()
        val frameBytes = hexString2ByteArray(
            frameInHex.replace(" ", "")
        )
        for (byte in frameBytes) {
            checksum = checksum xor byte
        }
        return byteArray2HexString(byteArrayOf(checksum)).uppercase()
    }

    fun hexString2ByteArray(hexString: String): ByteArray {
        val len = hexString.length
        val byteArray = ByteArray(len / 2)
        for (i in 0 until len step 2) {
            val firstChar = Character.digit(hexString[i], 16)
            val secondChar = Character.digit(hexString[i + 1], 16)
            byteArray[i / 2] = ((firstChar shl 4) + secondChar).toByte()
        }
        return byteArray
    }

    fun byteArray2HexString(hexBytes: ByteArray): String {
        var hexString = ""
        for (byte in hexBytes) {
            val hex = String.format("%02x", byte)
            hexString += hex
        }
        return hexString
    }


    fun hexToSpaced(hex: String): String {
        var hexClean = hex.replace(" ", "")
        val sb = StringBuilder()
        for (i in 0 until hexClean.trim().length step 2) {
            sb.append(hexClean.substring(i, i + 2)).append(" ")
        }
        return sb.toString().trimEnd().uppercase()
    }

    fun fillValue(value: String, isString: Boolean, length: Int): String {
        val valueToFill = if (value.isBlank()) " " else value
        val filled = if (!isString) {
            String.format("%0${length}d", valueToFill.toInt())
        } else {
            value.padEnd(length, ' ')
        }
        return filled
    }

    fun stringToHex(value: String, withSpaceSeparator: Boolean = false): String {
        val result = StringBuilder()
        for (char in value) {
            val hex = char.code.toString(16).padStart(2, '0')
            if (withSpaceSeparator)
                result.append("$hex ")
            else
                result.append(hex)
        }
        if (withSpaceSeparator)
            result.deleteCharAt(result.length - 1)
        return result.toString()
    }

    fun fillingDtoHex(tefFrameDTO: TefFrameDTO, typeOfData: String, data: String?): TefFrameDTO {
        when (typeOfData) {
            Fields.TOTALPURCHASE.hexa() -> tefFrameDTO.totalPurchase = data
            Fields.IVA.hexa() -> tefFrameDTO.iva = data
            Fields.CASHREGISTERID.hexa() -> tefFrameDTO.cashRegisterId = data
            Fields.INVOICEID.hexa() -> tefFrameDTO.invoiceId = data
            Fields.RRN.hexa() -> tefFrameDTO.rrn = data
            Fields.TERMINALID.hexa() -> tefFrameDTO.terminalId = data
            Fields.TYPEOFDOCUMENT.hexa() -> tefFrameDTO.typeOfDocument = data
            Fields.TRXDATE.hexa() -> tefFrameDTO.trxDate = data
            Fields.TRXTIME.hexa() -> tefFrameDTO.trxTime = data
            Fields.ANSWERCODE.hexa() -> tefFrameDTO.answerCode = data
            Fields.FRANCHISE.hexa() -> tefFrameDTO.franchise = data
            Fields.ACCOUNTTYPE.hexa() -> tefFrameDTO.accountType = data
            Fields.INSTALLMENTS.hexa() -> tefFrameDTO.installments = data
            Fields.TRANSACTIONID.hexa() -> tefFrameDTO.transactionId = data
            Fields.LAST4DIGITS.hexa() -> tefFrameDTO.last4digits = data
            Fields.CARDBIN.hexa() -> tefFrameDTO.cardBin = data
            Fields.EXPIRATIONCARD.hexa() -> tefFrameDTO.expirationCard = data
            Fields.COMMERCECODE.hexa() -> tefFrameDTO.commerceCode = data
            Fields.COMMERCEADDRESS.hexa() -> tefFrameDTO.commerceAddress = data
            Fields.LABEL.hexa() -> tefFrameDTO.label = data
            Fields.TIP.hexa() -> tefFrameDTO.tip = data
            Fields.IAC.hexa() -> tefFrameDTO.iac = data
            Fields.CASHIERID.hexa() -> tefFrameDTO.cashierId = data
            Fields.FILLER.hexa() -> tefFrameDTO.filler = data
            Fields.FILLER2.hexa() -> tefFrameDTO.filler2 = data
            Fields.FILLER3.hexa() -> tefFrameDTO.filler3 = data
            Fields.FILLER4.hexa() -> tefFrameDTO.filler4 = data
            Fields.FILLER5.hexa() -> tefFrameDTO.filler5 = data
            Fields.FILLER6.hexa() -> tefFrameDTO.filler6 = data
            Fields.FILLER7.hexa() -> tefFrameDTO.filler7 = data
            transportHeader -> tefFrameDTO.transportHeader = data
            "presentationHeader" -> tefFrameDTO.presentationHeader = data
            Fields.DISCOUNTIVA.hexa() -> tefFrameDTO.discountIva = data
            Fields.PAN.hexa() -> tefFrameDTO.pan = data
            Fields.IDCLIENT.hexa() -> tefFrameDTO.idClient = data
        }
        return tefFrameDTO
    }

    fun separateConcatenatedFrame(frame: String): String {
        val result = buildString {
            for (i in 0 until frame.length) {
                if (i % 2 == 0 && i > 0)
                    append(' ')
                append(frame[i])
            }
        }
        return result
    }


    fun testingFields(
        requiredFields: TefUtil.TransactionsStages,
        notRequiredFields: String
    ): TefFrameDTO {
        var listOfRequired =
            requiredFields.confirmationFields().split(" ").chunked(2) { it.joinToString(" ") }
        var listOfNotRequiredFields = notRequiredFields.split("-")
        println(listOfRequired)
        var tefFrame = TefFrameDTO()
        for (i in listOfRequired) {

            if (!listOfNotRequiredFields.contains(gettingHexaType(i))) {
                tefFrame = fillingDtoHex(tefFrame, i, "")
            }
        }
        return tefFrame
    }

    fun gettingHexaType(type: String): String {
        val separated = type.chunked(1)

        return "3${separated.get(0)}" + " 3" + separated.get(1)
    }

    fun gettingRealDate(date: String): String {
        var realDate = date.takeLast(8)
        val year = realDate.takeLast(2)
        return year + realDate.take(4)
    }

    fun getCommMode(commMode: CommModes): String = when (commMode) {
        CommModes.R232 -> "RS232"
        CommModes.USB -> "USB"
        CommModes.TCP_IP -> "TCP IP"
        CommModes.TEF_SERVICES -> "TEF SERVICES"
        else -> ""
    }

    fun isAlphanumeric(value: String): Boolean{
        val alphaValidation = Regex("^(?=.*[a-zA-Z]|.*[-!@#$%^&*(),.?\":{}|<>])[a-zA-Z0-9!@#$%^&*(),.?\":{}|<>-]+$")
        return alphaValidation.containsMatchIn(value)
    }
}
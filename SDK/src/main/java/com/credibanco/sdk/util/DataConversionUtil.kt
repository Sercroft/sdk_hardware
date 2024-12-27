package com.credibanco.sdk.util

object DataConversionUtil {

    fun intToBinary(x: Int, len: Int): String {
        return String.format(
            "%" + len + "s",
            Integer.toBinaryString(x)
        ).replace(" ".toRegex(), "0")
    }

    fun hexToInt(byte: String): Int?{
        if(isHexadecimalNumber(byte)) {
            var i = byte.length - 1
            var decimalN: Int = 0
            var base = 1
            while(i >= 0) {
                val charAtPos = byte[i]

                val lastDigit = if((charAtPos >= '0') && (charAtPos <= '9')) {
                    charAtPos - '0'
                } else if((charAtPos >= 'A') && (charAtPos <= 'F')) {
                    charAtPos.toInt() - 55
                } else if((charAtPos >= 'a') && (charAtPos <= 'f')) {
                    charAtPos.toInt() - 87
                } else {
                    0
                }

                decimalN += lastDigit * base
                base *= 16

                i--
            }
            return decimalN
        }
        return null
    }

    fun isHexadecimalNumber(hexadecimalNum: String): Boolean {

        var isHexadecimalNum = true

        for(charAtPos in hexadecimalNum) {
            if(!(((charAtPos >= '0') && (charAtPos <= '9')) || ((charAtPos >= 'A') && (charAtPos <= 'F')) || ((charAtPos >= 'a') && (charAtPos <= 'f')))) {
                isHexadecimalNum = false
                break
            }
        }
        return isHexadecimalNum
    }

    fun hexToAscii(hexValue: String, tag: String?): String {
        var asciiString = ""
        val hexStringCleaned = tag?.let { hexValue.removePrefix(it) }
        if (hexStringCleaned != null) {
            for (i in hexStringCleaned.indices step 2) {
                val byte = hexStringCleaned.substring(i, i + 2)
                val asciiValue = byte.toInt(16)
                if (asciiValue > 32) {
                    val asciiCharacter = asciiValue.toChar()
                    asciiString += asciiCharacter
                }
            }
        }
        return asciiString
    }

}
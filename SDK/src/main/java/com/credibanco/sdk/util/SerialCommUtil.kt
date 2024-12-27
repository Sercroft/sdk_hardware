package com.credibanco.sdk.util

import com.credibanco.sdk.util.Constants.EMPTY_STRING
import kotlin.experimental.xor

object SerialCommUtil {

    fun calculateFrameXor(byteArray: ByteArray): Byte {
        var xorResult = (0x0).toByte()

        for (byte in byteArray) {
            xorResult = xorResult.xor(byte)
        }

        return xorResult
    }

    fun hexToAscii(hexStr: String): String {
        val output = StringBuilder(EMPTY_STRING)
        var str: String

        var newHexStr = hexStr

        if (hexStr.length % 2 == 1) {
            newHexStr = hexStr.dropLast(1)
        }

        for (i in newHexStr.indices step 2) {
            str = newHexStr.substring(i, i + 2);
            output.append(Integer.parseInt(str, 16).toChar());
        }

        return output.toString();
    }
}
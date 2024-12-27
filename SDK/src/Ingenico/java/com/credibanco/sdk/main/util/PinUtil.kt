package com.credibanco.sdk.main.util

import com.credibanco.sdk.domain.type.PinBlockType

object PinUtil {

    fun getPinBlockType(pinBlockType: Boolean?): PinBlockType? {
        return when(pinBlockType) {
            true -> PinBlockType.PIN
            false -> PinBlockType.OFFLINEPIN
            else ->  PinBlockType.NOPIN
        }
    }
}
package com.credibanco.sdk.main.util.printer

import com.usdk.apiservice.aidl.BaseError

object ErrorPrinterIngenicoUtil {
    fun getErrorMessage(error: Int): String {
        return when (error) {
            BaseError.SERVICE_CRASH -> "SERVICE_CRASH"
            BaseError.REQUEST_EXCEPTION -> "REQUEST_EXCEPTION"
            BaseError.ERROR_CANNOT_EXECUTABLE -> "ERROR_CANNOT_EXECUTABLE"
            BaseError.ERROR_INTERRUPTED -> "ERROR_INTERRUPTED"
            BaseError.ERROR_HANDLE_INVALID -> "ERROR_HANDLE_INVALID"
            else -> "Unknown error"
        }
    }
}
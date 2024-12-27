package com.credibanco.sdk.data.mapper

import com.credibanco.sdk.util.NexgoPrintingConstants.PRINTER_ADDIMG_FAIL
import com.credibanco.sdk.util.NexgoPrintingConstants.PRINTER_ADDPRNSTR_FAIL
import com.credibanco.sdk.util.NexgoPrintingConstants.PRINTER_BUSY
import com.credibanco.sdk.util.NexgoPrintingConstants.PRINTER_FAULT
import com.credibanco.sdk.util.NexgoPrintingConstants.PRINTER_NODEVICE
import com.credibanco.sdk.util.NexgoPrintingConstants.PRINTER_OTHER_ERROR
import com.credibanco.sdk.util.NexgoPrintingConstants.PRINTER_PAPERLACK
import com.credibanco.sdk.util.NexgoPrintingConstants.PRINTER_PRINT_FAIL
import com.credibanco.sdk.util.NexgoPrintingConstants.PRINTER_SUCCESS
import com.credibanco.sdk.util.NexgoPrintingConstants.PRINTER_TOO_HOT
import com.credibanco.sdk.util.NexgoPrintingConstants.PRINTER_UNFINISHED
import com.credibanco.sdk.util.NexgoPrintingConstants.PRINTER_WRONG_PACKAGE
import com.nexgo.oaf.apiv3.SdkResult

class NexgoPrintResponseMapper : BaseMapper<Int, Int> {
    override fun transformFrom(input: Int): Int {
        return when (input) {
            SdkResult.Success                   -> PRINTER_SUCCESS
            SdkResult.Printer_Print_Fail        -> PRINTER_PRINT_FAIL
            SdkResult.Printer_AddPrnStr_Fail    -> PRINTER_ADDPRNSTR_FAIL
            SdkResult.Printer_AddImg_Fail       -> PRINTER_ADDIMG_FAIL
            SdkResult.Printer_Busy              -> PRINTER_BUSY
            SdkResult.Printer_PaperLack         -> PRINTER_PAPERLACK
            SdkResult.Printer_Wrong_Package     -> PRINTER_WRONG_PACKAGE
            SdkResult.Printer_Fault             -> PRINTER_FAULT
            SdkResult.Printer_TooHot            -> PRINTER_TOO_HOT
            SdkResult.Printer_UnFinished        -> PRINTER_UNFINISHED
            SdkResult.Printer_NoDevice          -> PRINTER_NODEVICE
            else -> PRINTER_OTHER_ERROR
        }
    }

    override fun transformTo(input: Int): Int {
        TODO("Not yet implemented")
    }
}
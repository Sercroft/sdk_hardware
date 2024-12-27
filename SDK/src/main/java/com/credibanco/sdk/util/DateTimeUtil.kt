package com.credibanco.sdk.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateTimeUtil {
    fun getNowDate(): String {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        return format.format(calendar.time)
    }

    fun getNowTime(): String {
        val format = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        return format.format(calendar.time)
    }

    fun formatDateTimeForReceipt(dateTime: String): String? {
        val format = SimpleDateFormat("HHmmssMMddyyyy", Locale.ENGLISH);
        val date = format.parse(dateTime)
        format.applyPattern("HH:mm:ss dd/MM/yyyy")

        return date?.let {
            format.format(date)
        }
    }

    fun formatDateTimeForReceiptSDK(dateTime: String): String {
        val format = SimpleDateFormat("ddMMyy", Locale.ENGLISH);
        val date = format.parse(dateTime)
        format.applyPattern("yyyyMMdd")

        return date?.let {
            format.format(date)
        }?: ""
    }

}
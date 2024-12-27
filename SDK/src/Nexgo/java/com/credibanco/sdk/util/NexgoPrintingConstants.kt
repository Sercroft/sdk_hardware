package com.credibanco.sdk.util

object NexgoPrintingConstants {
    const val TYPEFACE = "TYPEFACE"
    const val LETTER_SPACING = "LETTER_SPACING"
    const val GRAY_LEVEL = "GRAY_LEVEL"
    const val PACKAGE_NAME = "PACKAGE_NAME"

    const val TYPEFACE_DEFAULT = "TYPEFACE_DEFAULT"
    const val TYPEFACE_DEFAULT_BOLD = "TYPEFACE_DEFAULT_BOLD"
    const val TYPEFACE_MONOSPACE = "TYPEFACE_MONOSPACE"
    const val TYPEFACE_SANS_SERIF = "TYPEFACE_SANS_SERIF"
    const val TYPEFACE_SERIF = "TYPEFACE_SERIF"

    const val GRAY_LEVEL_0 = "GRAY_LEVEL_0"
    const val GRAY_LEVEL_1 = "GRAY_LEVEL_1"
    const val GRAY_LEVEL_2 = "GRAY_LEVEL_2"
    const val GRAY_LEVEL_3 = "GRAY_LEVEL_3"
    const val GRAY_LEVEL_4 = "GRAY_LEVEL_4"

    const val FONT_BIG = "FONT_BIG"
    const val FONT_NORMAL = "FONT_NORMAL"
    const val FONT_IOU = "FONT_IOU"

    const val ALIGN_LEFT = "ALIGN_LEFT"
    const val ALIGN_RIGHT = "ALIGN_RIGHT"
    const val ALIGN_CENTER = "ALIGN_CENTER"

    const val DEFAULT_LETTER_SPACING = 6

    const val LETTER_IS_BOLD = "LETTER_IS_BOLD"

    private const val IS_UNDERLINE = false
    private const val MARGIN_LEFT = 0
    private const val IS_ZOOM_X = false
    private const val IS_ZOOM_Y = false
    private const val IS_BOLD = false

    // PRINTING LINE TYPES
    const val IMAGE_TYPE = "IMAGE"
    const val TEXT_TYPE = "TEXT"
    const val TEXT_DOUBLE_TYPE = "TEXT_DOUBLE"
    const val QR_TYPE = "QR"

    // PRINTING RESULT CODES
    const val PRINTER_SUCCESS = 0
    const val PRINTER_PRINT_FAIL = -1001
    const val PRINTER_ADDPRNSTR_FAIL = -1002
    const val PRINTER_ADDIMG_FAIL = -1003
    const val PRINTER_BUSY = -1004
    const val PRINTER_PAPERLACK = -1005
    const val PRINTER_WRONG_PACKAGE = -1006
    const val PRINTER_FAULT = -1007
    const val PRINTER_TOO_HOT = -1008
    const val PRINTER_UNFINISHED = -1009
    const val PRINTER_NODEVICE = -1010
    const val PRINTER_OTHER_ERROR = -1999

    const val TIME_OUT = "time_out"
    const val CUSTOMER_EXIT = "customer_exit"
    const val ERROR_SCAN = "error_scan"
}
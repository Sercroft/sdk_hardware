package com.credibanco.sdk.util

object Constants {

    const val EMPTY_STRING      = ""
    const val FILE_SEPARATOR    = ","
    const val SEPARATOR_DECIMAL = "."

    // PACKAGE AND ACTIVITIES
    const val PACKAGE_SMARTPOS_PERIPHERALS      = "com.credibanco.smartposperipherals"
    const val NFC_ACTIVITY_PERIPHERAL           = "com.credibanco.smartposperipherals.presentation.activity.ExternalNfcReadActivity"
    const val PRINT_ACTIVITY_PERIPHERAL         = "com.credibanco.smartposperipherals.presentation.activity.ExternalPrintingActivity"
    const val CAMERA_ACTIVITY_PERIPHERAL        = "com.credibanco.smartposperipherals.presentation.activity.ExternalScannerActivity"
    const val BLUETOOTH_ACTIVITY_PERIPHERAL     = "com.credibanco.smartposperipherals.presentation.activity.ExternalBluetoothActivity"

    // NEXGO MODELS
    const val UN20  = "UN20"
    const val N6    = "N6"
    const val N86   = "N86"
    const val N6S   = "N6S"

    // INGENICO MODELS
    const val DX4000 = "DX4000"

    // CARD FRANCHISES
    const val VISA_FRANCHISE        = "VISA"
    const val MASTERCARD_FRANCHISE  = "MASTERCARD"
    const val AMEX_FRANCHISE        = "AMERICAN_EXPRESS"
    const val UNKNOWN_FRANCHISE     = "UNKNOWN_FRANCHISE"

    // TYPES
    const val IMAGE = "IMAGE"
    const val TEXT  = "TEXT"

    // ALIGNMENTS
    const val ALIGN_LEFT = "ALIGN_LEFT"
    const val ALIGN_RIGHT = "ALIGN_RIGHT"
    const val ALIGN_CENTER = "ALIGN_CENTER"

    // FONT SIZES
    const val FONT_BIG = "FONT_BIG"
    const val FONT_NORMAL = "FONT_NORMAL"
    const val FONT_IOU = "FONT_IOU"

    // PROPERTY NAMES
    const val TYPEFACE            = "TYPEFACE"
    const val LETTER_SPACING      = "LETTER_SPACING"
    const val GRAY_LEVEL          = "GRAY_LEVEL"
    const val PACKAGE_NAME        = "PACKAGE_NAME"


    // PERIPHERALS RESULT CODES
    const val RESULT_NFC_CODE       = 60000
    const val RESULT_PRINT_CODE     = 40000
    const val RESULT_CAMERA_CODE    = 80000
    const val RESULT_BLUETOOTH_CODE = 5010

    // INTEGRATION TYPES
    const val TYPE_INTEGRATION  = "TYPE_INTEGRATION"
    const val TYPE_FUNCTION     = "TYPE_FUNCTION"
    const val NFC               = "NFC"
    const val CAMERA            = "CAMERA"
    const val PRINT             = "PRINT"
    const val BLUETOOTH         = "BLUETOOTH"

    // GENERAL VARIABLES
    const val HASH_CODE = "HASH_CODE"

    // VARIABLES CAMERA
    const val SHOWBAR       = "SHOWBAR"
    const val SHOWBACK      = "SHOWBACK"
    const val SHOWTITLE     = "SHOWTITLE"
    const val SHOWSWITCH    = "SHOWSWITCH"
    const val SHOWMENU      = "SHOWMENU"
    const val TITLE         = "TITLE"
    const val TITLESIZE     = "TITLESIZE"
    const val SCANTIP       = "SCANTIP"
    const val TIPSIZE       = "TIPSIZE"

    // TYPEFACES
    const val TYPEFACE_DEFAULT      = "TYPEFACE_DEFAULT"
    const val TYPEFACE_DEFAULT_BOLD = "TYPEFACE_DEFAULT_BOLD"
    const val TYPEFACE_MONOSPACE    = "TYPEFACE_MONOSPACE"
    const val TYPEFACE_SANS_SERIF   = "TYPEFACE_SANS_SERIF"
    const val TYPEFACE_SERIF        = "TYPEFACE_SERIF"

    // GRAY LEVELS
    const val GRAY_LEVEL_0 = "GRAY_LEVEL_0"
    const val GRAY_LEVEL_1 = "GRAY_LEVEL_1"
    const val GRAY_LEVEL_2 = "GRAY_LEVEL_2"
    const val GRAY_LEVEL_3 = "GRAY_LEVEL_3"
    const val GRAY_LEVEL_4 = "GRAY_LEVEL_4"


    //BLUETOOTH
    val STATE_BLUETOOTH = "STATE_BLUETOOTH"

    // NFC
    const val UID = "UID"

    // QR
    const val QR = "QR"

}
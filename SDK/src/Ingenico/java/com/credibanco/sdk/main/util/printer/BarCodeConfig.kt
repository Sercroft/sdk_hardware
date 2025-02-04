package com.credibanco.sdk.main.util.printer

data class BarCodeConfig(
    val content: String,
    val height: Int = 48,
    val type: Int = 2
)
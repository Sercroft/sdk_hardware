package com.credibanco.sdk.main.data.datasource

import android.content.res.Resources
import com.credibanco.sdk.main.util.printer.PrintConfig

interface PrintDataSourceIngenico {
    suspend operator fun invoke(
        printConfig : PrintConfig,
        resources   : Resources,
        typeface    : String?
    )
}
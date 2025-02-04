package com.credibanco.sdk.main.domain.usecase

import android.content.res.Resources
import com.credibanco.sdk.main.util.printer.PrintConfig

interface ExternalPrintUseCaseIngenico {
    suspend operator fun invoke(
        printConfig : PrintConfig,
        resources   : Resources,
        typeface    : String?
    )
}
package com.credibanco.sdk.datasource

import android.content.res.Resources

interface PrintDataSource {
    suspend operator fun invoke(
        linesToPrint: ArrayList<String>,
        packageName: String,
        resources: Resources,
        typeface: String?,
        letterSpacing: Int?,
        grayLevel: String?
    ): Int
}
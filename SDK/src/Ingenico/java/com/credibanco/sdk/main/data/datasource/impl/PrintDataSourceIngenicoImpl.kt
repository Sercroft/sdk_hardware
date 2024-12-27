package com.credibanco.sdk.main.data.datasource.impl

import android.content.res.Resources
import com.credibanco.sdk.datasource.PrintDataSource
import javax.inject.Inject

class PrintDataSourceIngenicoImpl @Inject constructor(): PrintDataSource {
    override suspend fun invoke(
        linesToPrint: ArrayList<String>,
        packageName: String,
        resources: Resources,
        typeface: String?,
        letterSpacing: Int?,
        grayLevel: String?
    ): Int {
        TODO("Not yet implemented")
    }
}
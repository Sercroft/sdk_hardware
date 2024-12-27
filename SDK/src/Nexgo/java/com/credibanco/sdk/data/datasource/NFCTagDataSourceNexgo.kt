package com.credibanco.sdk.data.datasource

interface NFCTagDataSourceNexgo {
    suspend operator fun invoke(isRequiredUID: Boolean?) : String
}
package com.credibanco.sdk.data.repository.impl

import com.credibanco.sdk.data.datasource.NFCTagDataSourceNexgo
import com.credibanco.sdk.domain.repository.NFCTagRepositoryNexgo
import javax.inject.Inject

class NFCTagRepositoryNexgoImpl @Inject constructor(
    private val nfcTagDataSource: NFCTagDataSourceNexgo
) : NFCTagRepositoryNexgo {
    override suspend fun invoke(isRequiredUID: Boolean?): String {
        return nfcTagDataSource.invoke(isRequiredUID)
    }
}
package com.credibanco.sdk.domain.usecase.impl

import com.credibanco.sdk.domain.repository.NFCTagRepositoryNexgo
import com.credibanco.sdk.domain.usecase.NFCTagUseCaseNexgo
import javax.inject.Inject

class NFCTagNexgoUseCaseNexgoImpl @Inject constructor(
    private val nfcTagRepository: NFCTagRepositoryNexgo
) : NFCTagUseCaseNexgo {
    override suspend fun invoke(isRequiredUID: Boolean?): String {
        return nfcTagRepository.invoke(isRequiredUID)
    }
}
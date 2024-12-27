package com.credibanco.sdk.domain.repository

interface NFCTagRepositoryNexgo {
    suspend operator fun invoke(isRequiredUID: Boolean?): String
}
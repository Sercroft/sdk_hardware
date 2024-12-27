package com.credibanco.sdk.domain.usecase

interface NFCTagUseCaseNexgo {
    suspend operator fun invoke(isRequiredUID: Boolean?): String
}
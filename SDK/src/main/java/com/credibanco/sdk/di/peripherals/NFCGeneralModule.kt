package com.credibanco.sdk.di.peripherals

import android.content.Context
import com.credibanco.sdk.data.repository.impl.NFCTagRepositoryGeneralImpl
import com.credibanco.sdk.datasource.NFCTagDataSourceGeneral
import com.credibanco.sdk.domain.repository.NFCTagRepositoryGeneral
import com.credibanco.sdk.domain.usecase.impl.NFCTagUseCaseGeneralImpl
import com.credibanco.sdk.domain.usecase.NFCTagUseCaseGeneral
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NFCGeneralModule {

    @Provides
    @Singleton
    fun provideNFCTagUseCase(nfcTagRepository: NFCTagRepositoryGeneral): NFCTagUseCaseGeneral =
        NFCTagUseCaseGeneralImpl(nfcTagRepository)

    @Provides
    @Singleton
    fun provideNFCTagRepository(nfcTagDataSource: NFCTagDataSourceGeneral): NFCTagRepositoryGeneral =
        NFCTagRepositoryGeneralImpl(nfcTagDataSource)



}
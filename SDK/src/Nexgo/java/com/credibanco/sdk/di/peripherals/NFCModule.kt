package com.credibanco.sdk.di.peripherals

import com.credibanco.sdk.data.datasource.NFCTagDataSourceNexgo
import com.credibanco.sdk.data.datasource.impl.NFCTagDataSourceNexgoImpl
import com.credibanco.sdk.data.repository.impl.NFCTagRepositoryNexgoImpl
import com.credibanco.sdk.domain.repository.NFCTagRepositoryNexgo
import com.credibanco.sdk.domain.usecase.NFCTagUseCaseNexgo
import com.credibanco.sdk.domain.usecase.impl.NFCTagNexgoUseCaseNexgoImpl
import com.nexgo.oaf.apiv3.DeviceEngine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NFCModule {

    @Provides
    @Singleton
    fun provideNFCTagUseCase(nfcTagRepository: NFCTagRepositoryNexgo): NFCTagUseCaseNexgo =
        NFCTagNexgoUseCaseNexgoImpl(nfcTagRepository)

    @Provides
    @Singleton
    fun provideNFCTagRepository(nfcTagDataSource: NFCTagDataSourceNexgo): NFCTagRepositoryNexgo =
        NFCTagRepositoryNexgoImpl(nfcTagDataSource)

}
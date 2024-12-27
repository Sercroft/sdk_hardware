package com.credibanco.sdk.di.peripherals

import com.credibanco.sdk.data.datasource.impl.ScannerDataSourceNexgo
import com.credibanco.sdk.data.datasource.impl.ScannerDataSourceNexgoImpl
import com.credibanco.sdk.data.repository.impl.ScannerRepositoryNexgoImpl
import com.credibanco.sdk.domain.repository.ScannerRepositoryNexgo
import com.credibanco.sdk.domain.usecase.ScannerUseCaseNexgo
import com.credibanco.sdk.domain.usecase.impl.ScannerUseCaseNexgoImpl
import com.nexgo.oaf.apiv3.DeviceEngine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScannerModule {

    @Provides
    @Singleton
    fun provideScannerRepository(scannerDataSource: ScannerDataSourceNexgo): ScannerRepositoryNexgo =
        ScannerRepositoryNexgoImpl(scannerDataSource)

    @Provides
    @Singleton
    fun provideScannerUseCase(scannerRepository: ScannerRepositoryNexgo): ScannerUseCaseNexgo =
        ScannerUseCaseNexgoImpl(scannerRepository)
}
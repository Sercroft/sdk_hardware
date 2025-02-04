package com.credibanco.sdk.di.peripherals

import com.credibanco.sdk.data.repository.impl.ScannerRepositoryNexgoImpl
import com.credibanco.sdk.datasource.ScannerDataSourceGeneral
import com.credibanco.sdk.data.datasource.impl.ScannerDataSourceNexgoImpl
import com.credibanco.sdk.domain.repository.ScannerRepositoryGeneral
import com.credibanco.sdk.domain.usecase.ScannerUseCaseGeneral
import com.credibanco.sdk.domain.usecase.impl.ScannerUseCaseGeneralImpl
import com.nexgo.oaf.apiv3.DeviceEngine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScannerNexgoModule {

    @Provides
    @Singleton
    fun provideScannerRepository(scannerDataSource: ScannerDataSourceGeneral): ScannerRepositoryGeneral =
        ScannerRepositoryNexgoImpl(scannerDataSource)

    @Provides
    @Singleton
    fun provideScannerUseCase(scannerRepository: ScannerRepositoryGeneral): ScannerUseCaseGeneral =
        ScannerUseCaseGeneralImpl(scannerRepository)

    @Provides
    @Singleton
    fun provideScannerDataSource(deviceEngine: DeviceEngine): ScannerDataSourceGeneral =
        ScannerDataSourceNexgoImpl(deviceEngine)
}
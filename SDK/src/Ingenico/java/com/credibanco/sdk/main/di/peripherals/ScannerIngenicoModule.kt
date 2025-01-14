package com.credibanco.sdk.main.di.peripherals

import com.credibanco.sdk.datasource.ScannerDataSourceGeneral
import com.credibanco.sdk.domain.repository.ScannerRepositoryGeneral
import com.credibanco.sdk.domain.usecase.ScannerUseCaseGeneral
import com.credibanco.sdk.domain.usecase.impl.ScannerUseCaseGeneralImpl
import com.credibanco.sdk.main.data.datasource.impl.ScannerDataSourceIngenicoImpl
import com.credibanco.sdk.main.data.repository.impl.ScannerRepositoryIngenicoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScannerIngenicoModule {

        @Provides
        @Singleton
        fun provideScannerRepository(scannerDataSource: ScannerDataSourceGeneral): ScannerRepositoryGeneral =
            ScannerRepositoryIngenicoImpl(scannerDataSource)

        @Provides
        @Singleton
        fun provideScannerUseCase(scannerRepository: ScannerRepositoryGeneral): ScannerUseCaseGeneral =
            ScannerUseCaseGeneralImpl(scannerRepository)

        @Provides
        @Singleton
        fun provideScannerDataSource(): ScannerDataSourceGeneral =
            ScannerDataSourceIngenicoImpl()
    }
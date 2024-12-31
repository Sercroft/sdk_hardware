package com.credibanco.sdk.main.data.di

import android.content.Context
import com.credibanco.sdk.datasource.DeviceDataSource
import com.credibanco.sdk.datasource.PeripheralManagementDataSource
import com.credibanco.sdk.datasource.ExternalPrintDataSourceGeneral
import com.credibanco.sdk.domain.ExternalPrintUseCaseGeneral
import com.credibanco.sdk.main.data.datasource.PeripheralManagementDataSourceIngenicoImpl
import com.credibanco.sdk.main.data.datasource.impl.DeviceDataSourceIngenicoImpl
import com.credibanco.sdk.main.data.datasource.impl.ExternalPrintDataSourceIngenicoImpl
import com.credibanco.sdk.main.domain.repository.ExternalPrintRepositoryIngenico
import com.credibanco.sdk.main.domain.usecase.impl.ExternalPrintUseCaseIngenicoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModuleIngenico {

    @Singleton
    @Provides
    fun contextProvider(@ApplicationContext appContext: Context): Context = appContext


    @Singleton
    @Provides
    fun provideExternalPrintDataSourceIngenico(): ExternalPrintDataSourceGeneral =
        ExternalPrintDataSourceIngenicoImpl()

    @Provides
    @Singleton
    fun provideIngenicoPrintUseCase(externalPrintRepositoryIngenico: ExternalPrintRepositoryIngenico): ExternalPrintUseCaseGeneral {
        return ExternalPrintUseCaseIngenicoImpl(externalPrintRepositoryIngenico)
    }

    @Singleton
    @Provides
    fun peripheralDataSourceProvider(): PeripheralManagementDataSource =
        PeripheralManagementDataSourceIngenicoImpl()

    @Singleton
    @Provides
    fun deviceDataSourceProvider(): DeviceDataSource =
        DeviceDataSourceIngenicoImpl()
}

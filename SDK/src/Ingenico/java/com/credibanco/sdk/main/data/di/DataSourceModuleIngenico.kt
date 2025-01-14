package com.credibanco.sdk.main.data.di

import android.content.Context
import com.credibanco.sdk.datasource.PeripheralManagementDataSource
import com.credibanco.sdk.datasource.ExternalPrintDataSourceGeneral
import com.credibanco.sdk.domain.ExternalPrintUseCaseGeneral
import com.credibanco.sdk.main.data.datasource.GetDeviceManagerDataSourceIngenico
import com.credibanco.sdk.main.data.datasource.PeripheralManagementDataSourceIngenicoImpl
import com.credibanco.sdk.main.data.datasource.impl.ExternalPrintDataSourceIngenicoImpl
import com.credibanco.sdk.main.data.datasource.impl.GetDeviceManagerDataSourceIngenicoImpl
import com.credibanco.sdk.main.domain.repository.ExternalPrintRepositoryIngenico
import com.credibanco.sdk.main.domain.usecase.impl.ExternalPrintUseCaseIngenicoImpl
import com.credibanco.sdk.main.helper.UDeviceServiceManager
import com.usdk.apiservice.aidl.UDeviceService
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
    fun provideExternalPrintDataSourceIngenico(@ApplicationContext context: Context): ExternalPrintDataSourceGeneral =
        ExternalPrintDataSourceIngenicoImpl(context)

    @Provides
    @Singleton
    fun provideIngenicoPrintUseCase(externalPrintRepositoryIngenico: ExternalPrintRepositoryIngenico): ExternalPrintUseCaseGeneral {
        return ExternalPrintUseCaseIngenicoImpl(externalPrintRepositoryIngenico)
    }

    @Singleton
    @Provides
    fun peripheralDataSourceProvider(deviceService: UDeviceServiceManager): PeripheralManagementDataSource =
        PeripheralManagementDataSourceIngenicoImpl(deviceService)

    @Singleton
    @Provides
    fun provideGetDeviceManagerDataSource(deviceService: UDeviceService): GetDeviceManagerDataSourceIngenico {
        return GetDeviceManagerDataSourceIngenicoImpl(deviceService)
    }
}

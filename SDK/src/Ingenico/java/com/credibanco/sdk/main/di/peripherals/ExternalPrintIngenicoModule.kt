package com.credibanco.sdk.main.di.peripherals

import com.credibanco.sdk.datasource.ExternalPrintDataSourceGeneral
import com.credibanco.sdk.domain.di.Ingenico
import com.credibanco.sdk.main.data.repository.impl.ExternalPrintRepositoryIngenicoImpl
import com.credibanco.sdk.main.domain.repository.ExternalPrintRepositoryIngenico
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExternalPrintIngenicoModule {

    @Provides
    @Singleton
    @Ingenico
    fun provideExternalPrintRepositoryIngenico(externalPrintDataSourceIngenico: ExternalPrintDataSourceGeneral): ExternalPrintRepositoryIngenico =
        ExternalPrintRepositoryIngenicoImpl(externalPrintDataSourceIngenico)
}
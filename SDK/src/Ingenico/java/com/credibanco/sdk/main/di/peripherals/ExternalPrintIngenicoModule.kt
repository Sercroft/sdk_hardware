package com.credibanco.sdk.main.di.peripherals

import com.credibanco.sdk.datasource.ExternalPrintDataSourceGeneral
import com.credibanco.sdk.main.data.datasource.PrintDataSourceIngenico
import com.credibanco.sdk.main.data.repository.impl.ExternalPrintRepositoryIngenicoImpl
import com.credibanco.sdk.main.domain.repository.ExternalPrintRepositoryIngenico
import com.credibanco.sdk.main.domain.usecase.ExternalPrintUseCaseIngenico
import com.credibanco.sdk.main.domain.usecase.impl.ExternalPrintUseCaseIngenicoImpl
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
    fun provideExternalPrintRepositoryIngenico(externalPrintDataSourceIngenico: ExternalPrintDataSourceGeneral): ExternalPrintRepositoryIngenico =
        ExternalPrintRepositoryIngenicoImpl(externalPrintDataSourceIngenico)
}
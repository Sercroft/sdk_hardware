package com.credibanco.sdk.di.peripherals

import com.credibanco.sdk.data.repository.impl.ExternalPrintRepositoryNexgoImpl
import com.credibanco.sdk.datasource.PrintDataSource
import com.credibanco.sdk.domain.repository.ExternalPrintRepositoryNexgo
import com.credibanco.sdk.domain.usecase.ExternalPrintUseCaseNexgo
import com.credibanco.sdk.domain.usecase.impl.ExternalPrintUseCaseNexgoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExternalPrintModule {

    @Provides
    @Singleton
    fun provideExternalPrintRepositoryNexgo(externalPrintDataSource: PrintDataSource): ExternalPrintRepositoryNexgo =
        ExternalPrintRepositoryNexgoImpl(externalPrintDataSource)

    @Provides
    @Singleton
    fun provideExternalPrintUseCaseNexgo(externalPrintRepository: ExternalPrintRepositoryNexgo): ExternalPrintUseCaseNexgo =
        ExternalPrintUseCaseNexgoImpl(externalPrintRepository)
}

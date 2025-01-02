package com.credibanco.sdk.di.peripherals

import com.credibanco.sdk.data.repository.impl.ExternalPrintRepositoryNexgoImpl
import com.credibanco.sdk.datasource.ExternalPrintDataSourceGeneral
import com.credibanco.sdk.domain.ExternalPrintUseCaseGeneral
import com.credibanco.sdk.domain.repository.ExternalPrintRepositoryNexgo
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
    fun provideExternalPrintRepositoryNexgo(externalPrintDataSource: ExternalPrintDataSourceGeneral): ExternalPrintRepositoryNexgo {
        return ExternalPrintRepositoryNexgoImpl(externalPrintDataSource)
    }

    @Provides
    @Singleton
    fun provideExternalPrintUseCaseNexgo(externalPrintRepository: ExternalPrintRepositoryNexgo): ExternalPrintUseCaseGeneral {
        return ExternalPrintUseCaseNexgoImpl(externalPrintRepository)
    }
}

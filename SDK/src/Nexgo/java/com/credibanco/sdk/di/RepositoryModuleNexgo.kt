package com.credibanco.sdk.di

import com.credibanco.sdk.data.datasource.impl.PeripheralManagementDataSourceNexgoImpl
import com.credibanco.sdk.repository.PeripheralManagementRepository
import com.credibanco.sdk.repository.impl.PeripheralManagementRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModuleNexgo {

    @Provides
    @Singleton
    fun providePeripheralManagementRepository(
        peripheralManagementDataSourceImpl: PeripheralManagementDataSourceNexgoImpl
    ): PeripheralManagementRepository = PeripheralManagementRepositoryImpl(peripheralManagementDataSourceImpl)
}
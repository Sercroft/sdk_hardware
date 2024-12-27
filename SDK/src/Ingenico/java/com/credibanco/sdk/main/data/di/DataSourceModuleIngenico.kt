package com.credibanco.sdk.main.data.di

import android.content.Context
import com.credibanco.sdk.datasource.DeviceDataSource
import com.credibanco.sdk.datasource.PeripheralManagementDataSource
import com.credibanco.sdk.datasource.PrintDataSource
import com.credibanco.sdk.main.data.datasource.PeripheralManagementDataSourceIngenicoImpl
import com.credibanco.sdk.main.data.datasource.impl.DeviceDataSourceIngenicoImpl
import com.credibanco.sdk.main.data.datasource.impl.PrintDataSourceIngenicoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModuleIngenico {

    @Singleton
    @Provides
    fun contextProvider(@ApplicationContext appContext: Context): Context = appContext


    @Singleton
    @Provides
    fun printDataSourceProvider(): PrintDataSource =
        PrintDataSourceIngenicoImpl()

    @Singleton
    @Provides
    fun peripheralDataSourceProvider(): PeripheralManagementDataSource =
        PeripheralManagementDataSourceIngenicoImpl()

    @Singleton
    @Provides
    fun deviceDataSourceProvider(): DeviceDataSource =
        DeviceDataSourceIngenicoImpl()
}

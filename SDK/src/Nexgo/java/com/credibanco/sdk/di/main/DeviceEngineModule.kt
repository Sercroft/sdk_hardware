package com.credibanco.sdk.di.main

import android.content.Context
import com.credibanco.sdk.data.datasource.NFCTagDataSourceNexgo
import com.credibanco.sdk.data.datasource.PeripheralManagementDataSourceNexgo
import com.credibanco.sdk.data.datasource.impl.ExternalPrintDataSourceNexgoImpl
import com.credibanco.sdk.data.datasource.impl.NFCTagDataSourceNexgoImpl
import com.credibanco.sdk.data.datasource.impl.PeripheralManagementDataSourceNexgoImpl
import com.credibanco.sdk.data.datasource.impl.ScannerDataSourceNexgo
import com.credibanco.sdk.data.datasource.impl.ScannerDataSourceNexgoImpl
import com.credibanco.sdk.datasource.PeripheralManagementDataSource
import com.credibanco.sdk.datasource.PrintDataSource
import com.nexgo.oaf.apiv3.APIProxy
import com.nexgo.oaf.apiv3.DeviceEngine
import com.nexgo.oaf.apiv3.emv.EmvHandler2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DeviceEngineModule {

    @Provides
    @Singleton
    fun provideDeviceEngine(@ApplicationContext context: Context): DeviceEngine {
        return APIProxy.getDeviceEngine(context)
    }

    @Provides
    @Singleton
    fun createAPIProxy2Repository(@ApplicationContext context: Context): EmvHandler2 =
        APIProxy.getDeviceEngine(context).getEmvHandler2("app2")

    @Singleton
    @Provides
    fun contextProvider(@ApplicationContext appContext: Context): Context = appContext

    @Provides
    @Singleton
    fun provideExternalPrintDataSourceNexgo(deviceEngine: DeviceEngine): PrintDataSource =
        ExternalPrintDataSourceNexgoImpl(deviceEngine)

    @Provides
    @Singleton
    fun provideNFCTagDataSource(deviceEngine: DeviceEngine): NFCTagDataSourceNexgo =
        NFCTagDataSourceNexgoImpl(deviceEngine)

    @Provides
    @Singleton
    fun provideScannerDataSource(deviceEngine: DeviceEngine): ScannerDataSourceNexgo =
        ScannerDataSourceNexgoImpl(deviceEngine)

    @Singleton
    @Provides
    fun peripheralManagementDataSourceProvider(deviceEngine: DeviceEngine): PeripheralManagementDataSource =
        PeripheralManagementDataSourceNexgoImpl(deviceEngine)
}
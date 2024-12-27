package com.credibanco.sdk.di.main

import android.content.Context
import com.credibanco.sdk.data.datasource.PeripheralManagementDataSourceNexgo
import com.credibanco.sdk.data.datasource.impl.PeripheralManagementDataSourceNexgoImpl
import com.credibanco.sdk.main.ResultHardwareSDK
import com.credibanco.sdk.peripherals.BluetoothNexgo
import com.credibanco.sdk.peripherals.CameraNexgo
import com.credibanco.sdk.peripherals.PrintNexgo
import com.credibanco.sdk.peripherals.nfc.NFCNexgo
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
object MainModuleNexgo {

    @Singleton
    @Provides
    fun provideNfcNexgo(
        @ApplicationContext context: Context,
        result: ResultHardwareSDK
    ): NFCNexgo {
        return NFCNexgo(context, result)
    }

    @Singleton
    @Provides
    fun providePrintNexgo(
        @ApplicationContext context: Context,
        result: ResultHardwareSDK
    ): PrintNexgo {
        return PrintNexgo(context, result)
    }

    @Singleton
    @Provides
    fun provideCameraNexgo(
        @ApplicationContext context: Context,
        result: ResultHardwareSDK
    ): CameraNexgo {
        return CameraNexgo(context, result)
    }

    @Singleton
    @Provides
    fun provideBluetoothNexgo(
        @ApplicationContext context: Context,
        result: ResultHardwareSDK
    ): BluetoothNexgo {
        return BluetoothNexgo(context, result)
    }
}
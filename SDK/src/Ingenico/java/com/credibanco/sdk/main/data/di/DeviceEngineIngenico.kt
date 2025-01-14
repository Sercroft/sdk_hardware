package com.credibanco.sdk.main.data.di

import android.content.Context
import com.credibanco.sdk.main.helper.UDeviceServiceManager
import com.usdk.apiservice.aidl.UDeviceService
import com.usdk.apiservice.aidl.device.UDeviceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DeviceEngineIngenico {

    @Provides
    @Singleton
    fun provideUDeviceServiceManager(@ApplicationContext context: Context): UDeviceServiceManager {
        return UDeviceServiceManager(context)
    }

    @Provides
    @Singleton
    fun provideUDeviceManager(deviceService: UDeviceService): UDeviceManager {
        return UDeviceManager.Stub.asInterface(deviceService.deviceManager)
    }
}
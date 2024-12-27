package com.credibanco.sdk.di

import com.credibanco.sdk.main.IntegrationPeripheralsNexgoSDK
import com.credibanco.sdk.main.IntegrationPeripheralsSDKNexgoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object IntegrationPeripheralsModule {
    @Provides
    fun provideIntegrationPeripheralsNexgoSDK(): IntegrationPeripheralsNexgoSDK {
        return IntegrationPeripheralsSDKNexgoImpl()
    }
}
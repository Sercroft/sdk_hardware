package com.credibanco.sdk.main.di.peripherals

import android.content.Context
import com.credibanco.sdk.datasource.NFCTagDataSourceGeneral
import com.credibanco.sdk.main.data.datasource.impl.NFCTagDataSourceIngenicoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NFCIngenicoModule {

    @Provides
    @Singleton
    fun provideNFCTagDataSource(@ApplicationContext context: Context): NFCTagDataSourceGeneral =
        NFCTagDataSourceIngenicoImpl(context)
}
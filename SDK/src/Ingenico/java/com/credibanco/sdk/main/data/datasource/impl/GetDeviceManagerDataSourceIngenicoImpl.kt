package com.credibanco.sdk.main.data.datasource.impl

import android.os.IBinder
import android.os.RemoteException
import androidx.lifecycle.LiveData
import com.credibanco.sdk.main.data.datasource.GetDeviceManagerDataSourceIngenico
import com.usdk.apiservice.aidl.UDeviceService
import com.usdk.apiservice.aidl.device.UDeviceManager
import javax.inject.Inject

class GetDeviceManagerDataSourceIngenicoImpl @Inject constructor(
    private val deviceService: UDeviceService
) : GetDeviceManagerDataSourceIngenico {

    @Throws(Exception::class)
    override fun getDeviceManager(): UDeviceManager? {
        val iBinder = object : IBinderCreator() {
            @Throws(RemoteException::class)
            override fun create(): IBinder {
                return deviceService.deviceManager!!
            }
        }.start()
        return UDeviceManager.Stub.asInterface(iBinder)
    }

    internal abstract class IBinderCreator {
        @Throws(java.lang.IllegalStateException::class)
        fun start(): IBinder {
            return create()
        }

        @Throws(RemoteException::class)
        abstract fun create(): IBinder
    }
}
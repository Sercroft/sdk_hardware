package com.credibanco.sdk.main.data.datasource

import com.usdk.apiservice.aidl.device.UDeviceManager

interface GetDeviceManagerDataSourceIngenico {
    @Throws(Exception::class)
    fun getDeviceManager(): UDeviceManager?
}

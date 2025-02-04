package com.credibanco.sdk.main.data.datasource.impl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.Resources
import android.os.Binder
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.credibanco.sdk.datasource.ExternalPrintDataSourceGeneral
import com.credibanco.sdk.domain.KernelAvailableStateCallBackObjectSDK
import com.credibanco.sdk.main.util.ConstantsIngenico.TAG
import com.usdk.apiservice.aidl.DeviceServiceData
import com.usdk.apiservice.aidl.UDeviceService
import com.usdk.apiservice.aidl.printer.AlignMode
import com.usdk.apiservice.aidl.printer.OnPrintListener
import com.usdk.apiservice.aidl.printer.PrinterError
import com.usdk.apiservice.aidl.printer.UPrinter
import javax.inject.Inject

class ExternalPrintDataSourceIngenicoImpl @Inject constructor(
    private val context: Context
) : ExternalPrintDataSourceGeneral, ServiceConnection {

    private var kernelRunState = false
    private var kernelAvailableState = false
    private lateinit var kernelAvailableCallback: (callbackObjectType: KernelAvailableStateCallBackObjectSDK) -> Unit

    private var retry = 0
    private val MAX_RETRY_COUNT = 3
    private val RETRY_INTERVALS: Long = 3000
    private var isKernelFunctionCall: Boolean = false

    private var deviceService: UDeviceService? = null
    private var printer: UPrinter? = null
    private var currentSheetNo: Int = 1
    private var totalSheets: Int = 1

    init {
        initializePrinter()
    }


    private fun initializePrinter() {
        try {
            deviceService?.let {
                printer = UPrinter.Stub.asInterface(it.getPrinter())
                Log.i(TAG, "Impresora inicializada correctamente.")
            } ?: run {
                Log.e(TAG, "DeviceService no inicializado.")
            }
        } catch (e: RemoteException) {
            Log.e(TAG, "Error al inicializar la impresora: ${e.message}")
        }
    }

    private fun startPrinting(linesToPrint: ArrayList<String>, letterSpacing: Int?, grayLevel: String?) {
        if (currentSheetNo > totalSheets) return

        printer?.apply {
            try {
                linesToPrint.forEach { line ->
                    addText(AlignMode.LEFT, line)
                }

                feedLine(5)


                startPrint(object : OnPrintListener.Stub() {
                    override fun onFinish() {
                        currentSheetNo++
                        startPrinting(linesToPrint, letterSpacing, grayLevel)  // Continuar con la siguiente hoja
                    }

                    override fun onError(error: Int) {
                        Log.e(TAG, "=> Error en la impresión: ${getErrorDetail(error)}")
                    }
                })
            } catch (e: RemoteException) {
                Log.e(TAG, "Error al intentar imprimir: ${e.message}")
            }
        } ?: run {
            Log.e(TAG, "Impresora no inicializada.")
        }
    }

    private fun getErrorDetail(error: Int): String {
        return when (error) {
            PrinterError.ERROR_NOT_INIT -> "ERROR_NOT_INIT"
            PrinterError.ERROR_PARAM -> "ERROR_PARAM"
            PrinterError.ERROR_BMBLACK -> "ERROR_BMBLACK"
            PrinterError.ERROR_BUFOVERFLOW -> "ERROR_BUFOVERFLOW"
            PrinterError.ERROR_BUSY -> "ERROR_BUSY"
            PrinterError.ERROR_COMMERR -> "ERROR_COMMERR"
            PrinterError.ERROR_CUTPOSITIONERR -> "ERROR_CUTPOSITIONERR"
            PrinterError.ERROR_HARDERR -> "ERROR_HARDERR"
            PrinterError.ERROR_LIFTHEAD -> "ERROR_LIFTHEAD"
            PrinterError.ERROR_LOWTEMP -> "ERROR_LOWTEMP"
            PrinterError.ERROR_LOWVOL -> "ERROR_LOWVOL"
            PrinterError.ERROR_MOTORERR -> "ERROR_MOTORERR"
            PrinterError.ERROR_NOBM -> "ERROR_NOBM"
            PrinterError.ERROR_OVERHEAT -> "ERROR_OVERHEAT"
            PrinterError.ERROR_PAPERENDED -> "ERROR_PAPERENDED"
            PrinterError.ERROR_PAPERENDING -> "ERROR_PAPERENDING"
            PrinterError.ERROR_PAPERJAM -> "ERROR_PAPERJAM"
            PrinterError.ERROR_PENOFOUND -> "ERROR_PENOFOUND"
            PrinterError.ERROR_WORKON -> "ERROR_WORKON"
            else -> "Error desconocido"
        }
    }

    override suspend fun isKernelRun(): Boolean {

        if (kernelRunState  && deviceService != null){
            return kernelRunState
        }else{
            //startkernel to do
            retry = 0
            return bindService()
        }

    }

    private fun bindService(): Boolean {
        if (kernelRunState) {
            return kernelRunState
        }
        val service = Intent("com.usdk.apiservice")
        service.setPackage("com.usdk.apiservice")
        kernelRunState= context.bindService(service, this, Context.BIND_AUTO_CREATE)


        // If the binding fails, it is rebinded
        if (!kernelRunState && retry++ < MAX_RETRY_COUNT) {
            Handler().postDelayed({ bindService() }, RETRY_INTERVALS)
        }
        return kernelRunState
    }


    // Método que se invoca desde el DataSource
    override suspend fun invoke(
        linesToPrint: ArrayList<String>,
        packageName: String,
        resources: Resources,
        typeface: String?,
        letterSpacing: Int?,
        grayLevel: String?
    ): Int {
        totalSheets = linesToPrint.size
        try {
            startPrinting(linesToPrint, letterSpacing, grayLevel)
        } catch (e: Exception) {
            Log.e(TAG, "Excepción durante la impresión: ${e.message}")
        }
        return 0
    }

    override fun isKernelAvailableState(kernelAvailableCallback: (callbackObjectType: KernelAvailableStateCallBackObjectSDK) -> Unit) {
        this.kernelAvailableCallback =  kernelAvailableCallback
        isKernelFunctionCall = true
        if(this.kernelRunState && this.kernelAvailableState) kernelAvailableCallback.invoke(KernelAvailableStateCallBackObjectSDK.AvailableKernel(KernelAvailableStateCallBackObjectSDK.AVAILABLE_KERNEL))
    }


    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

        try {
            deviceService = UDeviceService.Stub.asInterface(service)
            register(true)
            kernelRunState = printer != null
        } catch (e: java.lang.Exception) {
            kernelRunState = false
        }

    }
    override fun onServiceDisconnected(name: ComponentName?) {
        TODO("Not yet implemented")
    }

    @Throws(java.lang.IllegalStateException::class)
    private fun register(useEpayModule: Boolean) {
        try {
            val param = Bundle()
            param.putBoolean(DeviceServiceData.USE_EPAY_MODULE, useEpayModule)
            deviceService?.register(param, Binder())
            kernelAvailableState = true
            if(isKernelFunctionCall) kernelAvailableCallback.invoke(
                KernelAvailableStateCallBackObjectSDK.AvailableKernel(KernelAvailableStateCallBackObjectSDK.AVAILABLE_KERNEL))
        } catch (e: RemoteException) {
            e.printStackTrace()
            throw java.lang.IllegalStateException(e.message)
        } catch (e: SecurityException) {
            e.printStackTrace()
            throw java.lang.IllegalStateException(e.message)
        }
    }

}
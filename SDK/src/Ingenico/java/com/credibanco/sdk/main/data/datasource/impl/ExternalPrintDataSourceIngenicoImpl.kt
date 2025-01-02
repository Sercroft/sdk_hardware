package com.credibanco.sdk.main.data.datasource.impl

import android.content.res.Resources
import android.os.RemoteException
import android.util.Log
import com.credibanco.sdk.datasource.ExternalPrintDataSourceGeneral
import com.credibanco.sdk.main.util.ConstantsIngenico.TAG
import com.usdk.apiservice.aidl.printer.ASCScale
import com.usdk.apiservice.aidl.printer.ASCSize
import com.usdk.apiservice.aidl.printer.AlignMode
import com.usdk.apiservice.aidl.printer.HZScale
import com.usdk.apiservice.aidl.printer.HZSize
import com.usdk.apiservice.aidl.printer.OnPrintListener
import com.usdk.apiservice.aidl.printer.PrinterError
import com.usdk.apiservice.aidl.printer.UPrinter
import javax.inject.Inject

class ExternalPrintDataSourceIngenicoImpl @Inject constructor(): ExternalPrintDataSourceGeneral {

    private val printer: UPrinter? = null
    private var currentSheetNo: Int = 1
    private var totalSheets: Int = 1

    override suspend fun invoke(
        linesToPrint: ArrayList<String>,
        packageName: String,
        resources: Resources,
        typeface: String?,
        letterSpacing: Int?,
        grayLevel: String?
    ): Int {
        totalSheets = linesToPrint.size
        try{
            startPrinter(linesToPrint, letterSpacing, grayLevel)
        }catch(e: Exception){
            handleException(e)
        }

        return 0
    }

    private fun startPrinter( linesToPrint: ArrayList<String>, letterSpacing: Int?, grayLevel: String?) {
        if (currentSheetNo > totalSheets) {
            return
        }

        printer?.apply {
            try {
                linesToPrint.forEach { line ->
                    addText(AlignMode.LEFT, line)
                }

                // Configurar escala y tamaño de texto
                setAscScale(ASCScale.SC1x1)
                setAscSize(ASCSize.DOT24x12)
                addText(AlignMode.LEFT, "Escala: ${ASCScale.SC1x1}, Tamaño: ${ASCSize.DOT24x12}")

                setHzScale(HZScale.SC1x1)
                setHzSize(HZSize.DOT24x24)
                addText(AlignMode.LEFT, "Escala: ${HZScale.SC1x1}, Tamaño: ${HZSize.DOT24x24}")

                // Gray level
                grayLevel?.toInt()?.let {
                    setPrnGray(it)
                    addText(HZSize.DOT24x24, "Nivel de gris: $it")
                }

//                printConfig.qrCode?.let {
//                    addQrCode(AlignMode.CENTER, 240, ECLevel.ECLEVEL_H, it.content)
//                }
//
//                printConfig.barCode?.let {
//                    addBarCode(AlignMode.CENTER, 2, 48, it.content)
//                }
//
//                printConfig.bmpPath?.let {
//                    FileIngenicoUtil.copyFileToSD(context, Environment.getExternalStorageDirectory().path, it)
//                    addBmpPath(0, FactorMode.BMP1X1, Environment.getExternalStorageDirectory().path + "/$it")
//                }

                // Alimentar la impresora
                feedLine(5)

                // Iniciar la impresión
                startPrint(object : OnPrintListener.Stub() {
                    override fun onFinish() {
                        currentSheetNo++
                        startPrinter(linesToPrint, letterSpacing, grayLevel) // Llama al método para imprimir la siguiente hoja
                    }

                    override fun onError(error: Int) {
                        Log.e(TAG, "=> onError: ${getErrorDetail(error)}")
                    }
                })
            } catch (e: RemoteException) {
                Log.e(TAG, "Error al imprimir: ${e.message}")
            }
        } ?: run {
            Log.e(TAG, "Impresora no inicializada.")
        }
    }

    private fun getErrorDetail(error: Int): String {
        val message: String
        val errorMessage = getErrorDetail(error)

        when (error) {
            PrinterError.ERROR_NOT_INIT -> message = "ERROR_NOT_INIT"
            PrinterError.ERROR_PARAM -> message = "ERROR_PARAM"
            PrinterError.ERROR_BMBLACK -> message = "ERROR_BMBLACK"
            PrinterError.ERROR_BUFOVERFLOW -> message = "ERROR_BUFOVERFLOW"
            PrinterError.ERROR_BUSY -> message = "ERROR_BUSY"
            PrinterError.ERROR_COMMERR -> message = "ERROR_COMMERR"
            PrinterError.ERROR_CUTPOSITIONERR -> message = "ERROR_CUTPOSITIONERR"
            PrinterError.ERROR_HARDERR -> message = "ERROR_HARDERR"
            PrinterError.ERROR_LIFTHEAD -> message = "ERROR_LIFTHEAD"
            PrinterError.ERROR_LOWTEMP -> message = "ERROR_LOWTEMP"
            PrinterError.ERROR_LOWVOL -> message = "ERROR_LOWVOL"
            PrinterError.ERROR_MOTORERR -> message = "ERROR_MOTORERR"
            PrinterError.ERROR_NOBM -> message = "ERROR_NOBM"
            PrinterError.ERROR_OVERHEAT -> message = "ERROR_OVERHEAT"
            PrinterError.ERROR_PAPERENDED -> message = "ERROR_PAPERENDED"
            PrinterError.ERROR_PAPERENDING -> message = "ERROR_PAPERENDING"
            PrinterError.ERROR_PAPERJAM -> message = "ERROR_PAPERJAM"
            PrinterError.ERROR_PENOFOUND -> message = "ERROR_PENOFOUND"
            PrinterError.ERROR_WORKON -> message = "ERROR_WORKON"
            else -> message = errorMessage
        }
        return message
    }

    private fun handleException(e: Exception) {
        Log.e(TAG, "Excepción durante la impresión: ${e.message}")
    }

}
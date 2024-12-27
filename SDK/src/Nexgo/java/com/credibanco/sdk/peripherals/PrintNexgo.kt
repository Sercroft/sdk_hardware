package com.credibanco.sdk.peripherals

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import android.widget.Toast
import com.credibanco.sdk.R
import com.credibanco.sdk.main.IntegrationPeripheralsNexgoSDK
import com.credibanco.sdk.main.ResultHardwareSDK
import com.credibanco.sdk.util.Constants
import com.credibanco.sdk.util.Constants.N6
import com.credibanco.sdk.util.Constants.N86
import com.credibanco.sdk.util.Constants.UN20
import com.credibanco.sdk.util.FileUtil
import java.io.ByteArrayOutputStream
import java.io.File

class PrintNexgo(
    private val context: Context,
    private val result: ResultHardwareSDK
) {
    fun startPrint(hashCode: String, valuesToSend: ArrayList<String>){
        val model = Build.MODEL

        when (model) {
            UN20, N6 -> {
                Toast.makeText(context, "Función no permitida", Toast.LENGTH_LONG).show()
            }

            N86 -> {
                val imageName = "logo"
                val text = "Prueba para imprimir"
                val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.qr)
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val b = baos.toByteArray()
                val encodedImage = Base64.encodeToString(b, Base64.DEFAULT)

                val dirName = "images"
                val profileDir: File = FileUtil.makeAndGetProfileDirectory(dirName)
                FileUtil.writeToFile(profileDir, "qr", encodedImage)

                //CON FONT_BIG = 24 CARACTERES POR LINEA
                //CON FONT_NORMAL = 32 CARACTERES POR LINEA
                //CON FONT_IOU = 48 CARACTERES POR LINEA

                /*val valuesToSend = ArrayList<String>().apply {
                    add("${Constants.IMAGE}, $imageName, ${Constants.ALIGN_RIGHT}")
                    add("${Constants.TEXT}, $text, ${Constants.FONT_BIG}, ${Constants.ALIGN_RIGHT}")
                    add("${Constants.TEXT}, $text, ${Constants.FONT_BIG}, ${Constants.ALIGN_RIGHT}")
                    add("${Constants.TEXT}, $text, ${Constants.FONT_BIG}, ${Constants.ALIGN_RIGHT}")
                    add("${Constants.TEXT}, $text, ${Constants.FONT_BIG}, ${Constants.ALIGN_RIGHT}")
                    add("${Constants.TEXT}, $text, ${Constants.FONT_BIG}, ${Constants.ALIGN_RIGHT}")
                    add("${Constants.TEXT}, $text, ${Constants.FONT_BIG}, ${Constants.ALIGN_RIGHT}")
                    add("${Constants.TEXT}, $text, ${Constants.FONT_BIG}, ${Constants.ALIGN_RIGHT}")
                    add("${Constants.QR}, ${Constants.FONT_NORMAL}, ${Constants.ALIGN_CENTER}")
                }*/

                IntegrationPeripheralsNexgoSDK.getSmartPosInstancePeripheralsNexgo().startPrint(
                    context,
                    Constants.TYPEFACE_DEFAULT,
                    6,
                    Constants.GRAY_LEVEL_2,
                    hashCode,
                    valuesToSend,
                    resultHardwareSDK = result
                )
            }
        }
    }
}
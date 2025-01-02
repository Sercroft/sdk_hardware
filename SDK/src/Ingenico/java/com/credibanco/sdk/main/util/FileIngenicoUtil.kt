package com.credibanco.sdk.main.util

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object FileIngenicoUtil {

    fun copyFilesToSD(context: Context, path: String, fileNames: Array<String>?) {
        fileNames?.forEach { fileName ->
            copyFileToSD(context, path, fileName)
        }
    }


    fun copyFileToSD(context: Context, path: String, assertFileName: String?) {
        if (assertFileName == null) return

        var inputStream: InputStream? = null
        var outputStream: FileOutputStream? = null
        try {
            inputStream = context.assets.open(assertFileName)
            val parent = File(path)
            if (!parent.exists()) {
                parent.mkdirs()
            }

            val file = File(path, assertFileName)
            if (file.exists()) {
                return
            }

            outputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var len: Int
            while (inputStream.read(buffer).also { len = it } != -1) {
                outputStream.write(buffer, 0, len)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                outputStream?.close()
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun saveDataToFile(path: String, fileName: String, data: ByteArray) {
        var outputStream: FileOutputStream? = null
        try {
            val parent = File(path)
            if (!parent.exists()) {
                parent.mkdirs()
            }

            val file = File(path, fileName)
            outputStream = FileOutputStream(file)
            outputStream.write(data)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun readAssetsFile(context: Context, assetsPath: String): ByteArray {
        var inputStream: InputStream? = null
        return try {
            inputStream = context.assets.open(assetsPath)
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            buffer
        } catch (e: Exception) {
            e.printStackTrace()
            byteArrayOf()
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
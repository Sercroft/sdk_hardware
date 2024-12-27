package com.credibanco.sdk.util

import android.os.Build
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter

object FileUtil {

    fun makeAndGetProfileDirectory(dirName: String?): File {
        // Determine the profile directory
        val profileDirectory: File = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            File(Environment.getStorageDirectory(), dirName)
        } else {
            File(Environment.getExternalStorageDirectory(), dirName)
        }

        // Creates the directory if not present yet
        profileDirectory.mkdir()
        return profileDirectory
    }

    fun writeToFile(directory: File, file: String, data: String) {
        val qrFile = File(directory, file)
        var outputFile: FileOutputStream? = null
        val outputStreamWriter: OutputStreamWriter

        try {
            outputFile = FileOutputStream(qrFile)
        } catch (e: FileNotFoundException) {
            Log.d("TAG-1", "Failed to open file", e)
            e.printStackTrace()
        }
        try {
            outputStreamWriter = OutputStreamWriter(outputFile)
            outputStreamWriter.write(data)
            outputStreamWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
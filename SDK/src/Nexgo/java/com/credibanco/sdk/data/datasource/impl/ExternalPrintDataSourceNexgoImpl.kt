package com.credibanco.sdk.data.datasource.impl

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Build
import android.os.Environment
import android.util.Base64
import com.credibanco.sdk.data.mapper.NexgoPrintResponseMapper
import com.credibanco.sdk.datasource.PrintDataSource
import com.credibanco.sdk.util.NexgoPrintingConstants.ALIGN_CENTER
import com.credibanco.sdk.util.NexgoPrintingConstants.ALIGN_RIGHT
import com.credibanco.sdk.util.NexgoPrintingConstants.DEFAULT_LETTER_SPACING
import com.credibanco.sdk.util.NexgoPrintingConstants.FONT_BIG
import com.credibanco.sdk.util.NexgoPrintingConstants.FONT_IOU
import com.credibanco.sdk.util.NexgoPrintingConstants.GRAY_LEVEL_0
import com.credibanco.sdk.util.NexgoPrintingConstants.GRAY_LEVEL_1
import com.credibanco.sdk.util.NexgoPrintingConstants.GRAY_LEVEL_3
import com.credibanco.sdk.util.NexgoPrintingConstants.GRAY_LEVEL_4
import com.credibanco.sdk.util.NexgoPrintingConstants.IMAGE_TYPE
import com.credibanco.sdk.util.NexgoPrintingConstants.LETTER_IS_BOLD
import com.credibanco.sdk.util.NexgoPrintingConstants.PRINTER_SUCCESS
import com.credibanco.sdk.util.NexgoPrintingConstants.QR_TYPE
import com.credibanco.sdk.util.NexgoPrintingConstants.TEXT_DOUBLE_TYPE
import com.credibanco.sdk.util.NexgoPrintingConstants.TEXT_TYPE
import com.credibanco.sdk.util.NexgoPrintingConstants.TYPEFACE_DEFAULT_BOLD
import com.credibanco.sdk.util.NexgoPrintingConstants.TYPEFACE_MONOSPACE
import com.credibanco.sdk.util.NexgoPrintingConstants.TYPEFACE_SANS_SERIF
import com.credibanco.sdk.util.NexgoPrintingConstants.TYPEFACE_SERIF
import com.nexgo.oaf.apiv3.DeviceEngine
import com.nexgo.oaf.apiv3.device.printer.AlignEnum
import com.nexgo.oaf.apiv3.device.printer.DotMatrixFontEnum
import com.nexgo.oaf.apiv3.device.printer.FontEntity
import com.nexgo.oaf.apiv3.device.printer.GrayLevelEnum
import com.nexgo.oaf.apiv3.device.printer.LineOptionEntity
import com.nexgo.oaf.apiv3.device.printer.Printer
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class ExternalPrintDataSourceNexgoImpl @Inject constructor(
    private val deviceEngine: DeviceEngine
) : PrintDataSource {

    private lateinit var printer: Printer

    companion object {
        private const val LINE_ELEMENT_DELIMITER = ","
        private const val TYPE_INDEX = 0
        private const val IMAGE_NAME_INDEX = 1
        private const val TEXT_INDEX = 1
        private const val TEXT_LEFT_INDEX_FOR_TEXT_DOUBLE = 1
        private const val TEXT_RIGHT_INDEX_FOR_TEXT_DOUBLE = 2
        private const val FONT_SIZE_INDEX = 2
        private const val ALIGNMENT_INDEX_FOR_IMAGE = 2
        private const val ALIGNMENT_INDEX_FOR_TEXT = 3
        private const val FONT_SIZE_INDEX_FOR_TEXT_DOUBLE = 3
        private const val STYLE_INDEX_FOR_TEXT = 4
        private const val STYLE_INDEX_FOR_TEXT_DOUBLE = 4
    }

    fun setup(
        typeface: String?,
        letterSpacing: Int?,
        grayLevel: String?
    ) {
        printer = deviceEngine.printer
        printer.setTypeface(getTypeFace(typeface))
        printer.setLetterSpacing(letterSpacing ?: DEFAULT_LETTER_SPACING)
        printer.setGray(getGrayLevelEnum(grayLevel))
    }
    override suspend fun invoke(
        linesToPrint: ArrayList<String>,
        packageName: String,
        resources: Resources,
        typeface: String?,
        letterSpacing: Int?,
        grayLevel: String?
    ): Int {
        setup(typeface, letterSpacing, grayLevel)

        for (line in linesToPrint) {
            val lineElements = getLineElements(line)

            when (lineElements[TYPE_INDEX]) {
                IMAGE_TYPE -> {
                    val imageName = lineElements[IMAGE_NAME_INDEX]
                    val alignment = getAlignment(lineElements[ALIGNMENT_INDEX_FOR_IMAGE])
                    val resId: Int = resources.getIdentifier(imageName, "drawable", packageName)
                    val bitmap = BitmapFactory.decodeResource(resources, resId)

                    printer.appendImage(bitmap, alignment)
                }

                QR_TYPE -> {
                    var dirPath: String?
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        dirPath = Environment.getStorageDirectory().toString() + "/images"
                    }else{
                        dirPath = Environment.getExternalStorageDirectory().toString() + "/images"
                    }

                    val dir = dirPath?.let { File(it) }
                    val filePath = "$dirPath/qr"
                    val alignment = getAlignment(lineElements[ALIGNMENT_INDEX_FOR_IMAGE])
                    val file = File(filePath)
                    val contents = file.readText()
                    printer.appendImage(contents.toBitmap(), alignment)
                    if (dir != null && deviceEngine.printer.status == PRINTER_SUCCESS) {
                        deleteRecursive(dir)
                    }
                }

                TEXT_TYPE -> {
                    val text = lineElements[TEXT_INDEX]
                    val fontSize = getFontSize(lineElements[FONT_SIZE_INDEX])
                    val alignment = getAlignment(lineElements[ALIGNMENT_INDEX_FOR_TEXT])
                    var lineOption: LineOptionEntity? = null

                    if (lineElements.getOrNull(STYLE_INDEX_FOR_TEXT) == LETTER_IS_BOLD) {
                        lineOption = LineOptionEntity().apply {
                            this.isBold = true
                        }
                    }

                    printer.appendPrnStr(text, fontSize, alignment, lineOption)
                }

                TEXT_DOUBLE_TYPE -> {
                    val textLeft = lineElements[TEXT_LEFT_INDEX_FOR_TEXT_DOUBLE]
                    val textRight = lineElements[TEXT_RIGHT_INDEX_FOR_TEXT_DOUBLE]
                    val fontSize = getFontSize(lineElements[FONT_SIZE_INDEX_FOR_TEXT_DOUBLE])
                    var lineOption: LineOptionEntity? = null

                    if (lineElements.getOrNull(STYLE_INDEX_FOR_TEXT_DOUBLE) == LETTER_IS_BOLD) {
                        lineOption = LineOptionEntity().apply {
                            this.isBold = true
                        }
                    }

                    printer.appendPrnStr(textLeft, textRight, fontSize, lineOption)
                }
            }
        }

        return suspendCoroutine { cont ->
            printer.startPrint(true) { printResponseCode ->
                val mappedResponseCode = NexgoPrintResponseMapper().transformFrom(printResponseCode)
                cont.resumeWith(Result.success(mappedResponseCode))
            }
        }
    }

    private fun getFontSize(fontSize: String): FontEntity {
        return when(fontSize) {
            FONT_BIG -> FontEntity(DotMatrixFontEnum.CH_SONG_24X24, DotMatrixFontEnum.ASC_SONG_16X24)
            FONT_IOU -> FontEntity(DotMatrixFontEnum.CH_SONG_20X20, DotMatrixFontEnum.ASC_SONG_8X16)
            else -> FontEntity(DotMatrixFontEnum.CH_SONG_20X20, DotMatrixFontEnum.ASC_SONG_12X24)
        }
    }

    private fun getAlignment(alignment: String?): AlignEnum {
        return when(alignment) {
            ALIGN_RIGHT -> AlignEnum.RIGHT
            ALIGN_CENTER -> AlignEnum.CENTER
            else -> AlignEnum.LEFT
        }
    }

    private fun getLineElements(line: String) = line.trim().split(LINE_ELEMENT_DELIMITER)

    private fun getTypeFace(typeFace: String?): Typeface {
        return when(typeFace) {
            TYPEFACE_DEFAULT_BOLD -> Typeface.DEFAULT_BOLD
            TYPEFACE_MONOSPACE -> Typeface.MONOSPACE
            TYPEFACE_SANS_SERIF -> Typeface.SANS_SERIF
            TYPEFACE_SERIF -> Typeface.SERIF
            else -> Typeface.DEFAULT
        }
    }

    private fun getGrayLevelEnum(grayLevel: String?): GrayLevelEnum {
        return when(grayLevel) {
            GRAY_LEVEL_0 -> GrayLevelEnum.LEVEL_0
            GRAY_LEVEL_1 -> GrayLevelEnum.LEVEL_1
            GRAY_LEVEL_3 -> GrayLevelEnum.LEVEL_3
            GRAY_LEVEL_4 -> GrayLevelEnum.LEVEL_4
            else -> GrayLevelEnum.LEVEL_2
        }
    }

    fun String.toBitmap(): Bitmap? {
        Base64.decode(this, Base64.DEFAULT).apply {
            return BitmapFactory.decodeByteArray(this, 0, size)
        }
    }

    fun deleteRecursive(fileOrDirectory: File) {
        if (fileOrDirectory.isDirectory) for (child in fileOrDirectory.listFiles()) deleteRecursive(
            child
        )
        fileOrDirectory.delete()
    }
}
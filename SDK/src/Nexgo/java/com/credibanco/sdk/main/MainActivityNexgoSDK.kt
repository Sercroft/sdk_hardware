package com.credibanco.sdk.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Organization.TITLE
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.credibanco.sdk.util.Constants.BLUETOOTH
import com.credibanco.sdk.util.Constants.BLUETOOTH_ACTIVITY_PERIPHERAL
import com.credibanco.sdk.util.Constants.CAMERA
import com.credibanco.sdk.util.Constants.CAMERA_ACTIVITY_PERIPHERAL
import com.credibanco.sdk.util.Constants.GRAY_LEVEL
import com.credibanco.sdk.util.Constants.HASH_CODE
import com.credibanco.sdk.util.Constants.LETTER_SPACING
import com.credibanco.sdk.util.Constants.NFC
import com.credibanco.sdk.util.Constants.NFC_ACTIVITY_PERIPHERAL
import com.credibanco.sdk.util.Constants.PACKAGE_NAME
import com.credibanco.sdk.util.Constants.PACKAGE_SMARTPOS_PERIPHERALS
import com.credibanco.sdk.util.Constants.PRINT
import com.credibanco.sdk.util.Constants.PRINT_ACTIVITY_PERIPHERAL
import com.credibanco.sdk.util.Constants.RESULT_BLUETOOTH_CODE
import com.credibanco.sdk.util.Constants.RESULT_CAMERA_CODE
import com.credibanco.sdk.util.Constants.RESULT_NFC_CODE
import com.credibanco.sdk.util.Constants.RESULT_PRINT_CODE
import com.credibanco.sdk.util.Constants.SCANTIP
import com.credibanco.sdk.util.Constants.SHOWBACK
import com.credibanco.sdk.util.Constants.SHOWBAR
import com.credibanco.sdk.util.Constants.SHOWMENU
import com.credibanco.sdk.util.Constants.SHOWSWITCH
import com.credibanco.sdk.util.Constants.SHOWTITLE
import com.credibanco.sdk.util.Constants.STATE_BLUETOOTH
import com.credibanco.sdk.util.Constants.TIPSIZE
import com.credibanco.sdk.util.Constants.TITLESIZE
import com.credibanco.sdk.util.Constants.TYPEFACE
import com.credibanco.sdk.util.Constants.TYPE_INTEGRATION
import com.credibanco.sdk.util.Constants.UID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class MainActivityNexgoSDK : AppCompatActivity() {

    private val startForResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCodes = listOf(
                RESULT_NFC_CODE,
                RESULT_CAMERA_CODE,
                RESULT_PRINT_CODE,
                RESULT_BLUETOOTH_CODE
            )
            val resultCode = result.resultCode

            if(resultCode in resultCodes) {
                ResultHardwareSDK.information?.let {
                    ResultHardwareSDK.getSmartPosInstancePeripherals(
                        it
                    ).resultActivity(result)
                }
            }

            ResultHardwareSDK.information = null
            finish()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addInformationToViewModel(intent)
    }

    private fun addInformationToViewModel(intent: Intent) {
        intent.let { intent ->
            val packageName: String? = intent.getStringExtra(PACKAGE_NAME)
            val hashCode: String? = intent.getStringExtra(HASH_CODE)
            val typeIntegration: String? = intent.getStringExtra(TYPE_INTEGRATION)

            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SENDTO
            shareIntent.type = "text/plain"
            shareIntent.putExtra(HASH_CODE, hashCode)
            shareIntent.putExtra(PACKAGE_NAME, packageName)

            when(typeIntegration) {

                NFC -> {
                    val uid: Boolean = intent.getBooleanExtra(UID, true)
                    shareIntent.putExtra(UID, uid)
                    shareIntent.setPackage(PACKAGE_SMARTPOS_PERIPHERALS)
                    shareIntent.component = ComponentName(
                        PACKAGE_SMARTPOS_PERIPHERALS,
                        NFC_ACTIVITY_PERIPHERAL
                    )
                }

                PRINT -> {
                    val typeFace        : String? = intent.getStringExtra(TYPEFACE)
                    val letterSpacing   : String? = intent.getStringExtra(LETTER_SPACING)
                    val grayLevel       : String? = intent.getStringExtra(GRAY_LEVEL)
                    val valuesToSend    : ArrayList<String>? = intent.getStringArrayListExtra(Intent.EXTRA_STREAM)

                    shareIntent.putExtra(TYPEFACE, typeFace)
                    shareIntent.putExtra(LETTER_SPACING, letterSpacing?.toInt())
                    shareIntent.putExtra(GRAY_LEVEL, grayLevel)
                    shareIntent.putStringArrayListExtra(Intent.EXTRA_STREAM, valuesToSend)
                    shareIntent.setPackage(PACKAGE_SMARTPOS_PERIPHERALS)
                    shareIntent.component = ComponentName(
                        PACKAGE_SMARTPOS_PERIPHERALS,
                        PRINT_ACTIVITY_PERIPHERAL
                    )
                }

                CAMERA -> {
                    val showBar     : String? = intent.getStringExtra(SHOWBAR)
                    val showBack    : String? = intent.getStringExtra(SHOWBACK)
                    val showTitle   : String? = intent.getStringExtra(SHOWTITLE)
                    val showSwitch  : String? = intent.getStringExtra(SHOWSWITCH)
                    val showMenu    : String? = intent.getStringExtra(SHOWMENU)
                    val title       : String? = intent.getStringExtra(TITLE)
                    val titleSize   : String? = intent.getStringExtra(TITLESIZE)
                    val tipSize     : String? = intent.getStringExtra(TIPSIZE)
                    val scanTip     : String? = intent.getStringExtra(SCANTIP)

                    shareIntent.putExtra(SHOWBAR, showBar)
                    shareIntent.putExtra(SHOWBACK, showBack)
                    shareIntent.putExtra(SHOWTITLE, showTitle)
                    shareIntent.putExtra(SHOWSWITCH, showSwitch)
                    shareIntent.putExtra(SHOWMENU, showMenu)
                    shareIntent.putExtra(TITLE, title)
                    shareIntent.putExtra(TITLESIZE, titleSize)
                    shareIntent.putExtra(TIPSIZE, tipSize)
                    shareIntent.putExtra(SCANTIP, scanTip)
                    shareIntent.setPackage(PACKAGE_SMARTPOS_PERIPHERALS)
                    shareIntent.component = ComponentName(
                        PACKAGE_SMARTPOS_PERIPHERALS,
                        CAMERA_ACTIVITY_PERIPHERAL
                    )
                }

                BLUETOOTH -> {
                    val stateBluetooth: Boolean = intent.getBooleanExtra(STATE_BLUETOOTH, true)

                    shareIntent.putExtra(STATE_BLUETOOTH, stateBluetooth)
                    shareIntent.setPackage(PACKAGE_SMARTPOS_PERIPHERALS)
                    shareIntent.component = ComponentName(
                        PACKAGE_SMARTPOS_PERIPHERALS,
                        BLUETOOTH_ACTIVITY_PERIPHERAL
                    )
                }
            }

            if (shareIntent.`package`?.let { foundApp(it, this) } == true) {
                startForResult.launch(shareIntent)
            }
        }
    }

    private fun foundApp(namePackage: String, context: Context): Boolean {
        val packageManager = context.packageManager
        return try{
            packageManager.getPackageInfo(namePackage, PackageManager.GET_ACTIVITIES)
            true
        }catch(e: PackageManager.NameNotFoundException){
            false
        }
    }
}
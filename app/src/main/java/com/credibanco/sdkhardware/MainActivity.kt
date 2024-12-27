package com.credibanco.sdkhardware

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import com.credibanco.sdk.main.ResultHardwareSDK
import com.credibanco.sdk.util.Constants.RESULT_BLUETOOTH_CODE
import com.credibanco.sdk.util.Constants.RESULT_CAMERA_CODE
import com.credibanco.sdk.util.Constants.RESULT_NFC_CODE
import com.credibanco.sdk.util.Constants.RESULT_PRINT_CODE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ResultHardwareSDK {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun resultActivity(activityResult: ActivityResult) {
        Toast.makeText(this, activityResult.resultCode.toString(), Toast.LENGTH_LONG).show()
        val bundle = activityResult.data?.extras

        when(activityResult.resultCode) {
            RESULT_NFC_CODE -> {
                val returnString: String? = bundle?.getString("NFC_READ_TAG")
            }

            RESULT_CAMERA_CODE -> {
                val returnString: String? = bundle?.getString("SCANNER")
            }

            RESULT_BLUETOOTH_CODE -> {
                val bluetoothState: String? = bundle?.getString("BLUETOOTH_ADAPTER_STATUS")
            }

            RESULT_PRINT_CODE -> {
                val returnString: String? = bundle?.getString("PRINT_READ_TAG")
            }
        }
    }
}
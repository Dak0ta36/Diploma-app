package com.example.diploma2

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class bluetooth: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)

        navigate(DevicesFragment.newInstance())

        enableBluetooth()
    }

    private fun navigate(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerFragment, fragment)
            .commit()

    }
    private fun enableBluetooth(){
        requestEnableBluetooth.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
    }
    private val requestEnableBluetooth = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if (result.resultCode == RESULT_CANCELED){
            showEnableBluetoothMessage()
        }
    }
    private fun showEnableBluetoothMessage(){
        AlertDialog.Builder(this)
            .setTitle("Включить bluetooth")
            .setMessage("Для работы приложения включите bluetooth")
            .setPositiveButton("OK"){_,_ ->
                enableBluetooth()
            }
            .setNegativeButton("Cancel"){dialogInterface, _ ->
                dialogInterface.dismiss()
                finish()
            }
            .create()
            .show()
    }

}
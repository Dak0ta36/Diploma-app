package com.example.diploma2

import android.bluetooth.BluetoothDevice
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import no.nordicsemi.android.ble.observer.ConnectionObserver
import java.lang.IllegalArgumentException

class ControlViewModel(private val adapterProvider: BluetoothAdapterProvider): ViewModel() {


    private val controlManager: BleControlManager = BleControlManager(adapterProvider.getContext())
    fun connect(deviceAddress: String){
            val device = adapterProvider.getAdapter().getRemoteDevice(deviceAddress)
            controlManager.connect(device)
                .retry(10, 100)
                .useAutoConnect(true)
                .done{
                    Log.e("ControlViewModel", "connection success")
                }
                .fail{ _, status ->
                    Log.e("ControlViewModel", "connection failed")
                }
                .enqueue()
        controlManager.setConnectionObserver(connectionObserver)
    }

    fun disconnect(){
        controlManager.disconnect().enqueue()
    }

    fun StartCollecting(rtypes: Rtypes){
        if(controlManager.isReady){
            controlManager.startCollecting(rtypes)
        }
    }
    fun endCollecting(rtypes: Rtypes){
        if(controlManager.isReady){
            controlManager.endCollecting(rtypes)
        }
    }
    fun SetFreq(rtypes: Rtypes){
        if(controlManager.isReady){
            controlManager.setFrequency(rtypes)
        }
    }
    fun Calibrate(rtypes: Rtypes){
        if(controlManager.isReady){
            controlManager.Calibrate(rtypes)
        }
    }
    fun Getdata(rtypes: Rtypes){
        if(controlManager.isReady){
            controlManager.getData(rtypes)
        }
    }
    private val connectionObserver = object : ConnectionObserver{
        override fun onDeviceConnecting(device: BluetoothDevice) {}

        override fun onDeviceConnected(device: BluetoothDevice) {}

        override fun onDeviceFailedToConnect(device: BluetoothDevice, reason: Int) {}

        override fun onDeviceReady(device: BluetoothDevice) {
            Log.e("ControlViewModel", "Device is ready")
        }

        override fun onDeviceDisconnecting(device: BluetoothDevice) {}

        override fun onDeviceDisconnected(device: BluetoothDevice, reason: Int) {}

    }
}

class ControlViewModelFactory(
    private val adapterProvider: BluetoothAdapterProvider
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ControlViewModel::class.java)) {
            return ControlViewModel(adapterProvider) as T
        }
        throw IllegalArgumentException("ViewModel not found")
    }
}

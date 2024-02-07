package com.example.diploma2;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import java.util.UUID;
import java.util.zip.CRC32;

import no.nordicsemi.android.ble.BleManager;

public class BleControlManager extends BleManager {

    public static final UUID SERVICE_CONTROL_UUID = UUID.fromString("6E400001-B5A3-F393-E0A9-E50E24DCCA9E");
    public static final UUID SERVICE_WRITE_UUID = UUID.fromString("6E400002-B5A3-F393-E0A9-E50E24DCCA9E");
    public static final UUID SERVICE_READ_UUID = UUID.fromString("6E400003-B5A3-F393-E0A9-E50E24DCCA9E");

    public static final int FirstValue = 0x2;
    public static final byte Random = 0x0000;


    byte SequenceNumber = 0;

    private BluetoothGattCharacteristic controlRequest;
    private BluetoothGattCharacteristic controlResponse;


    public BleControlManager(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected BleManagerGattCallback getGattCallback() {
        return new BleControlManagerGattCallback();
    }



    public void startCollecting(Rtypes rtypes){

        writeCharacteristic(
                controlRequest, new byte[]{FirstValue, rtypes.getRt(), SequenceNumber, Random}, BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT)
                .enqueue();

        int NewSeq = SequenceNumber +2;
        SequenceNumber = (byte) NewSeq;

    }



    public void endCollecting(Rtypes rtypes){
        writeCharacteristic(
                controlRequest, new byte[]{FirstValue, rtypes.getRt(), SequenceNumber, Random}, BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT)
                .enqueue();
        int NewSeq = SequenceNumber +2;
        SequenceNumber = (byte) NewSeq;
    }

    public void setFrequency(Rtypes rtypes){
        writeCharacteristic(
                controlRequest, new byte[]{FirstValue, rtypes.getRt(), SequenceNumber, Random}, BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT)
                .enqueue();
        int NewSeq = SequenceNumber +2;
        SequenceNumber = (byte) NewSeq;
    }

    public void Calibrate(Rtypes rtypes){
        writeCharacteristic(
                controlRequest, new byte[]{FirstValue, rtypes.getRt(), SequenceNumber, Random}, BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT)
                .enqueue();
        int NewSeq = SequenceNumber +2;
        SequenceNumber = (byte) NewSeq;
    }

    public void getData(Rtypes rtypes){
        writeCharacteristic(
                controlRequest, new byte[]{FirstValue, rtypes.getRt(), SequenceNumber, Random}, BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT)
                .enqueue();
        int NewSeq = SequenceNumber +2;
        SequenceNumber = (byte) NewSeq;
    }
    class BleControlManagerGattCallback extends BleManagerGattCallback{

        @Override
        protected boolean isRequiredServiceSupported(@NonNull BluetoothGatt gatt) {

            BluetoothGattService controlService = gatt.getService(SERVICE_CONTROL_UUID);

            if(controlService != null){
                controlRequest = controlService.getCharacteristic(SERVICE_WRITE_UUID);
                controlResponse= controlService.getCharacteristic(SERVICE_READ_UUID);

            }

            return controlRequest !=null && controlResponse !=null;
        }

        @Override
        protected void onServicesInvalidated() {
            controlRequest = null;
            controlResponse= null;

        }
    }
}

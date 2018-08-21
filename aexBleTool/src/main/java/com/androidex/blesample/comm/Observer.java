package com.androidex.blesample.comm;


import com.androidex.fastble.data.BleDevice;

public interface Observer {

    void disConnected(BleDevice bleDevice);
}

package com.aditya.aadifit.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.aditya.aadifit.service.SensorService;

public class SensorRestarterBroadcastReceiver extends BroadcastReceiver {

    public final String TAG = SensorRestarterBroadcastReceiver.this.getClass().getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        Log.i(TAG, "Service Stops! Ops!!!!");
        context.startService(new Intent(context, SensorService.class));
    }
}

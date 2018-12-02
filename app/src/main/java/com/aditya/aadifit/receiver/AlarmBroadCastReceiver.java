package com.aditya.aadifit.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;

public class AlarmBroadCastReceiver extends BroadcastReceiver {

    public final String TAG = AlarmBroadCastReceiver.this.getClass().getName();
    private PowerManager.WakeLock screenWakeLock;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (screenWakeLock == null) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            String string ="ScreenLock tag from AlarmListener: ";
            screenWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP,string);
            screenWakeLock.acquire(10);
        }

        //TODO:Do your code here related to alarm receiver.
        if (screenWakeLock != null){
            screenWakeLock.release();
        }
    }
}

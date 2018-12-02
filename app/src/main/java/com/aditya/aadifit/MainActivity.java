package com.aditya.aadifit;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aditya.aadifit.service.SensorService;
import com.aditya.aadifit.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    public final String TAG = MainActivity.this.getClass().getName();
    private Intent mServiceIntent;
    private SensorService mSensorService;
    Context ctx;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public Context getCtx() {
        return ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            mSensorService = new SensorService(getCtx());
            mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
            if (!isMyServiceRunning(mSensorService.getClass())) {
                startService(mServiceIntent);
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i (TAG+ " ->isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i (TAG+" ->isMyServiceRunning?", false+"");
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        Log.i(TAG, "onDestroy!");
        super.onDestroy();
    }
}

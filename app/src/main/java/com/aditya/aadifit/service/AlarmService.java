package com.aditya.aadifit.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.aditya.aadifit.receiver.SensorRestarterBroadcastReceiver;

import java.util.Timer;
import java.util.TimerTask;

public class AlarmService extends Service {

    public int counter = 0;
    public final String TAG = AlarmService.this.getClass().getName();
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private Context applicationContext;

    public AlarmService() {
    }

    public AlarmService(Context applicationContext) {
        super();
        this.applicationContext=applicationContext;
        Log.i(TAG, "here I am!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        ge(counter);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "ondestroy!");
        int r = prefs.getInt(AppConstant.STEP_COUNTER,0);
        Log.i(TAG, "ondestroy! "+r);
        ge(counter);
        Intent broadcastIntent = new Intent(this, SensorRestarterBroadcastReceiver.class);
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  "+ (counter++));
                ge(counter);
            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void ge(int counter){

        try {
            prefs= getSharedPreferences(AppConstant.AADI_PREF, MODE_PRIVATE);
            editor = prefs.edit();
            editor.putInt(AppConstant.STEP_COUNTER, counter);
            editor.apply();
            //Long.i("MoveMore", "Saving readings to preferences");
        } catch (NullPointerException e) {
            Log.e(TAG, "error saving: are you testing?" +e.getMessage());
//            ServerOperations.database.addError(e.getMessage());
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }
}

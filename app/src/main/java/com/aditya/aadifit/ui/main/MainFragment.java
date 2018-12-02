package com.aditya.aadifit.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aditya.aadifit.R;
import com.aditya.aadifit.receiver.SensorRestarterBroadcastReceiver;
import com.aditya.aadifit.service.AppConstant;
import com.aditya.aadifit.service.StepCountingService;

import static android.content.Context.MODE_PRIVATE;

public class MainFragment extends Fragment {

    public final String TAG = MainFragment.this.getClass().getName();
    private MainViewModel mViewModel;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private TextView message;
    private Intent stepCountService;
    String countedStep;
    String DetectedStep;
    static final String State_Count = "Counter";
    static final String State_Detect = "Detector";

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        message = view.findViewById(R.id.message);
        prefs= getContext().getSharedPreferences(AppConstant.AADI_PREF, MODE_PRIVATE);
        editor = prefs.edit();
//        int r = prefs.getInt(AppConstant.STEP_COUNTER,0);
//        message.setText("Step Counter : " + r);
        getContext().startService(new Intent(getContext(), StepCountingService.class));
        // register our BroadcastReceiver by passing in an IntentFilter. * identifying the message that is broadcasted by using static string "BROADCAST_ACTION".
        getContext().registerReceiver(broadcastReceiver, new IntentFilter(StepCountingService.BROADCAST_ACTION));
//        message.setText("\n");
//        message.setText("Sleep Counter : ");
//        editor.(AppConstant.STEP_COUNTER, counter);
        stepCountService = new Intent(getContext(), StepCountingService.class);
        return view;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // call updateUI passing in our intent which is holding the data to display.
            updateViews(intent);
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getContext().unregisterReceiver(broadcastReceiver);
        // stop Service.
        getContext().stopService(new Intent(getContext(), StepCountingService.class));
    }

    private void updateViews(Intent intent) {
        // retrieve data out of the intent.
        countedStep = intent.getStringExtra("Counted_Step");
        DetectedStep = intent.getStringExtra("Detected_Step");
        Log.d(TAG, String.valueOf(countedStep));
        Log.d(TAG, String.valueOf(DetectedStep));
        message.setText('"' + String.valueOf(countedStep) + '"' + " Steps Counted");

    }

}

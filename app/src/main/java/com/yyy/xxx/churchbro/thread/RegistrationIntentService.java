package com.yyy.xxx.churchbro.thread;

import com.google.firebase.iid.FirebaseInstanceId;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by len on 2017. 5. 30..
 */

public class RegistrationIntentService extends IntentService {


    private static final String TAG = "RegIntentService";


    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "FCM Registration Token: " + token);
    }
}

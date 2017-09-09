package com.yyy.xxx.churchbro;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.yyy.xxx.churchbro.database.BibleBaseHelper;
import com.yyy.xxx.churchbro.thread.RegistrationIntentService;

import java.io.IOException;

/**
 * Created by len on 2017. 2. 14..
 */

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getName();

    private ImageView mImageView;
    private BibleBaseHelper mHelper;
    private static final int INTERVERTIME = 1000;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mImageView = (ImageView) findViewById(R.id.splashImage);

        mImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, INTERVERTIME);
        long startTime = System.currentTimeMillis();
        mHelper = new BibleBaseHelper(this);

        try {

            mHelper.createDataBase();
            Log.d(TAG, "CREATE DATABASE");
        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            mHelper.openDataBase();
            Log.d(TAG, "OPEN DATABASE");

        }catch(SQLException sqle){
            throw sqle;
        }

        long endTime = System.currentTimeMillis();

        Log.d(TAG, (endTime - startTime) / 1000 + "");

        //FirebaseService
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }


}

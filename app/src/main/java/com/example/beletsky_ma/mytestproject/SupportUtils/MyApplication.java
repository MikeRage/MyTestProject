package com.example.beletsky_ma.mytestproject.SupportUtils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;

import com.example.beletsky_ma.mytestproject.R;
import com.example.beletsky_ma.mytestproject.model.DbHelper;

public class MyApplication extends Application {

    public static DbHelper myDbHelper;
    SQLiteDatabase rwdb;
    private static MyApplication sInstance;
//    public static SharedPreferences prefs;
    Resources r;
    private PreferenceUtils preferenceUtils;

    @Override
    public void onCreate() {
        super.onCreate();

        myDbHelper = DbHelper.getInstance(this);
        rwdb = myDbHelper.getWritableDatabase();
        r = getResources();
//        prefs = getSharedPreferences(getString(R.string.myPrefs), Context.MODE_PRIVATE);
        sInstance = this;
        preferenceUtils = PreferenceUtils.getInstance();
        preferenceUtils.init(getApplicationContext());
    }

    public PreferenceUtils getPreferenceUtils() {
        return preferenceUtils;
    }

    public static synchronized MyApplication getInstance() {
        if (sInstance == null) {
            sInstance = new MyApplication();
        }
        return sInstance;
    }

}

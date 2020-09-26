package com.example.mkwon.mikekwonbranchsdk;

import android.app.Application;
import android.util.Log;

import io.branch.referral.Branch;

public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Branch.enableLogging();
        Branch.enableDebugMode();
        Branch.getAutoInstance(this);
        Log.i("MyApp", "Application onCreate() called");
    }
}

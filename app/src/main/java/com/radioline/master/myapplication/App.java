package com.radioline.master.myapplication;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.radioline.master.basic.Basket;
import com.radioline.master.basic.ParseSetting;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseCrashReporting.enable(this);
        Parse.enableLocalDatastore(getApplicationContext());
        ParseObject.registerSubclass(Basket.class);
        ParseObject.registerSubclass(ParseSetting.class);
        Parse.initialize(this, "5pOXIrqgAidVKFx2mWnlMHj98NPYqbR37fOEkuuY", "oZII0CmkEklLvOvUQ64CQ6i4QjOzBIEGZfbXvYMG");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
package com.radioline.master.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.radioline.master.basic.Basket;
import com.radioline.master.basic.Caches;
import com.radioline.master.basic.Group;
import com.radioline.master.basic.ParseSetting;

import java.util.Calendar;
import java.util.List;

public class App extends Application {

    private static Context mContext;
    /**
     * The Analytics singleton. The field is set in onCreate method override when the application
     * class is initially created.
     */
    private static GoogleAnalytics analytics;


    /**
     * The default app tracker. The field is from onCreate callback when the application is
     * initially created.
     */
    private static Tracker tracker;


    /**
     * Access to the global Analytics singleton. If this method returns null you forgot to either
     * set android:name="&lt;this.class.name&gt;" attribute on your application element in
     * AndroidManifest.xml or you are not setting this.analytics field in onCreate method override.
     */
    public static GoogleAnalytics analytics() {
        return analytics;
    }


    /**
     * The default app tracker. If this method returns null you forgot to either set
     * android:name="&lt;this.class.name&gt;" attribute on your application element in
     * AndroidManifest.xml or you are not setting this.tracker field in onCreate method override.
     */
    public static Tracker tracker() {
        return tracker;
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        ParseCrashReporting.enable(this);
        //Parse.enableLocalDatastore(this); //disable local store for caches
        
        ParseQuery.clearAllCachedResults();

        ParseObject.registerSubclass(Basket.class);
        ParseObject.registerSubclass(Group.class);
        ParseObject.registerSubclass(ParseSetting.class);

        Parse.initialize(this, "5pOXIrqgAidVKFx2mWnlMHj98NPYqbR37fOEkuuY", "oZII0CmkEklLvOvUQ64CQ6i4QjOzBIEGZfbXvYMG");
        Parse.setLogLevel(Parse.LOG_LEVEL_ERROR);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        Intent i = new Intent(getApplicationContext(), App.class);
        ParseAnalytics.trackAppOpenedInBackground(i);

        analytics = GoogleAnalytics.getInstance(this);


        // TODO: Replace the tracker-id with your app one from https://www.google.com/analytics/web/
        tracker = analytics.newTracker("UA-65055405-1");


        // Provide unhandled exceptions reports. Do that first after creating the tracker
        tracker.enableExceptionReporting(true);


        // Enable Remarketing, Demographics & Interests reports
        // https://developers.google.com/analytics/devguides/collection/android/display-features
        tracker.enableAdvertisingIdCollection(true);


        // Enable automatic activity tracking for your app
        tracker.enableAutoActivityTracking(true);



    }
}
package com.radioline.master.basic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.radioline.master.myapplication.App;

import hugo.weaving.DebugLog;

/**
 * Created by mikoladyachok on 11/26/14.
 */
public class BaseValues {
    private static String key, value;
    @DebugLog
    public static String GetValue(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        return prefs.getString(key, "");
//        String rt = "";
//        ParseQuery<ParseSetting> query = ParseSetting.getQuery();
//        query.fromLocalDatastore();
//        query.whereEqualTo("key", key);
//        try {
//            ParseSetting getSet = query.getFirst();
//            if (getSet.isDataAvailable()) {
//                rt = getSet.getValue().toString();
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (ClassCastException e) {
//            e.printStackTrace();
//        }
//        return rt;
    }

    @DebugLog
    public static void SetValue(String Key, String Value) {
        SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences(App.getContext()).edit();
        prefs.putString(Key, Value);
        prefs.commit();
//        ParseQuery<ParseSetting> queryPID = ParseSetting.getQuery();
//        queryPID.fromLocalDatastore();
//        queryPID.whereEqualTo("key", Key);
//        try {
//            if ((queryPID.count() > 0)) {
//                ParseSetting getSet = queryPID.getFirst();
//                getSet.setValue(Value);
//                getSet.pinInBackground();
//            } else {
//                ParseSetting setUID = new ParseSetting();
//                setUID.setKey(Key);
//                setUID.setValue(Value);
//                setUID.pinInBackground();
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

    @DebugLog
    public static String GetValue(String key,Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        return prefs.getString(key, "");

    }

    @DebugLog
    public static void SetValue(String Key, String Value,Context context) {

        SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences(App.getContext()).edit();
        prefs.putString(Key, Value);
        prefs.commit();
    }



}

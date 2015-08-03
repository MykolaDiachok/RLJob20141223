package com.radioline.master.basic;

import com.parse.ParseException;
import com.parse.ParseQuery;

/**
 * Created by mikoladyachok on 11/26/14.
 */
public class BaseValues {
    private static String key, value;

    public static String GetValue(String key) {
        String rt = "";
        ParseQuery<ParseSetting> query = ParseSetting.getQuery();
        query.fromLocalDatastore();
        query.whereEqualTo("key", key);
        try {
            ParseSetting getSet = query.getFirst();
            if (getSet.isDataAvailable()) {
                rt = getSet.getValue().toString();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static void SetValue(String Key, String Value) {
        ParseQuery<ParseSetting> queryPID = ParseSetting.getQuery();
        queryPID.fromLocalDatastore();
        queryPID.whereEqualTo("key", Key);
        try {
            if ((queryPID.count() > 0)) {
                ParseSetting getSet = queryPID.getFirst();
                getSet.setValue(Value);
                getSet.pinInBackground();
            } else {
                ParseSetting setUID = new ParseSetting();
                setUID.setKey(Key);
                setUID.setValue(Value);
                setUID.pinInBackground();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
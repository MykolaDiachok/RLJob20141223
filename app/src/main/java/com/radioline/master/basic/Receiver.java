package com.radioline.master.basic;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;
import com.radioline.master.myapplication.LoginActivity;

import hugo.weaving.DebugLog;

/**
 * Created by master on 23.12.2014.
 */
public class Receiver extends ParsePushBroadcastReceiver {

    @Override
    @DebugLog
    public void onPushOpen(Context context, Intent intent) {
        Intent i = new Intent(context, LoginActivity.class);
        i.putExtras(intent.getExtras());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}

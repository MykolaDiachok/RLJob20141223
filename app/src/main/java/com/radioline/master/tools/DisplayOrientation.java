package com.radioline.master.tools;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.Surface;

/**
 * Created by mikoladyachok on 7/8/15.
 */
public class DisplayOrientation {

    private static DisplayOrientation instance = new DisplayOrientation();
    private static Context context;
    private static Activity activity;

    public static DisplayOrientation getInstance(Context ctx,Activity act) {
        context = ctx.getApplicationContext();
        activity = act;
        return instance;
    }

    public void  enableOrientation()
    {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    public void  blockOrientation(){

        if (activity.getWindowManager().getDefaultDisplay().getRotation()== Surface.ROTATION_90)
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else if (activity.getWindowManager().getDefaultDisplay().getRotation()== Surface.ROTATION_270)
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        else if (activity.getWindowManager().getDefaultDisplay().getRotation()== Surface.ROTATION_180)
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
        else
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}

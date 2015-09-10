package com.radioline.master.tools;

import android.content.Context;

/**
 * Created by master on 08.09.2015.
 */
public interface AsyncTaskListener<Progress,Result> {

    Context getContext();
    //public void onTaskComplete(T result);
    public void onPreExecute();
    public void onProgressUpdate(Progress... progress);
    public void onCancelled();
    public void onPostExecute(Result result);
}

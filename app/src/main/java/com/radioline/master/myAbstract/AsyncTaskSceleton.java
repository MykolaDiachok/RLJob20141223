package com.radioline.master.myAbstract;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.radioline.master.basic.Group;
import com.radioline.master.myapplication.R;
import com.radioline.master.tools.AsyncTaskListener;

import java.util.ArrayList;

/**
 * Created by master on 08.09.2015.
 */
public abstract class AsyncTaskSceleton <Params, Progress, Result> extends
        AsyncTask<Params, Progress, Result> {

    private AsyncTaskListener<Progress, Result> callBack;
    private Context context;
    public ProgressDialog dialog;
    public Exception exception;

    public AsyncTaskSceleton(Context context,AsyncTaskListener<Progress, Result> callBack) {
        this.context = context;
        this.callBack = callBack;
        this.dialog = new ProgressDialog(context);
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callBack.onPreExecute();
        this.dialog.setMessage(context.getResources().getString(R.string.ProgressDialogTitle));
        this.dialog.show();
    }

    @Override
    protected void onPostExecute(Result result) {
        callBack.onPostExecute(result);
        if (dialog.isShowing()) dialog.dismiss();

    }

    @Override
    protected void onProgressUpdate(Progress... values) {
        callBack.onProgressUpdate(values);
    }

}

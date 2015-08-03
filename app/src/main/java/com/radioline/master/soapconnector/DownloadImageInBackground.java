package com.radioline.master.soapconnector;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.util.concurrent.ExecutionException;

/**
 * Created by master on 07.11.2014.
 */
public class DownloadImageInBackground extends AsyncTask<String, Void, Bitmap> {
    private ImageView iv;

    public DownloadImageInBackground(ImageView iv) {
        this.iv = iv;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Converts tg1 = new Converts();
        Bitmap bitmap = null;
        try {
            bitmap = tg1.getBitMapFromServer(params[0],200,200,50,true);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        iv.setImageBitmap(result);



    }
}

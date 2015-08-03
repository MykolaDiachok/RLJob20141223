package com.radioline.master.soapconnector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.radioline.master.myapplication.R;

import org.kobjects.base64.Base64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ImageDownloaderSOAP {

    private Map<String, Bitmap> imageCache;

    public ImageDownloaderSOAP() {
        imageCache = new HashMap<String, Bitmap>();

    }

    // cancel a download (internal only)
    private static boolean cancelPotentialDownload(String itemId,
                                                   ImageView imageView) {
        BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);

        if (bitmapDownloaderTask != null) {
            String bitmapItemID = bitmapDownloaderTask.itemID;
            if ((bitmapItemID == null) || (!bitmapItemID.equals(itemId))) {
                bitmapDownloaderTask.cancel(true);
            } else {
                // The same URL is already being downloaded.
                return false;
            }
        }
        return true;
    }

    // gets an existing download if one exists for the imageview
    private static BitmapDownloaderTask getBitmapDownloaderTask(
            ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof DownloadedDrawable) {
                DownloadedDrawable downloadedDrawable = (DownloadedDrawable) drawable;
                return downloadedDrawable.getBitmapDownloaderTask();
            }
        }
        return null;
    }

    // our caching functions
// Find the dir to save cached images
    private static File getCacheDirectory(Context context) {
        String sdState = android.os.Environment.getExternalStorageState();
        File cacheDir;

        if (sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
            File sdDir = android.os.Environment.getExternalStorageDirectory();


            cacheDir = new File(sdDir, "data/RL/images");
        } else
            cacheDir = context.getCacheDir();

        if (!cacheDir.exists())
            cacheDir.mkdirs();
        return cacheDir;
    }

    // the actual download code
    static Bitmap downloadBitmap(String itemID, Boolean full) throws ExecutionException, InterruptedException {
        SoapPrimitive runSoap;
        Link link = new Link();
        if (!link.getISWorkUrl()) {
            return null;
        }
        if (!full) {
            final String method_name = "GetPNGWithSize";


            // SoapObject tSoap = getFromServerSoapObject(method_name,soap_action);
            //LinkAsyncTaskGetSoapPrimitive linkAsyncTaskGetSoapPrimitive = new LinkAsyncTaskGetSoapPrimitive(method_name);
            //linkAsync.execute();
            PropertyInfo piidItem = new PropertyInfo();
            piidItem.setName("ItemId");
            piidItem.setValue(itemID);
            //piidItem.setValue("e3dd8ed4-8fa6-11e2-b51b-00155d060502");
            piidItem.setType(String.class);

            PropertyInfo piHeight = new PropertyInfo();
            piHeight.setName("Height");
            piHeight.setValue(200);
            piHeight.setType(Integer.class);

            PropertyInfo piWidth = new PropertyInfo();
            piWidth.setName("Width");
            piWidth.setValue(200);
            piWidth.setType(Integer.class);

            PropertyInfo piQuality = new PropertyInfo();
            piQuality.setName("Quality");
            piQuality.setValue(50);
            piQuality.setType(Integer.class);

            PropertyInfo piHardCompression = new PropertyInfo();
            piHardCompression.setName("HardCompression");
            piHardCompression.setValue(true);
            piHardCompression.setType(Boolean.class);


            runSoap = link.getFromServerSoapPrimitive(method_name, new PropertyInfo[]{piidItem, piHeight, piWidth, piQuality, piHardCompression});
        } else {
            final String method_name = "GetPNG";
            PropertyInfo piidItem = new PropertyInfo();
            piidItem.setName("ItemId");
            piidItem.setValue(itemID);
            piidItem.setType(String.class);
            //Link link = new Link();
            runSoap = link.getFromServerSoapPrimitive(method_name, new PropertyInfo[]{piidItem});
        }


        if (runSoap == null) {
            return null;
        }

        String base64String = runSoap.toString();
        byte[] bytearray = Base64.decode(base64String);

        return BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length);

    }

    // download function
    public void download(String itemID, ImageView imageView, Activity currentActivity, Boolean full) {
        if (cancelPotentialDownload(itemID, imageView)) {

            // Caching code right here
            String filename;
            if (full) {
                filename = "full_" + itemID;
            } else {
                filename = itemID;
            }
            File f = new File(getCacheDirectory(imageView.getContext()), filename);


            Bitmap bitmap = (Bitmap) imageCache.get(f.getPath());

            if (bitmap == null) {

                bitmap = BitmapFactory.decodeFile(f.getPath());

                if (bitmap != null) {
                    imageCache.put(f.getPath(), bitmap);
                }

            }
            // No? download it
            if (bitmap == null) {
                try {
                    BitmapDownloaderTask task = new BitmapDownloaderTask(
                            imageView, currentActivity);
                    DownloadedDrawable downloadedDrawable = new DownloadedDrawable(
                            task);
                    imageView.setImageDrawable(downloadedDrawable);
                    task.execute(itemID, full);
                } catch (Exception e) {
                    Log.e("Error==>", e.toString());
                }

            } else {
                // Yes? set the image
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private void writeFile(Bitmap bmp, File f) {
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(f);
            if (out != null)
                bmp.compress(Bitmap.CompressFormat.PNG, 80, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception ex) {
            }
        }
    }

    static class DownloadedDrawable extends ColorDrawable {
        private final WeakReference<BitmapDownloaderTask> bitmapDownloaderTaskReference;

        public DownloadedDrawable(BitmapDownloaderTask bitmapDownloaderTask) {
            super(Color.WHITE);
            bitmapDownloaderTaskReference = new WeakReference<BitmapDownloaderTask>(
                    bitmapDownloaderTask);
        }

        public BitmapDownloaderTask getBitmapDownloaderTask() {
            return bitmapDownloaderTaskReference.get();
        }
    }

    // download asynctask
    public class BitmapDownloaderTask extends AsyncTask<Object, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private String itemID;
        private Boolean full;
        private Activity currentActivity;
        private ProgressDialog progress;

        public BitmapDownloaderTask(ImageView imageView, Activity currentActivity) {
            imageViewReference = new WeakReference<ImageView>(imageView);
            this.currentActivity = currentActivity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (currentActivity != null) {

                progress = new ProgressDialog(currentActivity);
                progress.setTitle(currentActivity.getString(R.string.ProgressDialogTitle));
                progress.setMessage(currentActivity.getString(R.string.ProgressDialogMessage));
                progress.setIndeterminate(true);
                progress.show();
            }
        }


        @Override
        // Actual download method, run in the task thread
        protected Bitmap doInBackground(Object... params) {
            // params comes from the execute() call: params[0] is the url.
            itemID = (String) params[0];
            full = (Boolean) params[1];

            try {
                return downloadBitmap(itemID, full);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        // Once the image is downloaded, associates it to the imageView
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);
                // Change bitmap only if this process is still associated with
                // it
                if (this == bitmapDownloaderTask) {
                    imageView.setImageBitmap(bitmap);

                    // cache the image
                    if (bitmap != null) {
                    String filename;
                    if (full) {
                        filename = "full_" + itemID;
                    } else {
                        filename = itemID;
                    }
                    File f = new File(
                            getCacheDirectory(imageView.getContext()), filename);

                    imageCache.put(f.getPath(), bitmap);

                        writeFile(bitmap, f);
                    }
                }
            }
            if ((progress != null) && (progress.isShowing())) {
                progress.dismiss();
            }
        }

    }
}
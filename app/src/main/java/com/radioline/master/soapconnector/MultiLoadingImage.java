package com.radioline.master.soapconnector;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.radioline.master.basic.Group;
import com.radioline.master.basic.Item;

import org.kobjects.base64.Base64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by master on 15.11.2014.
 */


public class MultiLoadingImage extends AsyncTask<Void, TaskProgress, Void> {

    private ProgressDialog progressDialog;
    private boolean isCancelled = false;
    private Context mContext;
    private TaskProgress taskProgress;


    public MultiLoadingImage(Context context) {
        mContext = context;
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
        base64String = null;
        runSoap = null;
        return BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length);

    }

    @Override
    protected void onCancelled() {
        isCancelled = true;
        taskProgress.isCancelled = true;
        super.onCancelled();

    }

    @Override
    protected Void doInBackground(Void... voids) {
        Converts tg = new Converts();
        ArrayList<Group> groups;
        ArrayList<Group> groups2;
        ArrayList<Item> items;
        Boolean full = false;
        String itemID;
        try {
            taskProgress.message = "Downloading groups...Please Wait";
            taskProgress.percentage1 = 0;
            taskProgress.percentage2 = 0;
            publishProgress(taskProgress);
            groups = tg.getGroupsArrayListFromServerWithoutAsync();
            int lenght = groups.size();
            int total = 0;
            for (Group group : groups) {
                total++;
                taskProgress.message = "Downloading groups " + group.getName() + "...Please Wait";
                taskProgress.percentage1 = 0;
                taskProgress.percentage2 = 0;
                publishProgress(taskProgress);
                if (group == null)
                    continue;
                groups2 = tg.getGroupsArrayListFromServerWithoutAsync(group.getGroupid());
                if (groups2 == null)
                    continue;
                int lenght2 = groups2.size();
                int total2 = 0;
                for (Group group2 : groups2) {
                    total2++;
                    taskProgress.message = "Downloading " + group2.getFullnamegroup() + "...Please Wait";
                    //taskProgress.percentage1 = 0;
                    taskProgress.percentage2 = (total2 * 100) / lenght2;
                    publishProgress(taskProgress);
                    if (group2 == null)
                        continue;
                    items = tg.getItemsArrayListFromServerWithoutAsync(group2.getGroupid(), false);
                    if (items == null)
                        continue;
                    int lenghtItem = items.size();

                    int totalItem = 0;
                    for (Item item : items) {
                        totalItem++;
                        int CurrentProgress = progressDialog.getProgress();
                        int secondaryProgress = (CurrentProgress + 100 * total2) / lenght2;
                        taskProgress.message = "Downloading " + group2.getFullnamegroup() + "...Please Wait";
                        taskProgress.percentage1 = ((totalItem * 100) / lenghtItem);
                        taskProgress.percentage2 = secondaryProgress;
                        publishProgress(taskProgress);
                        if (item == null)
                            continue;


                        itemID = item.getId();
                        String filename;
                        if (full) {
                            filename = "full_" + itemID;
                        } else {
                            filename = itemID;
                        }

                        File f = new File(getCacheDirectory(mContext), filename);
                        if (f.exists())
                            continue;
                        Bitmap bmp = downloadBitmap(itemID, full);
                        if (bmp != null) {
                            writeFile(bmp, f);
                            bmp.recycle();
                            System.gc();
                        }
                        bmp = null;
                        if (taskProgress.isCancelled) break;
                    }
                    items = null;
                    if (taskProgress.isCancelled) break;
                }
                if (taskProgress.isCancelled) break;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        isCancelled = true;
        taskProgress.isCancelled = true;
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(TaskProgress... progress) {
        progressDialog.setProgress(progress[0].percentage1);
        progressDialog.setSecondaryProgress(progress[0].percentage2);
        progressDialog.setMessage(progress[0].message);
        progress[0].isCancelled = !progressDialog.isShowing();

    }

    @Override
    public void onPreExecute() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Downloading...Please Wait");
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(false);
        //progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(true);
        progressDialog.show();
        taskProgress.isCancelled = false;

    }

    private void writeFile(Bitmap bmp, File f) {
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(f);
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


}
//
//public  class TaskProgress {
//    static int percentage1;
//    static int percentage2;
//    static String message;
//
//    TaskProgress(int percentage1,int percentage2, String message) {
//        this.percentage1 = percentage1;
//        this.percentage2 = percentage2;
//        this.message = message;
//    }
//}
package com.radioline.master.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.badoo.mobile.util.WeakHandler;
import com.radioline.master.basic.Item;
import com.radioline.master.basic.SystemService;
import com.radioline.master.soapconnector.Converts;
import com.radioline.master.soapconnector.ImageDownloaderSOAP;
import com.splunk.mint.Mint;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class PicActivity extends Activity {
    private ImageView imageView2;
    private WeakHandler handler = new WeakHandler();
    private ProgressDialog dialog;
    private ArrayList<Item> itemlist;
    private TextView tvProperties;
    private TextView tvPriceUSD;
    private TextView tvPriceUAH;
    private TextView tvPriceRRCUSD;
    private TextView tvPriceRRCUAH;
    private Thread t;


    @Override
    protected void onResume() {
        super.onResume();
        Mint.startSession(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Mint.closeSession(this);
        Mint.flush();
        if ((t != null) && (t.isAlive())) {
            t.interrupt();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mint.initAndStartSession(this, getString(R.string.mint));
        //Mint.enableDebug();

        setContentView(R.layout.activity_pic);
        this.setTitle(getIntent().getStringExtra("Name"));
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        tvProperties = (TextView) findViewById(R.id.tvProperties);
        tvPriceUSD = (TextView) findViewById(R.id.tvPriceUSD);
        tvPriceUAH = (TextView) findViewById(R.id.tvPriceUAH);
        tvPriceRRCUSD = (TextView) findViewById(R.id.tvPriceRRCUSD);
        tvPriceRRCUAH = (TextView) findViewById(R.id.tvPriceRRCUAH);

        loadData();


    }


    private void loadData() {
        SystemService ss = new SystemService(this);
        if (ss.isNetworkAvailable()) {

            dialog = ProgressDialog.show(this, getString(R.string.ProgressDialogTitle),
                    getString(R.string.ProgressDialogMessage));
            Thread t = new Thread() {
                public void run() {
                    Converts tg = new Converts();
                    try {
                        itemlist = tg.getItemsArrayListFromServer(getIntent().getStringExtra("itemid"), true);

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        public void run() {
                            if (dialog != null) {
                                if (dialog.isShowing()) {
                                    try {
                                        dialog.dismiss();
                                    } catch (IllegalArgumentException e) {
                                        e.printStackTrace();
                                    }
                                    ;
                                }
                            }
                            if ((itemlist == null) && (itemlist.isEmpty()))
                                return;

                            Item item = itemlist.get(0);
                            String properties = "";
                            if ((item.getFullNameGroup() != null) && (item.getFullNameGroup().length() != 0))
                                properties = properties + "<b>groups:</b>" + item.getFullNameGroup() + "<br>";

                            if ((item.getType() != null) && (item.getType().length() != 0))
                                properties = properties + "<b>type:</b>" + item.getType() + "<br>";
                            if (item.getBrand() != null && (item.getBrand().length() != 0))
                                properties = properties + "<b>brand:</b>" + item.getBrand() + "<br>";
                            if ((item.getModel() != null) && (item.getModel().length() != 0))
                                properties = properties + "<b>model:</b>" + item.getModel() + "<br>";
                            if ((item.getModelCharacteristic() != null) && (item.getModelCharacteristic().length() != 0))
                                properties = properties + "<b>characteristic:</b>" + item.getModelCharacteristic() + "<br>";
                            if ((item.getColoration() != null) && (item.getColoration().length() != 0))
                                properties = properties + "<b>color:</b>" + item.getColoration() + "<br>";
                            if ((item.getWarranty() != null) && (item.getWarranty().length() != 0))
                                properties = properties + "<b>waranty:</b>" + item.getWarranty() + "<br>";

                            if ((item.getCode() != null) && (item.getCode().length() != 0))
                                properties = properties + "<b>code:</b>" + item.getCode() + "<br>";
                            //String Article
                            if ((item.getPartNumber() != null) && (item.getPartNumber().length() != 0))
                                properties = properties + "<b>part #:</b>" + item.getPartNumber() + "<br>";
                            if ((item.getDescription() != null) && (item.getDescription().length() != 0))
                                properties = properties + "<b>description #:</b>" + item.getDescription() + "<br>";

                            if ((item.getOurWebSite() != null) && (item.getOurWebSite().length() != 0)) {
                                properties = properties + "<a href=\"https://" + item.getOurWebSite() + ">our site</a><br>";
                            }

                            if ((item.getSite() != null) && (item.getSite().length() != 0)) {
                                properties = properties + "<a href=\"" + item.getSite() + ">product site</a><br>";
                            }

                            tvProperties.setMovementMethod(LinkMovementMethod.getInstance());
                            tvProperties.setText(Html.fromHtml(properties));


                            DecimalFormat dec = new DecimalFormat("0.00");
                            tvPriceUSD.setText("$ " + dec.format(item.getPrice()));
                            tvPriceUAH.setText("₴ " + dec.format(item.getPriceUAH()));
                            float rrpusd = item.getPriceRRP();
                            if (rrpusd != 0)
                                tvPriceRRCUSD.setText("РРЦ $ " + dec.format(rrpusd));
                            else
                                tvPriceRRCUSD.setText("");
                            float rrpuah = item.getPriceRRPUAH();
                            if (rrpuah != 0)
                                tvPriceRRCUAH.setText("РРЦ ₴ " + dec.format(rrpuah));
                            else
                                tvPriceRRCUAH.setText("");
                            //lvItem.setAdapter(itemViewAdapter);
                        }
                    });
                }
            };

            t.start();


            ImageDownloaderSOAP getimage = new ImageDownloaderSOAP();
            getimage.download(getIntent().getStringExtra("itemid"), imageView2, PicActivity.this, true);
        } else {
            Toast.makeText(PicActivity.this, getString(R.string.NoConnect), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

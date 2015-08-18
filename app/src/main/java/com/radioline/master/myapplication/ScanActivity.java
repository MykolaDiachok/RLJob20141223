package com.radioline.master.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.badoo.mobile.util.WeakHandler;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.radioline.master.basic.Item;
import com.radioline.master.basic.ItemViewAdapter;
import com.radioline.master.basic.SystemService;
import com.radioline.master.soapconnector.Converts;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class ScanActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {
    final Runnable showToastMessage = new Runnable() {
        public void run() {
            Toast.makeText(ScanActivity.this, "It's barcode=" + contents + " not found in database, perhaps the item is not available", Toast.LENGTH_SHORT).show();
        }
    };
    private Button btScan;
    private Button btSearchBarcode;
    private ListView lvScan;
    private WeakHandler handler = new WeakHandler();
    private ProgressDialog dialog;
    private ItemViewAdapter itemViewAdapter;
    private String contents;
    private EditText edBarcode;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Mint.enableDebug();


        setContentView(R.layout.activity_scan);

        lvScan = (ListView) findViewById(R.id.lvScan);
        lvScan.setOnItemClickListener(this);

        btSearchBarcode = (Button) findViewById(R.id.btSearchBarcode);
        btSearchBarcode.setOnClickListener(this);

        edBarcode = (EditText) findViewById(R.id.edBarcode);

        btScan = (Button) findViewById(R.id.btScan);
        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(ScanActivity.this);
                //integrator.addExtra("SCAN_WIDTH", 640);
                //integrator.addExtra("SCAN_HEIGHT", 480);
                //integrator.addExtra("SCAN_MODE", "ONE_D_MODE");
                integrator.addExtra("SCAN_MODE", "PRODUCT_MODE");
                integrator.addExtra("SCAN_FORMATS", "EAN_13");//EAN_13,CODE_128
                //customize the prompt message before scanning
                integrator.addExtra("PROMPT_MESSAGE", "Scanner Start!");
                integrator.initiateScan(IntentIntegrator.PRODUCT_CODE_TYPES);
                //Intent intent = integrator.createScanIntent();
                //startActivityForResult(intent, IntentIntegrator.REQUEST_CODE);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (result != null) {
            contents = result.getContents();
            if (contents != null) {
                //textViewScan.setText(contents);
                edBarcode.setText(contents);
                searchOnDataBase(contents);


            } else {
                //  String T = getString(R.string.result_failed_why);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_basket:
                intent = new Intent(this, BasketActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Item item = (Item) adapterView.getItemAtPosition(position);
        Intent intent = new Intent(this, PicActivity.class);
        intent.putExtra("itemid", item.getId());
        intent.putExtra("Name", item.getName());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSearchBarcode:
                searchOnDataBase(edBarcode.getText().toString());
                break;
            case R.id.btScan:
                break;
        }
    }

    private void searchOnDataBase(final String searchBarCode) {
        SystemService ss = new SystemService(this);
        if (ss.isNetworkAvailable()) {

            dialog = ProgressDialog.show(this, getString(R.string.ProgressDialogTitle),
                    getString(R.string.ProgressDialogMessage));
            Thread t = new Thread() {

                @Override
                public void interrupt() {

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
                    super.interrupt();
                }

                public void run() {
                    Converts tg = new Converts();
                    try {
                        ArrayList<Item> item = tg.getItemsArrayListFromServerWithBarcode(searchBarCode, false);
                        if (item == null) {

                            handler.post(showToastMessage);
                        } else {
                            itemViewAdapter = new ItemViewAdapter(ScanActivity.this, item);
                        }

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
                            if (itemViewAdapter != null) {
                                lvScan.setAdapter(itemViewAdapter);
                            } else {
                                Toast.makeText(ScanActivity.this, getString(R.string.NoConnect), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            };

            t.start();
        } else {
            Toast.makeText(ScanActivity.this, getString(R.string.NoConnect), Toast.LENGTH_LONG).show();
        }
    }

}

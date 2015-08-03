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
import com.radioline.master.basic.Item;
import com.radioline.master.basic.ItemViewAdapter;
import com.radioline.master.basic.SystemService;
import com.radioline.master.myapplication.R;
import com.radioline.master.soapconnector.Converts;
import com.splunk.mint.Mint;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {
    Button btSearchByName;
    EditText etSearchByName;
    ListView lvSearchByName;
    private WeakHandler handler = new WeakHandler();
    private ProgressDialog dialog;
    private String contents;
    private ItemViewAdapter itemViewAdapter;
    private Thread t;


    final Runnable showToastMessage = new Runnable() {
        public void run() {
            Toast.makeText(SearchActivity.this, "It's string:" + contents + " not found in database, perhaps the item is not available", Toast.LENGTH_SHORT).show();
        }
    };


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
        setContentView(R.layout.activity_search);
        Mint.initAndStartSession(this, getString(R.string.mint));

        lvSearchByName = (ListView) findViewById(R.id.lvSearchByName);
        lvSearchByName.setOnItemClickListener(this);

        btSearchByName = (Button) findViewById(R.id.btSearchByName);
        btSearchByName.setOnClickListener(this);

        etSearchByName = (EditText) findViewById(R.id.etSearchByName);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSearchByName:
                searchOnDataBase(etSearchByName.getText().toString());
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Item item = (Item) parent.getItemAtPosition(position);
        Intent intent = new Intent(this, PicActivity.class);
        intent.putExtra("itemid", item.getId());
        intent.putExtra("Name", item.getName());
        startActivity(intent);
    }


    private void searchOnDataBase(final String searchByName) {
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
                    ArrayList<Item> item = tg.getItemsArrayListFromServerSearchByName(searchByName, false);
                    if (item == null) {

                        handler.post(showToastMessage);
                    } else {
                        itemViewAdapter = new ItemViewAdapter(SearchActivity.this, item);
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
                            lvSearchByName.setAdapter(itemViewAdapter);
                        }
                    }
                });
            }
        };

        t.start();
        } else {
            Toast.makeText(SearchActivity.this, getString(R.string.NoConnect), Toast.LENGTH_LONG).show();
        }

    }

}

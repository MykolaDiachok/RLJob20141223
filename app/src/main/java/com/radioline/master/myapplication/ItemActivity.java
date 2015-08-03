package com.radioline.master.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.badoo.mobile.util.WeakHandler;
import com.radioline.master.basic.Item;
import com.radioline.master.basic.ItemViewAdapter;
import com.radioline.master.basic.SystemService;
import com.radioline.master.soapconnector.Converts;
import com.splunk.mint.Mint;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class ItemActivity extends Activity implements AdapterView.OnItemClickListener {


    //http://stackoverflow.com/questions/4373485/android-swipe-on-list
    private ListView lvItem;
    private WeakHandler handler = new WeakHandler();
    private ProgressDialog dialog;
    private ItemViewAdapter itemViewAdapter;
    //private SwipeDetector swipeDetector;
    private Thread t;
    private ArrayList<Item> itemArray;

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


        setContentView(R.layout.activity_item);

        this.setTitle(getIntent().getStringExtra("Name"));
        //swipeDetector = new SwipeDetector();
        lvItem = (ListView) findViewById(R.id.lvItem);
        lvItem.setOnItemClickListener(this);
        //lvItem.setOnTouchListener(swipeDetector);
//        Converts tg = new Converts();
//        try {
//            ItemViewAdapter itemViewAdapter = new ItemViewAdapter(this, tg.getItemsArrayListFromServer(getIntent().getStringExtra("parentid")));
//            lvItem.setAdapter(itemViewAdapter);
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        loadData();


    }

    private void loadData() {
        SystemService ss = new SystemService(this);
        if (ss.isNetworkAvailable()) {

            dialog = ProgressDialog.show(this, getString(R.string.ProgressDialogTitle),
                    getString(R.string.ProgressDialogMessage));
            t = new Thread() {

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
                        itemArray = tg.getItemsArrayListFromServer(getIntent().getStringExtra("parentid"));
                        if (itemArray != null)
                            itemViewAdapter = new ItemViewAdapter(ItemActivity.this, itemArray);

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
                            if ((itemViewAdapter != null) && (!itemViewAdapter.isEmpty())) {
                                lvItem.setAdapter(itemViewAdapter);
                            } else if ((itemArray != null)) {
                                Toast.makeText(ItemActivity.this, getString(R.string.NoData), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(ItemActivity.this, getString(R.string.NoConnect), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            };

            t.start();
        } else {
            Toast.makeText(ItemActivity.this, getString(R.string.NoConnect), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        Boolean rtvalue = true;
        switch (item.getItemId()) {
            case R.id.action_search:
                intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                rtvalue = true;
                break;
            case R.id.action_scan:
                intent = new Intent(this, ScanActivity.class);
                startActivity(intent);
                rtvalue = true;
                break;
            case R.id.action_basket:
                intent = new Intent(this, BasketActivity.class);
                startActivity(intent);
                rtvalue = true;
                break;
            case R.id.action_settings:
                rtvalue = true;
                break;
            case R.id.action_refresh:
                loadData();
                rtvalue = true;
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return rtvalue;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Item item = (Item) adapterView.getItemAtPosition(position);
//        if (swipeDetector.swipeDetected()){
//            if((swipeDetector.getAction()== SwipeDetector.Action.RL)||(swipeDetector.getAction()== SwipeDetector.Action.LR)){
//                Toast.makeText(ItemActivity.this,"swipe:"+item.getName()+" +1",Toast.LENGTH_SHORT).show();
//
//            }
//        } else {
        Intent intent = new Intent(this, PicActivity.class);
        intent.putExtra("itemid", item.getId());
        intent.putExtra("Name", item.getName());
        startActivity(intent);
//        }


    }
}

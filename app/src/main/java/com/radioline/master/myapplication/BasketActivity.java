package com.radioline.master.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.radioline.master.basic.Basket;
import com.radioline.master.basic.BasketViewAdapter;
import com.splunk.mint.Mint;

import java.util.List;

public class BasketActivity extends Activity {

    //private ParseQueryAdapter<ParseObject> mainAdapter;
    private ListView lvBasket;
    private BasketViewAdapter basketViewAdapter;


    @Override
    protected void onResume() {
        Mint.startSession(this);
        basketViewAdapter.notifyDataSetChanged();
        basketViewAdapter.loadObjects();
        lvBasket.setAdapter(basketViewAdapter);
        basketViewAdapter.loadObjects();
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Mint.closeSession(this);
        Mint.flush();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mint.initAndStartSession(this, getString(R.string.mint));

        ParseObject.registerSubclass(Basket.class);
        //ParseObject.registerSubclass(ParseGroups.class);
        //Parse.enableLocalDatastore(getApplicationContext());

        //Parse.initialize(this, "5pOXIrqgAidVKFx2mWnlMHj98NPYqbR37fOEkuuY", "oZII0CmkEklLvOvUQ64CQ6i4QjOzBIEGZfbXvYMG");

        setContentView(R.layout.activity_basket);


        basketViewAdapter = new BasketViewAdapter(this);
        lvBasket = (ListView) findViewById(R.id.lvBasket);
        //if((basketViewAdapter!=null)&&(!basketViewAdapter.isEmpty())) {
        lvBasket.setAdapter(basketViewAdapter);
        basketViewAdapter.loadObjects();
        //}


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_basket, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        Boolean rtvalue = true;
        switch (item.getItemId()) {
            case R.id.action_settings:
                rtvalue = true;
                break;
            case R.id.action_dispatch:
                intent = new Intent(this, DispatchActivity.class);
                startActivity(intent);
                rtvalue = true;
                break;
            case R.id.action_clearbasket:
                ParseQuery<Basket> query = Basket.getQuery();
                query.fromLocalDatastore();
                try {
                    List<Basket> basketList = query.find();
                    for (Basket ibasket : basketList) {
                        ibasket.unpinInBackground();
                    }
                    basketViewAdapter.notifyDataSetChanged();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                rtvalue = true;
                break;
            case R.id.action_refresh:
                basketViewAdapter.notifyDataSetChanged();
                basketViewAdapter.loadObjects();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }


        return rtvalue;
    }


}

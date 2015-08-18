package com.radioline.master.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.badoo.mobile.util.WeakHandler;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.radioline.master.basic.Group;
import com.radioline.master.basic.GroupViewAdapter;


import java.util.ArrayList;
import java.util.List;


public class SecondGroupActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView lvSecond;
    private WeakHandler handler = new WeakHandler();
    private ProgressDialog dialog;
    private GroupViewAdapter groupViewAdapter;
    private Thread t;

    private ProgressDialog mProgressDialog;
    private List<ParseObject> ob;
    private List<Group> groups = null;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();

        if ((t != null) && (t.isAlive())) {
            t.interrupt();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Mint.enableDebug();

        setContentView(R.layout.activity_secondgroup);
        lvSecond = (ListView) findViewById(R.id.lvSecond);
        lvSecond.setOnItemClickListener(this);
        this.setTitle(getIntent().getStringExtra("Name"));

        new RemoteDataTask().execute();
        //loadData();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_secondgroup, menu);
        return true;
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
                new RemoteDataTask().execute();
                rtvalue = true;
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return rtvalue;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Group itemgroup = (Group) adapterView.getItemAtPosition(position);
        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra("parentid", itemgroup.getGroupid());
        intent.putExtra("Name", itemgroup.getName());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

    }

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(SecondGroupActivity.this);
            mProgressDialog.setTitle(getString(R.string.ProgressDialogMessage));
            mProgressDialog.setMessage(getString(R.string.ProgressDialogTitle));
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            groups = new ArrayList<Group>();
            try {
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ParseGroups");
                query.whereEqualTo("parentid", getIntent().getStringExtra("parentid"));
                query.orderByAscending("sortcode");
                ob = query.find();
                for (ParseObject pgroup : ob) {
                    Group map = new Group(pgroup);
                    groups.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            // Pass the results into ListViewAdapter.java
            groupViewAdapter = new GroupViewAdapter(SecondGroupActivity.this,
                    groups);
            // Binds the Adapter to the ListView
            lvSecond.setAdapter(groupViewAdapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }
}

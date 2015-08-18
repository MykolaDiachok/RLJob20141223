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

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.radioline.master.basic.Group;
import com.radioline.master.basic.GroupViewAdapter;
import com.radioline.master.soapconnector.MultiLoadingImage;


import java.util.ArrayList;
import java.util.List;


public class FirstGroupActivity extends Activity implements AdapterView.OnItemClickListener {


    //https://play.google.com/apps/
    //https://code.google.com/p/android-query/wiki/API
    private ListView listView;
    private GroupViewAdapter groupViewAdapter;
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

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_firstgroup);
        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(this);

        new RemoteDataTask().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_firstgroup, menu);
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
                break;
            case R.id.action_updateallimages:
                downloadAllImages();
                rtvalue = true;
                break;
            case R.id.action_refresh:
                new RemoteDataTask().execute();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return rtvalue;
    }

    private void downloadAllImages() {
        MultiLoadingImage mli = new MultiLoadingImage(FirstGroupActivity.this);
        mli.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Group itemgroup = (Group) adapterView.getItemAtPosition(position);
        Intent intent = new Intent(this, SecondGroupActivity.class);
        intent.putExtra("parentid", itemgroup.getGroupid());
        intent.putExtra("Name", itemgroup.getName());
        startActivity(intent);
    }

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(FirstGroupActivity.this);
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
                query.whereEqualTo("parentid", "00000000-0000-0000-0000-000000000000");
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
            groupViewAdapter = new GroupViewAdapter(FirstGroupActivity.this,
                    groups);
            // Binds the Adapter to the ListView
            listView.setAdapter(groupViewAdapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }


}

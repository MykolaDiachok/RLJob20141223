package com.radioline.master.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.radioline.master.basic.AsyncTaskCompleteListener;
import com.radioline.master.basic.Group;
import com.radioline.master.basic.GroupViewAdapter;
import com.radioline.master.myapplication.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;


public class GroupsFragment extends Fragment  {
    @Bind(R.id.listView)
    ListView listView;

    private GroupViewAdapter groupViewAdapter;
    private ProgressDialog mProgressDialog;

    public class GroupsAsyncTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<Group>>
    {

        @Override
        public void onTaskComplete(ArrayList<Group> result)
        {
            groupViewAdapter = new GroupViewAdapter(getActivity(),result);
//            // Binds the Adapter to the ListView
            listView.setAdapter(groupViewAdapter);
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_groups, container, false);
        ButterKnife.bind(this, view);
        //new RemoteDataTask().execute();
        new RemoteDataTask(this.getActivity(),new GroupsAsyncTaskCompleteListener()).execute();
        return view;
    }



    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, ArrayList<Group>> {
        private ArrayList<Group> tGroupArrayList;
        private Context context;
        private AsyncTaskCompleteListener<ArrayList<Group>> listener;

        public RemoteDataTask(Context ctx, AsyncTaskCompleteListener<ArrayList<Group>> listener)
        {
            this.context = ctx;
            this.listener = listener;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//            mProgressDialog = new ProgressDialog(getActivity());
//            mProgressDialog.setTitle(getString(R.string.ProgressDialogMessage));
//            mProgressDialog.setMessage(getString(R.string.ProgressDialogTitle));
//            mProgressDialog.setIndeterminate(false);
//            mProgressDialog.show();
        }

        @Override
        @DebugLog
        protected ArrayList<Group> doInBackground(Void... params) {

            tGroupArrayList = new ArrayList<Group>();
            try {
                ParseQuery<Group> query = Group.getQuery();
                query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
                query.whereEqualTo("firstGroup", true);
                query.whereEqualTo("Enable", true);
                query.orderByAscending("sortcode");
                List<Group> groupList = query.find();
                for (Group pgroup : groupList) {
                    tGroupArrayList.add(pgroup);
                    Group newGroup = new Group();


                    ParseQuery<Group> queryChild = Group.getQuery();
                    query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
                    queryChild.whereEqualTo("parseGroupId", pgroup);
                    queryChild.whereEqualTo("Enable", true);
                    queryChild.orderByAscending("sortcode");
                    List<Group> groupListChild = queryChild.find();
                    for (Group pgroupChild : groupListChild) {
                        tGroupArrayList.add(pgroupChild);
                    }

                }

            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return tGroupArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Group> groups) {
            super.onPostExecute(groups);
            listener.onTaskComplete(groups);
//            // Pass the results into ListViewAdapter.java
            //groupViewAdapter = new GroupViewAdapter(getActivity(),tGroupArrayList);
//            // Binds the Adapter to the ListView
            //listView.setAdapter(groupViewAdapter);
//            // Close the progressdialog
            if (mProgressDialog!=null)
            mProgressDialog.dismiss();
        }
    }





}

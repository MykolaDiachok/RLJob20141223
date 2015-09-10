package com.radioline.master.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.radioline.master.myAbstract.ButterKnifeFragment;
import com.radioline.master.myapplication.R;
import com.radioline.master.tools.AsyncTaskListener;
import com.radioline.master.basic.Group;
import com.radioline.master.basic.GroupViewAdapter;

import com.radioline.master.myAbstract.AsyncTaskSceleton;
import com.radioline.master.tools.DisplayOrientation;

import java.io.Closeable;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import hugo.weaving.DebugLog;


public class GroupsFragment extends ButterKnifeFragment {
    @Bind(R.id.listView)
    ListView listView;

    private ArrayList<Group> tGroupArrayList= new ArrayList<Group>();
    private ProgressDialog dialog;

    private Context context;

    private GroupViewAdapter groupViewAdapter;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dialog.isShowing()) dialog.dismiss();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tGroupArrayList = new ArrayList<Group>();
        ParseQuery<Group> query = new ParseQuery<Group>("ParseGroups");
        query.setLimit(500);
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        //query.whereEqualTo("firstGroup", true);
        query.whereEqualTo("Enable", true);
        query.orderByAscending("sortcode");

        context = getActivity().getApplicationContext();
        DisplayOrientation.getInstance(context, getActivity()).blockOrientation();
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getActivity().getResources().getString(R.string.ProgressDialogTitle));
        dialog.show();

        query.findInBackground(new FindCallback<Group>() {


            @Override
            public void done(List<Group> list, ParseException e) {
                if (e==null){
                    tGroupArrayList.clear();
                    tGroupArrayList.addAll(list);
                    //groupViewAdapter.notifyDataSetChanged();
                    if (isAdded()) {
                        groupViewAdapter = new GroupViewAdapter(getActivity(), tGroupArrayList);
                        listView.setAdapter(groupViewAdapter);
                    }

                }

                if (dialog.isShowing()) dialog.dismiss();
                if (isAdded()) {
                    DisplayOrientation.getInstance(context, getActivity()).enableOrientation();
                }
            }
        });



        //new RemoteDataTask(this.getActivity(),new GroupsAsyncTaskCompleteListener()).execute();
    }


//new RemoteDataTask(this.getActivity(),new GroupsAsyncTaskCompleteListener()).execute();




    /**
     * @return the fragment layout id
     */
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_groups;
    }




    public class GroupsAsyncTaskCompleteListener implements AsyncTaskListener<Void, ArrayList<Group>>
    {

        @Override
        public Context getContext() {
            return getActivity();
        }

        @Override
        public void onPreExecute() {

        }

        @Override
        public void onProgressUpdate(Void... progress) {

        }


        @Override
        public void onCancelled() {

        }

        @Override
        public void onPostExecute(ArrayList<Group> result)
        {
            groupViewAdapter = new GroupViewAdapter(getActivity(),result);
//            // Binds the Adapter to the ListView
            listView.setAdapter(groupViewAdapter);
        }
    }





    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTaskSceleton<Void, Void, ArrayList<Group>> {


        public RemoteDataTask(Context context, AsyncTaskListener<Void, ArrayList<Group>> callBack) {
            super(context, callBack);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        @DebugLog
        protected ArrayList<Group> doInBackground(Void... params) {

            ArrayList<Group> tGroupArrayList = new ArrayList<Group>();
            try {
                ParseQuery<Group> query = Group.getQuery();
                query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
                query.whereEqualTo("firstGroup", true);
                query.whereEqualTo("Enable", true);
                query.orderByAscending("sortcode");
                List<Group> groupList = query.find();
                for (Group pgroup : groupList) {
                    tGroupArrayList.add(pgroup);

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

        }
    }





}

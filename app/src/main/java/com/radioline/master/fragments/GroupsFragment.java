package com.radioline.master.fragments;


import android.app.ProgressDialog;
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
import com.radioline.master.basic.Group;
import com.radioline.master.basic.GroupViewAdapter;
import com.radioline.master.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class GroupsFragment extends Fragment {
    @Bind(R.id.listView)
    ListView listView;

    private GroupViewAdapter groupViewAdapter;
    private ProgressDialog mProgressDialog;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        //View view = inflater.inflate(R.layout.fragment_groups, container, false);
//        View view = super.onCreateView(inflater, container, savedInstanceState);
//        this.inflater = inflater;
//        //getListView().setSelector(R.drawable.level_list_selector);
//       // view.setSelected(true);
//        //getListView().setItemChecked(0, true);
////        groupArrayList = new ArrayList<Group>();
////        groupViewAdapter = new GroupViewAdapter(inflater.getContext(),groupArrayList);
////        setListAdapter(groupViewAdapter);
////            try {
////                ParseQuery<Group> query = Group.getQuery();
////                query.whereEqualTo("parentid", "00000000-0000-0000-0000-000000000000");
////                query.orderByAscending("sortcode");
////                List<Group> groupList = query.find();
////                groupArrayList.addAll(groupList);
////                groupViewAdapter = new GroupViewAdapter(inflater.getContext(),groupArrayList);
////                setListAdapter(groupViewAdapter);
//////                for (Group pgroup : groupList) {
//////                    tGroupArrayList.add(pgroup);
//////                }
////            } catch (ParseException e) {
////                Log.e("Error", e.getMessage());
////                e.printStackTrace();
////            }
//
//
//   //          new RemoteDataTask().execute();
//        return view;
        //return inflater.inflate(R.layout.fragment_groups,null);

        View view = inflater.inflate(R.layout.fragment_groups, container, false);
        ButterKnife.bind(this, view);
        // TODO Use fields...
        return view;
    }





    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        private ArrayList<Group> tGroupArrayList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle(getString(R.string.ProgressDialogMessage));
            mProgressDialog.setMessage(getString(R.string.ProgressDialogTitle));
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            tGroupArrayList = new ArrayList<Group>();
            try {
                ParseQuery<Group> query = Group.getQuery();
                query.whereEqualTo("parentid", "00000000-0000-0000-0000-000000000000");
                query.orderByAscending("sortcode");
                List<Group> groupList = query.find();
                for (Group pgroup : groupList) {
                    tGroupArrayList.add(pgroup);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

//            GroupsFragment resultFrag = (GroupsFragment) getSupportFragmentManager()
//                    .findFragmentByTag("groups");
            GroupsFragment resultFrag = (GroupsFragment) getFragmentManager().findFragmentByTag("groups");
            //(GroupsFragment)GroupsFragment.this.getActivity().getSupportFragmentManager().findFragmentByTag("groups");

            if (resultFrag != null) {
//                resultFrag.refreshData(tGroupArrayList);
            }

//            // Pass the results into ListViewAdapter.java
//            groupViewAdapter = new GroupViewAdapter(getActivity(),groupArrayList);
//            // Binds the Adapter to the ListView
//            bGetGroups.setAdapter(groupViewAdapter);
//            // Close the progressdialog
            if (mProgressDialog!=null)
            mProgressDialog.dismiss();
        }
    }





}

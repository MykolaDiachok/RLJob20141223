package com.radioline.master.basic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.radioline.master.fragments.GroupsFragment;
import com.radioline.master.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Master on 01.11.2014.
 */
public class GroupViewAdapter extends ArrayAdapter<Group> {
    private Context context;
    private ArrayList<Group> groupArrayList;
    //private List<Group> groups = null;
    private LayoutInflater inflater;
    int resources;




    public GroupViewAdapter(Context context, int resource,  ArrayList<Group> groupssArrayList){
        super(context, resource,groupssArrayList);
        this.context = context;
        this.groupArrayList = new ArrayList<Group>(groupssArrayList);
        this.resources = resource;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public GroupViewAdapter(Context context, ArrayList<Group> groupssArrayList) {
        super(context, R.layout.groupview, groupssArrayList);
        this.resources = R.layout.groupview;
        this.context = context;
        this.groupArrayList = new ArrayList<Group>(groupssArrayList);
        this.inflater = LayoutInflater.from(context);
    }




    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public GroupViewAdapter(Context context, List<Group> groups) {
        super(context, R.layout.groupview, groups);
        this.context = context;
        //this.groups = groups;
        this.resources = R.layout.groupview;
        this.inflater = LayoutInflater.from(context);
        this.groupArrayList = new ArrayList<Group>();
        this.groupArrayList.addAll(groups);

    }

    @Override
    public int getCount() {
        return groupArrayList.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Group tempGroup = groupArrayList.get(position);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
        if (!"00000000-0000-0000-0000-000000000000".equals(tempGroup.getParentid())) {
            rowView = inflater.inflate(R.layout.groupview_second, parent, false);
        }
        else {
            rowView = inflater.inflate(resources, parent, false);
        }
        TextView textView = (TextView) rowView.findViewById(R.id.tvName);
        textView.setText(tempGroup.getName());
        return rowView;

//        ViewHolder holder;
//
//        Group tempGroup = groupArrayList.get(position);
//
//        if (view != null) {
//            holder = (ViewHolder) view.getTag();
//        } else {
//            if (!"00000000-0000-0000-0000-000000000000".equals(tempGroup.getParentid())) {
//                view = inflater.inflate(R.layout.groupview_second, parent, false);
//            }
//            else{
//                view = inflater.inflate(resources, parent, false);
//            }
//
//            holder = new ViewHolder(view);
//            view.setTag(holder);
//        }
//
//        holder.tvName.setText(tempGroup.getName());
//
//
//
//        return view;
    }

    static class ViewHolder {
        @Bind(R.id.tvName) TextView tvName;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
        // public TextView tvId;
        //public TextView tvCode;
    }


}

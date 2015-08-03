package com.radioline.master.basic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.radioline.master.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Master on 01.11.2014.
 */
public class GroupViewAdapter extends ArrayAdapter<Group> {
    private Context context;
    private ArrayList<Group> groupArrayList;
    private List<Group> groups = null;
    private LayoutInflater inflater;

    public GroupViewAdapter(Context context, ArrayList<Group> groupssArrayList) {
        super(context, R.layout.groupview, groupssArrayList);

        this.context = context;
        this.groupArrayList = groupssArrayList;
    }


    public GroupViewAdapter(Context context, List<Group> groups) {
        super(context, R.layout.groupview, groups);
        this.context = context;
        this.groups = groups;
        this.inflater = LayoutInflater.from(context);
        this.groupArrayList = new ArrayList<Group>();
        this.groupArrayList.addAll(groups);

    }

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public Group getItem(int position) {
        return groups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.groupview, null);
            holder.tvName = (TextView) view.findViewById(R.id.tvName);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tvName.setText(groupArrayList.get(position).getName());
        return view;
    }

    static class ViewHolder {
        public TextView tvName;
        // public TextView tvId;
        //public TextView tvCode;
    }


}

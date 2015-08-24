package com.radioline.master.basic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.radioline.master.myapplication.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by master on 24.08.2015.
 */
public class GroupSimpleAdapter extends ArrayAdapter<Group> {
    private final LayoutInflater mInflater;

    public GroupSimpleAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_2);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<Group> data) {
        clear();
        if (data != null) {
            addAll(data);
//            for (Group appEntry : data) {
//                add(appEntry);
//            }
        }
    }

    /**
     * Populate new items in the list.
     */
    @Override public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.groupview, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Group item = getItem(position);
        holder.tvName.setText(item.getName());

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tvName)
        TextView tvName;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
        // public TextView tvId;
        //public TextView tvCode;
    }
}

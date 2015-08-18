package com.radioline.master.basic;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.radioline.master.myapplication.R;
import com.radioline.master.soapconnector.ImageDownloaderSOAP;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dyachok on 13.11.2014.
 */

public class BasketViewAdapter extends ArrayAdapter<Basket> {

    private ArrayList<Basket> basketArrayList;
    private Context context;

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public BasketViewAdapter(Context context, ArrayList<Basket> basketArrayList) {
        super(context, R.layout.itemview, basketArrayList);

        this.context = context;
        this.basketArrayList = basketArrayList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.itemview, null, true);
            holder = new ViewHolder();
            holder.tvItemName = (TextView) rowView.findViewById(R.id.tvItemName);
            holder.tvItemUSD = (TextView) rowView.findViewById(R.id.tvItemUSD);
            holder.tvItemUAH = (TextView) rowView.findViewById(R.id.tvItemUAH);
            holder.tvQuantity = (TextView) rowView.findViewById(R.id.tvQuantity);
            holder.ivItem = (ImageView) rowView.findViewById(R.id.ivItem);
            holder.btAdd = (Button) rowView.findViewById(R.id.btAdd);
            holder.btDel = (Button) rowView.findViewById(R.id.btDel);

            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        Basket curObject =  basketArrayList.get(position);
        this.setOnClickListener(holder.btAdd,curObject);
        this.setOnClickListener(holder.btDel, curObject);
        //this.setOnClickListener(holder.ivItem, basketArrayList.get(position));
        holder.tvItemName.setText(curObject.getName());


        DecimalFormat dec = new DecimalFormat("0.00");

        holder.tvItemUSD.setText("$ " + dec.format(curObject.getRequiredpriceUSD()));
        holder.tvItemUAH.setText("₴ " + dec.format(curObject.getRequiredpriceUAH()));

        //        TextView tvQuantity = (TextView) v.findViewById(R.id.tvQuantity);
//        tvQuantity.setText("Quantity:" + String.valueOf(object.getQuantity()));
        holder.tvQuantity.setText("Quantity:" + String.valueOf(curObject.getQuantity()));

        ImageDownloaderSOAP getimage = new ImageDownloaderSOAP();
        getimage.download(curObject.getProductId(), holder.ivItem, null, false);

        return rowView;
    }

    private void setOnClickListener(Button btListener, final Basket object) {
        //btListener.setTag(position);
        btListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btAdd:
                        addItem(object);
                        break;
                    case R.id.btDel:
                        delItem(object);
                        break;
                }

            }
        });
    }


    private void reloadDataFromBase()  {
        ParseQuery query = Basket.getQuery();
        query.fromLocalDatastore();
        query.whereGreaterThan("quantity", 0);
        try {
            List<Basket> list = query.find();
            basketArrayList.clear();
            for (Basket i:list){
                basketArrayList.add(i);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void addItem(Basket object) {
        //int pos = Integer.parseInt(v.getTag().toString());
        //Item finalitem = itemArrayList.get(pos);
        ParseQuery<Basket> query = Basket.getQuery();
        query.fromLocalDatastore();
        query.whereEqualTo("productId", object.getProductId());
        int currentcount = 1;
        try {
            Basket localbasket = query.getFirst();
            currentcount = localbasket.getQuantity() + 1;
            localbasket.setQuantity(currentcount);
            localbasket.pin();
            reloadDataFromBase();
            this.notifyDataSetChanged();
        } catch (ParseException e) {
            e.printStackTrace();
        }

       // Toast.makeText(context, "add: " + object.getName() + "+1=" + currentcount, Toast.LENGTH_SHORT).show();
    }

    private void delItem(Basket object) {
        ParseQuery<Basket> query = Basket.getQuery();
        query.fromLocalDatastore();
        query.whereEqualTo("productId", object.getProductId());
        int currentcount = 0;
        try {
            Basket localbasket = query.getFirst();
            currentcount = localbasket.getQuantity() - 1;
            if (currentcount < 0) {
                currentcount = 0;
            }
            localbasket.setQuantity(currentcount);
            localbasket.pin();
            reloadDataFromBase();
            //localbasket.unpinInBackground();
            this.notifyDataSetChanged();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Toast.makeText(context, "del: " + object.getName() + "-1=" + currentcount, Toast.LENGTH_SHORT).show();
    }

    static class ViewHolder {
        public TextView tvItemName;
        public TextView tvItemUSD;
        public TextView tvItemUAH;
        public TextView tvQuantity;
        public ImageView ivItem;
        public Button btAdd;
        public Button btDel;

    }

}

//public class BasketViewAdapter extends ParseQueryAdapter<Basket> {
//
//    Context context;
//
//    public BasketViewAdapter(Context context) {
//
//        super(context, new ParseQueryAdapter.QueryFactory<Basket>() {
//            public ParseQuery create() {
//                ParseQuery query = Basket.getQuery();
//                query.fromLocalDatastore();
//                query.whereGreaterThan("quantity", 0);
//                return query;
//            }
//        });
//        this.context = context;
//    }
//
//
//    // Customize the layout by overriding getItemView
//    @Override
//    public View getItemView(Basket object, View v, ViewGroup parent) {
//        if (v == null) {
//            v = View.inflate(getContext(), R.layout.itemview, null);
//        }
//
//        super.getItemView(object, v, parent);
//        ImageView itemImage = (ImageView) v.findViewById(R.id.ivItem);
//        ImageDownloaderSOAP getimage = new ImageDownloaderSOAP();
//        getimage.download(object.getProductId(), itemImage, null, false);
//
//
//        TextView tvItemName = (TextView) v.findViewById(R.id.tvItemName);
//        tvItemName.setText(object.getName());
//
//        DecimalFormat dec = new DecimalFormat("0.00");
//        TextView tvItemUSD = (TextView) v.findViewById(R.id.tvItemUSD);
//        tvItemUSD.setText("$ " + dec.format(object.getQuantity() * object.getRequiredpriceUSD()));
//
//        TextView tvItemUAH = (TextView) v.findViewById(R.id.tvItemUAH);
//        tvItemUAH.setText("₴ " + dec.format(object.getQuantity() * object.getRequiredpriceUAH()));
//
//        TextView tvQuantity = (TextView) v.findViewById(R.id.tvQuantity);
//        tvQuantity.setText("Quantity:" + String.valueOf(object.getQuantity()));
//
//        Button btAdd = (Button) v.findViewById(R.id.btAdd);
//
//        Button btDel = (Button) v.findViewById(R.id.btDel);
//
//
//        this.setOnClickListener(btAdd, object);
//        this.setOnClickListener(btDel, object);
//
//
//        return v;
//    }
//
//    private void setOnClickListener(Button btListener, final Basket object) {
//        //btListener.setTag(position);
//        btListener.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()) {
//                    case R.id.btAdd:
//                        addItem(object);
//                        break;
//                    case R.id.btDel:
//                        delItem(object);
//                        break;
//                }
//
//            }
//        });
//    }
//
//    private void addItem(Basket object) {
//        //int pos = Integer.parseInt(v.getTag().toString());
//        //Item finalitem = itemArrayList.get(pos);
//        ParseQuery<Basket> query = Basket.getQuery();
//        query.fromLocalDatastore();
//        query.whereEqualTo("productId", object.getProductId());
//        int currentcount = 1;
//        try {
//            Basket localbasket = query.getFirst();
//            currentcount = localbasket.getQuantity() + 1;
//            localbasket.setQuantity(currentcount);
//            localbasket.pinInBackground();
//            this.notifyDataSetChanged();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        Toast.makeText(context, "add: " + object.getName() + "+1=" + currentcount, Toast.LENGTH_SHORT).show();
//    }
//
//    private void delItem(Basket object) {
//        ParseQuery<Basket> query = Basket.getQuery();
//        query.fromLocalDatastore();
//        query.whereEqualTo("productId", object.getProductId());
//        int currentcount = 0;
//        try {
//            Basket localbasket = query.getFirst();
//            currentcount = localbasket.getQuantity() - 1;
//            if (currentcount < 0) {
//                currentcount = 0;
//            }
//            localbasket.setQuantity(currentcount);
//            localbasket.pinInBackground();
//            //localbasket.unpinInBackground();
//            this.notifyDataSetChanged();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        Toast.makeText(context, "del: " + object.getName() + "-1=" + currentcount, Toast.LENGTH_SHORT).show();
//    }

//}

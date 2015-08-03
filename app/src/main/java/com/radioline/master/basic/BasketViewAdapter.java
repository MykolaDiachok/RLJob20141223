package com.radioline.master.basic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.radioline.master.myapplication.R;
import com.radioline.master.soapconnector.ImageDownloaderSOAP;

import java.text.DecimalFormat;

/**
 * Created by dyachok on 13.11.2014.
 */
public class BasketViewAdapter extends ParseQueryAdapter<Basket> {

    Context context;

    public BasketViewAdapter(Context context) {

        super(context, new ParseQueryAdapter.QueryFactory<Basket>() {
            public ParseQuery create() {
                ParseQuery query = Basket.getQuery();
                query.fromLocalDatastore();
                query.whereGreaterThan("quantity", 0);
                return query;
            }
        });
        this.context = context;
    }


    // Customize the layout by overriding getItemView
    @Override
    public View getItemView(Basket object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.itemview, null);
        }

        super.getItemView(object, v, parent);
        ImageView itemImage = (ImageView) v.findViewById(R.id.ivItem);
        ImageDownloaderSOAP getimage = new ImageDownloaderSOAP();
        getimage.download(object.getProductId(), itemImage, null, false);


        TextView tvItemName = (TextView) v.findViewById(R.id.tvItemName);
        tvItemName.setText(object.getName());

        DecimalFormat dec = new DecimalFormat("0.00");
        TextView tvItemUSD = (TextView) v.findViewById(R.id.tvItemUSD);
        tvItemUSD.setText("$ " + dec.format(object.getQuantity() * object.getRequiredpriceUSD()));

        TextView tvItemUAH = (TextView) v.findViewById(R.id.tvItemUAH);
        tvItemUAH.setText("₴ " + dec.format(object.getQuantity() * object.getRequiredpriceUAH()));

        TextView tvQuantity = (TextView) v.findViewById(R.id.tvQuantity);
        tvQuantity.setText("Quantity:" + String.valueOf(object.getQuantity()));

        Button btAdd = (Button) v.findViewById(R.id.btAdd);

        Button btDel = (Button) v.findViewById(R.id.btDel);


        this.setOnClickListener(btAdd, object);
        this.setOnClickListener(btDel, object);


        return v;
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
            localbasket.pinInBackground();
            this.notifyDataSetChanged();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Toast.makeText(context, "add: " + object.getName() + "+1=" + currentcount, Toast.LENGTH_SHORT).show();
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
            localbasket.pinInBackground();
            //localbasket.unpinInBackground();
            this.notifyDataSetChanged();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Toast.makeText(context, "del: " + object.getName() + "-1=" + currentcount, Toast.LENGTH_SHORT).show();
    }

}

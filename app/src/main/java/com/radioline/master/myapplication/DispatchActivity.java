package com.radioline.master.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.badoo.mobile.util.WeakHandler;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.radioline.master.basic.BaseValues;
import com.radioline.master.basic.Basket;
import com.radioline.master.basic.SystemService;
import com.radioline.master.soapconnector.Link;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class DispatchActivity extends Activity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private EditText etComments;
    private Switch swCurrency;
    private Button btSendToServer;
    private TextView tvSum;
    private DatePicker dpDeliveryDate;
    private WeakHandler handler;
    private ProgressDialog dialog;


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseObject.registerSubclass(Basket.class);
        //ParseObject.registerSubclass(ParseGroups.class);
        //Parse.enableLocalDatastore(getApplicationContext());

        //Parse.initialize(this, "5pOXIrqgAidVKFx2mWnlMHj98NPYqbR37fOEkuuY", "oZII0CmkEklLvOvUQ64CQ6i4QjOzBIEGZfbXvYMG");

        handler = new WeakHandler();

        setContentView(R.layout.activity_dispatch);
        etComments = (EditText) findViewById(R.id.etComments);
        swCurrency = (Switch) findViewById(R.id.swCurrency);
        swCurrency.setChecked(false);
        swCurrency.setOnCheckedChangeListener(this);
        tvSum = (TextView) findViewById(R.id.tvSum);
        btSendToServer = (Button) findViewById(R.id.btSendToServer);
        btSendToServer.setOnClickListener(this);
        setSumBasket(true);
        SystemService ss = new SystemService(this);
        if (!ss.isNetworkAvailable()) {
            btSendToServer.setEnabled(false);
        }

        dpDeliveryDate = (DatePicker) findViewById(R.id.dpDeliveryDate);
        // dpDeliveryDate.setCalendarViewShown(false);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.add(Calendar.DATE, 1);
        dpDeliveryDate.setMinDate(calendar.getTimeInMillis());
        calendar.add(Calendar.DATE, 13);
        dpDeliveryDate.setMaxDate(calendar.getTimeInMillis());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dispatch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setSumBasket(final Boolean USD) {
        ParseQuery<Basket> query = Basket.getQuery();
        query.fromLocalDatastore();
        //query.whereGreaterThan("quantity", 0);
        query.findInBackground(new FindCallback<Basket>() {
            public void done(List<Basket> BasketList,
                             ParseException e) {
                if (e == null) {
                    double sumInBasket = 0;
                    for (Basket ibasket : BasketList) {
                        if (USD)
                            sumInBasket = sumInBasket + (ibasket.getQuantity() * ibasket.getRequiredpriceUSD());
                        else
                            sumInBasket = sumInBasket + (ibasket.getQuantity() * ibasket.getRequiredpriceUAH());
                    }
                    DecimalFormat dec = new DecimalFormat("0.00");
                    if (USD)
                        tvSum.setText("$ " + dec.format(sumInBasket));
                    else
                        tvSum.setText("₴ " + dec.format(sumInBasket));
                }
            }
        });
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.swCurrency:
                setSumBasket(!b);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSendToServer:
                int day = dpDeliveryDate.getDayOfMonth();
                int month = dpDeliveryDate.getMonth()+1;
                int year = dpDeliveryDate.getYear();
                GregorianCalendar gC = new GregorianCalendar(year,month, day);

                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ss");

                //Date nD = new Date(year,month,day);
                //nD.setYear(dpDeliveryDate.getYear() - 1900);
//                    nD.setMonth(dpDeliveryDate.getMonth());
//                    nD.setDate(dpDeliveryDate.getDayOfMonth());
                String dd = dateFormat.format(gC.getTime());


                SentToServer(dd);
                break;
        }
    }

    private void SentToServer(final String tempDate) {
        //Method SetOrder
        //Input Order
        /* ***************order***********
        OrderNumber
        PartnerId
        ContractId
        NewOrder
        Description
        DeliveryDate
        Table
        *******************Table***************
        * RowOrder
        ProductId
        ProductCode
        ProductArticle
        ProductPartNumber
        Quantity
        RequiredPrice
        *
         */


        SystemService ss = new SystemService(this);
        if (ss.isNetworkAvailable()) {
            dialog = ProgressDialog.show(this, getString(R.string.ProgressDialogTitle),
                    getString(R.string.ProgressDialogMessage));
            Thread t = new Thread() {
                public void run() {


                    Link link = new Link();
                    PropertyInfo pi0 = new PropertyInfo();
                    pi0.setName("PartnerId");
                    pi0.setValue(BaseValues.GetValue("PartnerId"));
                    pi0.setType(String.class);

                    PropertyInfo pi1 = new PropertyInfo();
                    pi1.setName("Currency");
                    String cur = "USD";
                    if (swCurrency.isChecked()) cur = "UAH";
                    pi1.setValue(cur);
                    pi1.setType(String.class);


                    String rtvalue = link.getFromServerSoapPrimitive("GetContractId", new PropertyInfo[]{pi0, pi1}).toString();

                    SoapObject Order = new SoapObject("http://www.rl.ua", "Order");
                    Order.addProperty("PartnerId", BaseValues.GetValue("PartnerId"));
                    Order.addProperty("ContractId", rtvalue); // Основной договор
                    Order.addProperty("NewOrder", true);
                    Order.addProperty("Description", "" + etComments.getText().toString());

                    Order.addProperty("DeliveryDate", tempDate);

                    //ArrayList<SoapObject> rowOrders = new ArrayList<SoapObject>();
                    SoapObject rowOrders = new SoapObject("http://www.rl.ua", "RowOrders");


                    ParseQuery<Basket> query = Basket.getQuery();
                    query.fromLocalDatastore();
                    try {
                        List<Basket> basketList = query.find();
                        for (Basket ibasket : basketList) {
                            SoapObject rowOrder = new SoapObject("http://www.rl.ua", "RowOrder");
                            rowOrder.addProperty("ProductId", ibasket.getProductId().toString());
                            rowOrder.addProperty("Quantity", +ibasket.getQuantity());
                            DecimalFormat df = new DecimalFormat("0.00");
                            String fPrice = df.format(ibasket.getRequiredpriceUSD());
                            rowOrder.addProperty("RequiredPrice", fPrice);

                            rowOrders.addSoapObject(rowOrder);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

//                    Link link = new Link();
                    SoapPrimitive returnSoapPrimitive = link.setToServerSoapObjectgetSoapPrimitive("SetOrder", Order, rowOrders);
                    Boolean rt = false;
                    if (returnSoapPrimitive != null) {
                        rt = Boolean.valueOf(returnSoapPrimitive.getValue().toString());
                    }
                    if (rt) {
                        ParseQuery<Basket> querydel = Basket.getQuery();
                        querydel.fromLocalDatastore();
                        try {
                            List<Basket> basketList = querydel.find();
                            for (Basket ibasket : basketList) {
                                ibasket.unpinInBackground();
//                                ibasket.notify();
                            }

//                            basketViewAdapter.notifyDataSetChanged();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        finish();
                    }
                    final Boolean rtToToast = rt;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (rtToToast) {
                                Toast.makeText(DispatchActivity.this, getString(R.string.OrderSendGood), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(DispatchActivity.this, getString(R.string.OrderSendBad), Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                    handler.post(new Runnable() {
                        public void run() {
                            if (dialog != null) {
                                if (dialog.isShowing()) {
                                    try {
                                        dialog.dismiss();
                                    } catch (IllegalArgumentException e) {
                                        e.printStackTrace();
                                    }
                                    ;


                                }
                            }

                        }

                        ;
                    });
                }
            };

            t.start();
        } else {
            Toast.makeText(DispatchActivity.this, getString(R.string.NoConnect), Toast.LENGTH_LONG).show();
        }


        //Order.

    }
}

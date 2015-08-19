package com.radioline.master.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseConfig;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.radioline.master.basic.BaseValues;
import com.radioline.master.tools.DisplayOrientation;
import com.radioline.master.tools.TestNetworks;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;


public class LoginActivity extends Activity {
    @Bind(R.id.btLogin)
    Button btLogin;
    @Bind(R.id.etID)
    EditText etID;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.tvLoginInfoView)
    TextView tvLoginInfo;
    String setID,setPassword;
    private Boolean connection;
    private Context context;
    @Bind(R.id.ibtNetworkInfo)
    ImageButton ibtNetworkInfo;
    private ProgressDialog progressD = null;
    boolean OnCLickbtLogin = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        context = LoginActivity.this.getApplicationContext();

        connection = TestNetworks.getInstance(this).isOnline();

        String userID = BaseValues.GetValue("UserId", context);
        if (userID != null)
            etID.setText(userID);

        etPassword = (EditText) findViewById(R.id.etPassword);
        String passwordId = BaseValues.GetValue("PasswordId",context);
        if (passwordId != null)
            etPassword.setText(passwordId);

        if (!((userID.isEmpty())&&(passwordId.isEmpty())))
        {
            tvLoginInfo.setText("Loaded user info from cache");
            tvLoginInfo.setTextColor(Color.BLUE);
        }
        if (connection){
            ibtNetworkInfo.setImageDrawable(getResources().getDrawable(R.drawable.greenbutton));
        }
        else{
            ibtNetworkInfo.setImageDrawable(getResources().getDrawable(R.drawable.redbutton));
            Toast.makeText(LoginActivity.this, "Unable to connect to the internet"
                    , Toast.LENGTH_LONG).show();
        }

        //ParseAnalytics.trackEventInBackground("open - LoginActivity");
    }

    @Override
    protected void onDestroy() {
        CancelProgressDialog();
        super.onDestroy();
    }

    public void CancelProgressDialog(){
        if ((LoginActivity.this.progressD != null)){
            LoginActivity.this.progressD.dismiss();
            LoginActivity.this.progressD = null;
        }
        DisplayOrientation.getInstance(context,this).enableOrientation();

    }

    @DebugLog
    @OnClick(R.id.btLogin)
    public void OnCLickbtLogin(){
        if (OnCLickbtLogin)
            return;

        OnCLickbtLogin=true;

        if (!TestNetworks.getInstance(this).isOnline())
        {
            Log.d("LoginActivity", "Unable to connect to the internet");
            Toast.makeText(LoginActivity.this, "Unable to connect to the internet"
                    , Toast.LENGTH_LONG).show();
            tvLoginInfo.setText("Unable to connect to the internet");
            tvLoginInfo.setTextColor(Color.RED);
            return;
        }

        DisplayOrientation.getInstance(context, this).blockOrientation();
        this.progressD = ProgressDialog.show(this,"Connecting...","Connecting to server...",true,false);
        tvLoginInfo.setText("");
        setID = etID.getText().toString();
        setPassword = etPassword.getText().toString();


        if ((setID.isEmpty())||(setPassword.isEmpty())){
            Toast.makeText(LoginActivity.this, "Emtpy ID or Password, please input identification"
                    , Toast.LENGTH_LONG).show();
            tvLoginInfo.setText("Emtpy ID or Password, please input identification");
            tvLoginInfo.setTextColor(Color.RED);
            Log.d("LoginActivity", "Emtpy login and password");
            CancelProgressDialog();
            return;
        }

        Integer tlog = null;

        try {
            tlog = Integer.parseInt(setID);
        } catch (NumberFormatException e) {
            e.printStackTrace();

            Toast.makeText(LoginActivity.this, "Error name in login, only numbers"
                    , Toast.LENGTH_LONG).show();
            tvLoginInfo.setText("Error name in login, only numbers");
            tvLoginInfo.setTextColor(Color.RED);
            CancelProgressDialog();
            return;
        }
        setID = tlog.toString();


        ParseUser.logInInBackground(setID, setPassword, new LogInCallback() {

            @DebugLog
            @Override
            public void done(ParseUser parseUser, com.parse.ParseException e) {
                if (parseUser != null) {
                    ParseConfig.getInBackground();
                    if (!ParseUser.getCurrentUser().getBoolean("Enable")) {
                        //ParseAnalytics.trackEventInBackground("Error login, disable user");
                        Log.d("LoginActivity", "Error login, disable user");

                        Toast.makeText(LoginActivity.this, "Error login, disable user, please contact to manager"
                                , Toast.LENGTH_LONG).show();

                        tvLoginInfo.setText("Error login, disable user, please contact to manager");
                        tvLoginInfo.setTextColor(Color.RED);
                        return;
                    }
                    Log.d("LoginActivity", "Login-Ok");

                    App.tracker().set("&uid", setID);



                    App.tracker().send(new HitBuilders.EventBuilder().setCategory("UX").setAction("User Sign In").build());

                    Context context = LoginActivity.this.getApplicationContext();
                    BaseValues.SetValue("UserId", setID,context);
                    BaseValues.SetValue("PasswordId", setPassword,context);
                    BaseValues.SetValue("PartnerId", ParseUser.getCurrentUser().get("PartnerId").toString(),context);

                    ParseUser.getCurrentUser().increment("RunCount");
                    ParseUser.getCurrentUser().saveInBackground();

                    Intent intent = new Intent(LoginActivity.this, FirstGroupActivity.class);
                    startActivity(intent);
                } else {
                    if (!TestNetworks.getInstance(LoginActivity.this).isOnline())
                    {
                        Log.d("LoginActivity", "Unable to connect to the internet");
                        Toast.makeText(LoginActivity.this, "Unable to connect to the internet"
                                , Toast.LENGTH_LONG).show();
                        tvLoginInfo.setText("Unable to connect to the internet");
                        tvLoginInfo.setTextColor(Color.RED);
                    }
                    else {
                        //ParseAnalytics.trackEventInBackground("Error login, unknown user&password");
                        Log.d("LoginActivity", "Error login, unknown user&password");
                        Toast.makeText(LoginActivity.this, "Error login, unknown user&password"
                                , Toast.LENGTH_LONG).show();
                        tvLoginInfo.setText("Error login, unknown user&password");
                        tvLoginInfo.setTextColor(Color.RED);
                    }


                }
                CancelProgressDialog();
                OnCLickbtLogin=false;
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            }

        });
    }


    private void updateUI(Intent intent) {
        if (TestNetworks.getInstance(this).isOnline()){
            //ibtNetworkInfo.setImageDrawable(getResources().getDrawable(R.drawable.greenbutton));
            ibtNetworkInfo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.greenbutton));
        }
        else{
            ///ibtNetworkInfo.setImageDrawable(getResources().getDrawable(R.drawable.redbutton));
            ibtNetworkInfo.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.redbutton));
        }
    }

    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };

    @Override
    protected void onResume() {
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        super.onResume();
    }

    @Override
    protected void onPause() {
        if (networkChangeReceiver!=null){
            unregisterReceiver(networkChangeReceiver);
            networkChangeReceiver = null;}
        super.onPause();
    }
}

package com.radioline.master.soapconnector;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapPrimitive;

/**
 * Created by master on 06.11.2014.
 */
public class LinkAsyncTaskGetSoapPrimitive extends AsyncTask<PropertyInfo, Void, SoapPrimitive> {

    private String method_name;
    private final Link link = new Link();

    public LinkAsyncTaskGetSoapPrimitive(String method_name) {
        this.method_name = method_name;
    }

    public String getMethod_name() {
        return method_name;
    }

    public void setMethod_name(String method_name) {
        this.method_name = method_name;
    }

    @Override
    protected SoapPrimitive doInBackground(PropertyInfo... propertiesInfo) {
        if (link.getISWorkUrl()){
            return link.getFromServerSoapPrimitive(method_name, propertiesInfo);
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(SoapPrimitive result) {
        super.onPostExecute(result);
    }

}

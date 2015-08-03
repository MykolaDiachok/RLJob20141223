package com.radioline.master.soapconnector;

import android.os.AsyncTask;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;


/**
 * Created by master on 01.11.2014.
 */
public class LinkAsyncTaskGetSoapObject extends AsyncTask<PropertyInfo, Void, SoapObject> {

    private String method_name;
    private Link link;

    public LinkAsyncTaskGetSoapObject(String method_name) {
        this.method_name = method_name;
    }

    public String getMethod_name() {
        return method_name;
    }

    public void setMethod_name(String method_name) {
        this.method_name = method_name;
    }

    @Override
    protected SoapObject doInBackground(PropertyInfo... propertiesInfo) {
        if (link.getISWorkUrl()){
            return link.getFromServerSoapObject(method_name,propertiesInfo);}
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        link = new Link();
    }




    protected void onPostExecute() {
        //super.onPostExecute();
    }
}

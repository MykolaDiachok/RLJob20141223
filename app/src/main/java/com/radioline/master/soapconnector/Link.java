package com.radioline.master.soapconnector;


import com.parse.ParseConfig;
import com.parse.ParseException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by master on 28.10.2014.
 */
public class Link {
    private String nameSpace;
    private String url;
    private boolean debug = false;
    private Boolean workUrl;

    public Link() {
        nameSpace = "http://www.rl.ua";

        workUrl = false;

        ParseConfig conf = ParseConfig.getCurrentConfig();
//      if parseconfig empty value
//        if (conf.params.size()==0) {
//            try {
//                ParseConfig.get();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            conf = ParseConfig.getCurrentConfig();
//        }
        if (isConnectedToServer(conf.getString("globalServer"), 5000)) {
            url = conf.getString("globalConnection");
            workUrl = true;
        } else if (isConnectedToServer(conf.getString("localServer"), 5000)) {
            url = conf.getString("localConnection");
            workUrl = true;
        }





    }


    public Boolean getISWorkUrl() {
        return workUrl;
    }

    public void setIsWorkUrl(Boolean workUrl) {
        this.workUrl = workUrl;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isConnectedToServer(String urlsite, int timeout) {
        Boolean ret = false;
//        try {
////            Pattern p = Pattern.compile("[https]://([\\w\\d\\.-]+)[:\\d]*/");
////            Matcher m = p.matcher(urlsite);
////            if (m.matches()) {
////                String searchsite = m.group(1);
//                InetAddress inet = InetAddress.getByName(urlsite);//.getByAddress(new byte[]{(byte) 173, (byte) 194, 32, 38});
//                ret = inet.isReachable(5000);
////            }
//
//
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            URL myUrl = new URL(urlsite);
            SSLConection.allowAllSSL();
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(timeout);
            connection.connect();
            ret = true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;

    }

    public SoapPrimitive getFromServerSoapPrimitive(String methodName) {
        String soapAction = nameSpace + "/" + methodName;
        //SoapObject resultRequestSoap;
        SoapObject request = new SoapObject(nameSpace,
                methodName);
        //SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(110);
        SSLConection.allowAllSSL();
        HttpTransportSE httpTransport = new HttpTransportSE(url);
        httpTransport.debug = this.debug;
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            envelope.encodingStyle = SoapSerializationEnvelope.ENC2003;
            envelope.dotNet = false;
            envelope.setOutputSoapObject(request);
            httpTransport.call(soapAction, envelope);
            return (SoapPrimitive) envelope.getResponse();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (HttpResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SoapPrimitive getFromServerSoapPrimitive(String methodName, PropertyInfo[] properties) {
        String soapAction = nameSpace + "/" + methodName;
        //SoapObject resultRequestSoap;
        SoapObject request = new SoapObject(nameSpace,
                methodName);
        for (PropertyInfo propertyInfo : properties) {
            request.addProperty(propertyInfo);
        }

        //SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(110);
        SSLConection.allowAllSSL();
        HttpTransportSE httpTransport = new HttpTransportSE(url);
        //httpTransport.debug = this.debug;
        httpTransport.debug = true;
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            envelope.encodingStyle = SoapSerializationEnvelope.ENC2003;
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            httpTransport.call(soapAction, envelope);
            return (SoapPrimitive) envelope.getResponse();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (HttpResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }


    public SoapObject getFromServerSoapObject(String methodName, PropertyInfo[] properties) {
        String soapAction = nameSpace + "/" + methodName;
        //SoapObject resultRequestSoap;
        SoapObject request = new SoapObject(nameSpace,
                methodName);
        for (PropertyInfo propertyInfo : properties) {
            request.addProperty(propertyInfo);
        }
        //SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(110);
        SSLConection.allowAllSSL();
        HttpTransportSE httpTransport = new HttpTransportSE(url);
        httpTransport.debug = this.debug;
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            envelope.encodingStyle = SoapSerializationEnvelope.ENC2003;
            envelope.dotNet = false;
            envelope.setOutputSoapObject(request);
            httpTransport.call(soapAction, envelope);
            return (SoapObject) envelope.getResponse();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (HttpResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public SoapObject getFromServerSoapObject(String methodName) {
        String soapAction = nameSpace + "/" + methodName;
        //SoapObject resultRequestSoap;
        SoapObject request = new SoapObject(nameSpace,
                methodName);
        //SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(110);
        SSLConection.allowAllSSL();
        HttpTransportSE httpTransport = new HttpTransportSE(url);
        httpTransport.debug = this.debug;
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            envelope.encodingStyle = SoapSerializationEnvelope.ENC2003;
            envelope.dotNet = false;
            envelope.setOutputSoapObject(request);
            httpTransport.call(soapAction, envelope);
            return (SoapObject) envelope.getResponse();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (HttpResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SoapPrimitive setToServerSoapObjectgetSoapPrimitive(String methodName, SoapObject... soapObjects) {
        //String nameSpace = "http://www.rl.ua";
        //String methodName = "SetOrder";
        String soapAction = nameSpace + "/" + methodName;
        SoapObject request = new SoapObject(nameSpace,
                methodName);
        for (SoapObject soapObject : soapObjects) {
            request.addSoapObject(soapObject);
        }
        //SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(110);
        SSLConection.allowAllSSL();
        HttpTransportSE httpTransport = new HttpTransportSE(url);
        //httpTransport.debug = this.debug;
        httpTransport.debug = this.debug;
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            envelope.encodingStyle = SoapSerializationEnvelope.ENC2003;
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            httpTransport.call(soapAction, envelope);
            return (SoapPrimitive) envelope.getResponse();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (HttpResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}



package com.org.nic.ts.webservice;

import android.os.AsyncTask;
import android.util.Log;

import com.org.nic.ts.LoginEmastya;
import com.org.nic.ts.custom.Utility;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class Authenticate extends AsyncTask<String, Void, SoapObject> {

    private int activityVal = 0;
    private LoginEmastya activity;
    private final static String TAG = Authenticate.class.getSimpleName();

    public Authenticate(LoginEmastya activity, int activityVal) {
        this.activity = activity;
        this.activityVal = activityVal;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (activityVal == 0) {
            // Check network available.
            if (!Utility.isNetworkAvailable(activity)) {
                activity.onError("Network error");
            }
        } else if (activityVal == 1) {
            // Check network available.
            if (!Utility.isNetworkAvailable(activity)) {
                activity.onError("Network error");
            }
           /* if (!Utility.isNetworkAvailable(activityLICNavigationMenu)) {
                activityLICNavigationMenu.onError("Network error");
            }*/
        }
    }

    @Override
    protected SoapObject doInBackground(String... strings) {

        SoapObject request = new SoapObject(Utility.WSDL_TARGET_NAMESPACE, Utility.OPERATION_NAME_ValidateLogin);

        request.addProperty("UserName", "" + strings[0]);
        request.addProperty("Pwd", "" + strings[1]);
        request.addProperty("IMEI_No", "" + strings[2]);
        request.addProperty("WS_UserName", "" + Utility.WS_UserName);
        request.addProperty("WS_Password", "" + Utility.WS_Password);
        request.addProperty("MobileVersion", "" + strings[3]);

        String Url ="";
//        if (activityVal == 0)
            Url= Utility.Url;
                   /* else
            Url= Utility.LIC_Url;*/

                   if (Utility.showLogs==0)
                       Log.d(TAG,"Request: "+request);

            return getXMLResult(request, Url,
                    "" + Utility.SOAP_ACTION_ValidateLogin);
//        return getXMLResult(request, Utility.SOAP_ADDRESS_ValidateLogin, ""+Utility.SOAP_ACTION_ValidateLogin);

    }

    /**
     * get service result and catching exceptions
     *
     * @param request
     * @param url
     * @param soapAction
     * @return
     */
    protected SoapObject getXMLResult(SoapObject request, String url, String soapAction) {
        try {
            return getServiceResult(url, soapAction, request);
        } catch (SoapFault e) {
            if (Utility.showLogs == 0)
                e.printStackTrace();
            return null;
        } catch (XmlPullParserException e) {
            if (Utility.showLogs == 0)
                e.printStackTrace();
            return null;
        } catch (IOException e) {
            if (Utility.showLogs == 0)
                e.printStackTrace();
            return null;
        } catch (Exception e) {
            if (Utility.showLogs == 0)
                e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(SoapObject response) {
        super.onPostExecute(response);

        if (response == null) {
            activity.onError("Getting Data Error");
        } else if (response.hasProperty("LoginVasData")) {//sProperty("Role")
//            Log.i(TAG, "code " + response.getProperty("Role").toString().trim());
//            if (response.getProperty("Role").toString().trim().length()>0) {
            //request success and received expecting data

            activity.parsingLoginResp(response);
//            } else {
//                activity.onError("Server Error");
//            }
        } else {
//            activity.parsingSupportedBanksList(response);
            activity.onError("Data Error");
        }
    }

    /**
     * Get result (list of soap objects) from web service
     *
     * @param strURL
     * @param strSoapAction
     * @param request
     * @return
     * @throws XmlPullParserException
     * @throws IOException
     */
    public static SoapObject getServiceResult(String strURL, String strSoapAction, SoapObject request)
            throws XmlPullParserException, IOException {
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;

        // Set output SOAP object
        envelope.setOutputSoapObject(request);

        // Create HTTP call object
//            HttpsTransportSE androidHttpTransport = new HttpsTransportSE(strURL);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(strURL);
        androidHttpTransport.debug = true;

        SoapObject response;

        // StringBuffer result = null;

        System.setProperty("http.keepAlive", "false");

        try {
            // Invoke web service
            androidHttpTransport.call(strSoapAction, envelope);

            // Get the response
            response = (SoapObject) envelope.getResponse();
            StringBuffer result;
            result = new StringBuffer(response.toString());
            if (Utility.showLogs == 0)
                Log.d(TAG, result.toString());

        } catch (SoapFault e) {
            if (Utility.showLogs == 0)
                Log.e(TAG, "SoapFaultException");
            throw e;
        } catch (XmlPullParserException e) {
            if (Utility.showLogs == 0)
                Log.e(TAG, "XmlPullParserException");
            throw e;
        } catch (IOException e) {
            if (Utility.showLogs == 0)
                Log.e(TAG, "IOException");
            throw e;
        } catch (Exception e) {
            if (Utility.showLogs == 0)
                Log.e(TAG, "HttpResponseException");
            throw e;
        }
        return response;
    }
}

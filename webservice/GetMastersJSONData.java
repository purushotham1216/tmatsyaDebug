package com.org.nic.ts.webservice;

import android.os.AsyncTask;
import android.util.Log;

import com.org.nic.ts.FishSeedStockingNavigation;
/*import com.org.nic.ts.FishSeedStockingPreviousDatesNav;
import com.org.nic.ts.HarvestingNavigation;
import com.org.nic.ts.PrawnSeedStockingNavigation;*/
import com.org.nic.ts.custom.Utility;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class GetMastersJSONData extends AsyncTask<String, Void, String> {

    private final static String TAG = GetMastersJSONData.class.getSimpleName();

    private String activityVal = "0";
    private int activityCalledVal = 0;//for 0--> HarvestingNavigation, 1-->FishSeedStockingNavigation

    private HarvestingNavigation activityHarvestingNavigation;

    public GetMastersJSONData(HarvestingNavigation activity, String val) {
        this.activityHarvestingNavigation = activity;
        activityVal = val;
        activityCalledVal = 0;
    }

    private FishSeedStockingNavigation activityFishSeedStockingNavigation;

    public GetMastersJSONData(FishSeedStockingNavigation activity, String val) {
        this.activityFishSeedStockingNavigation = activity;
        activityVal = val;
        activityCalledVal = 1;
    }

    private FishSeedStockingPreviousDatesNav activityFishSeedStockingPreviousDatesNav;

    public GetMastersJSONData(FishSeedStockingPreviousDatesNav activity, String val) {
        this.activityFishSeedStockingPreviousDatesNav = activity;
        activityVal = val;
        activityCalledVal = 11;
    }


    private PrawnSeedStockingNavigation activityPrawnSeedStockingNavigation;

    public GetMastersJSONData(PrawnSeedStockingNavigation activity, String val) {
        this.activityPrawnSeedStockingNavigation = activity;
        activityVal = val;
        activityCalledVal = 2;
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
    public static String getServiceResult(String strURL, String strSoapAction, SoapObject request)
            throws XmlPullParserException, IOException {
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;

        // Set output SOAP object
        envelope.setOutputSoapObject(request);

        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(strURL);
        androidHttpTransport.debug = true;

        SoapObject response;

        String response1;

        // StringBuffer result = null;

        System.setProperty("http.keepAlive", "false");

        try {
            // Invoke web service
            androidHttpTransport.call(strSoapAction, envelope);

            // Get the response
            response1 = envelope.getResponse().toString();
//            response = (SoapObject) envelope.getResponse();
//            StringBuffer result;
//            result = new StringBuffer(response.toString());

            if (Utility.showLogs == 0)
                Log.d(TAG, response1);

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
        return response1;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (activityVal.equalsIgnoreCase("DISTRICT_MST") ||
                activityVal.equalsIgnoreCase("MANDAL_MST")
                || activityVal.equalsIgnoreCase("VILLAGE_MST")
                || activityVal.equalsIgnoreCase("SeasonalityMst")
                || activityVal.equalsIgnoreCase("CultureTypeMst")
                || activityVal.equalsIgnoreCase("SpeciesMst")
                || activityVal.equalsIgnoreCase("WaterbodyMst")
                || activityVal.equalsIgnoreCase("FinYearMST")
                || activityVal.equalsIgnoreCase("WaterBodyData")
                || activityVal.equalsIgnoreCase("WaterBodySSData")
                || activityVal.equalsIgnoreCase("WaterbodySupplierlist")
                || activityVal.equalsIgnoreCase("SupplierIndentingData")
                || activityVal.equalsIgnoreCase("SeedingHarvesting")
        ) {
            // Check network available.

            if (activityCalledVal == 1) {
                if (!Utility.isNetworkAvailable(activityFishSeedStockingNavigation))
                    activityFishSeedStockingNavigation.onError("Network error");

            /*if (activityCalledVal == 0) {
                if (!Utility.isNetworkAvailable(activityHarvestingNavigation))
                    activityHarvestingNavigation.onError("Network error");
            } else if (activityCalledVal == 1) {
                if (!Utility.isNetworkAvailable(activityFishSeedStockingNavigation))
                    activityFishSeedStockingNavigation.onError("Network error");
            } else if (activityCalledVal == 11) {
                if (!Utility.isNetworkAvailable(activityFishSeedStockingPreviousDatesNav))
                    activityFishSeedStockingPreviousDatesNav.onError("Network error");
            } else if (activityCalledVal == 2) {
                if (!Utility.isNetworkAvailable(activityPrawnSeedStockingNavigation))
                    activityPrawnSeedStockingNavigation.onError("Network error");
            }*/
            }

        }
    }

    @Override
    protected String doInBackground(String... strings) {

        SoapObject request = null;
        String returnSoapObj = null;

        if (activityVal.equalsIgnoreCase("DISTRICT_MST"))
            request = new SoapObject(Utility.WSDL_TARGET_NAMESPACE, Utility.OPERATION_NAME_DistrictMaster);
        else if (activityVal.equalsIgnoreCase("MANDAL_MST"))
            request = new SoapObject(Utility.WSDL_TARGET_NAMESPACE, Utility.OPERATION_NAME_MandalMaster);
        else if (activityVal.equalsIgnoreCase("VILLAGE_MST"))
            request = new SoapObject(Utility.WSDL_TARGET_NAMESPACE, Utility.OPERATION_NAME_VillageMaster);
        else if (activityVal.equalsIgnoreCase("SeasonalityMst"))
            request = new SoapObject(Utility.WSDL_TARGET_NAMESPACE, Utility.OPERATION_NAME_SeasonalityMst);
        else if (activityVal.equalsIgnoreCase("CultureTypeMst"))
            request = new SoapObject(Utility.WSDL_TARGET_NAMESPACE, Utility.OPERATION_NAME_CultureTypeMst);
        else if (activityVal.equalsIgnoreCase("SpeciesMst"))
            request = new SoapObject(Utility.WSDL_TARGET_NAMESPACE, Utility.OPERATION_NAME_SpeciesMst);
        else if (activityVal.equalsIgnoreCase("WaterbodyMst"))
            request = new SoapObject(Utility.WSDL_TARGET_NAMESPACE, Utility.OPERATION_NAME_WaterbodyMst);
        else if (activityVal.equalsIgnoreCase("FinYearMST"))
            request = new SoapObject(Utility.WSDL_TARGET_NAMESPACE, Utility.OPERATION_NAME_FinYearMST);
        else if (activityVal.equalsIgnoreCase("WaterBodyData"))
            request = new SoapObject(Utility.WSDL_TARGET_NAMESPACE, Utility.OPERATION_NAME_WaterbodyMst);
        else if (activityVal.equalsIgnoreCase("WaterBodySSData"))
            request = new SoapObject(Utility.WSDL_TARGET_NAMESPACE, Utility.OPERATION_NAME_GetIndentedWaterbody);
        else if (activityVal.equalsIgnoreCase("SeedingHarvesting"))
            request = new SoapObject(Utility.WSDL_TARGET_NAMESPACE, Utility.OPERATION_NAME_getSeedingHarvesting);
        else if (activityVal.equalsIgnoreCase("WaterbodySupplierlist"))
            request = new SoapObject(Utility.WSDL_TARGET_NAMESPACE, Utility.OPERATION_NAME_GetWaterbodySupplierlist);
        else if (activityVal.equalsIgnoreCase("SupplierIndentingData"))
            request = new SoapObject(Utility.WSDL_TARGET_NAMESPACE, Utility.OPERATION_NAME_GetWaterbodySupplierIndenting);

        if (activityVal.equalsIgnoreCase("WaterBodyData")) {
            request.addProperty("Distcode", strings[0]);
            request.addProperty("Mandalcode", strings[1]);
            request.addProperty("villagecode", strings[2]);
            request.addProperty("yearcode", strings[3]);
            request.addProperty("Seasonality", strings[4]);
            request.addProperty("WS_UserName", "" + Utility.WS_UserName);
            request.addProperty("WS_Password", "" + Utility.WS_Password);

           /* if (activityCalledVal == 0)
                request.addProperty("MobileVersion", "" + Utility.getVersionNameCode(activityHarvestingNavigation));
            else*/ if (activityCalledVal == 1)
                request.addProperty("MobileVersion", "" + Utility.getVersionNameCode(activityFishSeedStockingNavigation));
            else if (activityCalledVal == 11)
                request.addProperty("MobileVersion", "3.0.2");
            /*else if (activityCalledVal == 2)
                request.addProperty("MobileVersion", "" + Utility.getVersionNameCode(activityPrawnSeedStockingNavigation));*/
        } else if (activityVal.equalsIgnoreCase("WaterBodySSData")) {
            request.addProperty("Distcode", strings[0]);
            request.addProperty("Mandalcd", strings[1]);
            request.addProperty("villcode", strings[2]);
            request.addProperty("Seasonnalitycd", strings[3]);
            request.addProperty("year", strings[4]);
            request.addProperty("CultureType", strings[5]);
            request.addProperty("WS_UserName", "" + Utility.WS_UserName);
            request.addProperty("WS_Password", "" + Utility.WS_Password);

            /*if (activityCalledVal == 0)
                request.addProperty("MobileVersion", "" + Utility.getVersionNameCode(activityHarvestingNavigation));
            else*/ if (activityCalledVal == 1)
                request.addProperty("MobileVersion", "" + Utility.getVersionNameCode(activityFishSeedStockingNavigation));
            else if (activityCalledVal == 11)
                request.addProperty("MobileVersion", "3.0.2");
           /* else if (activityCalledVal == 2)
                request.addProperty("MobileVersion", "" + Utility.getVersionNameCode(activityPrawnSeedStockingNavigation));*/
        } else if (activityVal.equalsIgnoreCase("WaterbodySupplierlist")) {
            request.addProperty("waterCode", strings[0]);
            request.addProperty("year", strings[1]);
            request.addProperty("CultureType", strings[2]);
            request.addProperty("WS_UserName", "" + Utility.WS_UserName);
            request.addProperty("WS_Password", "" + Utility.WS_Password);

           /* if (activityCalledVal == 0)
                request.addProperty("MobileVersion", "" + Utility.getVersionNameCode(activityHarvestingNavigation));
            else*/ if (activityCalledVal == 1)
                request.addProperty("MobileVersion", "" + Utility.getVersionNameCode(activityFishSeedStockingNavigation));
            else if (activityCalledVal == 11)
                request.addProperty("MobileVersion", "3.0.2");
            /*else if (activityCalledVal == 2)
                request.addProperty("MobileVersion", "" + Utility.getVersionNameCode(activityPrawnSeedStockingNavigation));*/
        } else if (activityVal.equalsIgnoreCase("SupplierIndentingData")) {
            request.addProperty("waterCode", strings[0]);
            request.addProperty("suppliercd", strings[1]);
            request.addProperty("year", strings[2]);
            request.addProperty("CultureType", strings[3]);
            request.addProperty("WS_UserName", "" + Utility.WS_UserName);
            request.addProperty("WS_Password", "" + Utility.WS_Password);

           /* if (activityCalledVal == 0)
                request.addProperty("MobileVersion", "" + Utility.getVersionNameCode(activityHarvestingNavigation));
            else*/ if (activityCalledVal == 1)
                request.addProperty("MobileVersion", "" + Utility.getVersionNameCode(activityFishSeedStockingNavigation));
            else if (activityCalledVal == 11)
                request.addProperty("MobileVersion", "3.0.2");
           /* else if (activityCalledVal == 2)
                request.addProperty("MobileVersion", "" + Utility.getVersionNameCode(activityPrawnSeedStockingNavigation));*/
        } else if (activityVal.equalsIgnoreCase("SeedingHarvesting")) {
            request.addProperty("waterCode", strings[0]);
            request.addProperty("year", strings[1]);
            request.addProperty("CultureType", strings[2]);
            request.addProperty("WS_UserName", "" + Utility.WS_UserName);
            request.addProperty("WS_Password", "" + Utility.WS_Password);
           /* if (activityCalledVal == 0)
                request.addProperty("MobileVersion", "" + Utility.getVersionNameCode(activityHarvestingNavigation));
            else */if (activityCalledVal == 1)
                request.addProperty("MobileVersion", "" + Utility.getVersionNameCode(activityFishSeedStockingNavigation));
            else if (activityCalledVal == 11)
                request.addProperty("MobileVersion", "3.0.2");
          /*  else if (activityCalledVal == 2)
                request.addProperty("MobileVersion", "" + Utility.getVersionNameCode(activityPrawnSeedStockingNavigation));*/
        } else {
            request.addProperty("WS_UserName", "" + Utility.WS_UserName);
            request.addProperty("WS_Password", "" + Utility.WS_Password);
           /* if (activityCalledVal == 0)
                request.addProperty("MobileVersion", "" + Utility.getVersionNameCode(activityHarvestingNavigation));
            else */if (activityCalledVal == 1)
                request.addProperty("MobileVersion", "" + Utility.getVersionNameCode(activityFishSeedStockingNavigation));
            else if (activityCalledVal == 11)
                request.addProperty("MobileVersion", "3.0.2");
           /* else if (activityCalledVal == 2)
                request.addProperty("MobileVersion", "" + Utility.getVersionNameCode(activityPrawnSeedStockingNavigation));*/
        }

        String Url = Utility.Url_Harvesting;

        if (Utility.showLogs == 0)
            Log.d(TAG, "request: " + request);

        if (activityVal.equalsIgnoreCase("DISTRICT_MST"))
            returnSoapObj = getXMLResult(request, Url,
                    "" + Utility.SOAP_ACTION_DistrictMaster);

        else if (activityVal.equalsIgnoreCase("MANDAL_MST"))
            returnSoapObj = getXMLResult(request, Url,
                    "" + Utility.SOAP_ACTION_MandalMaster);

        else if (activityVal.equalsIgnoreCase("VILLAGE_MST"))
            returnSoapObj = getXMLResult(request, Url,
                    "" + Utility.SOAP_ACTION_VillageMaster);

        else if (activityVal.equalsIgnoreCase("SeasonalityMst"))
            returnSoapObj = getXMLResult(request, Url,
                    "" + Utility.SOAP_ACTION_SeasonalityMst);
        else if (activityVal.equalsIgnoreCase("CultureTypeMst"))
            returnSoapObj = getXMLResult(request, Url,
                    "" + Utility.SOAP_ACTION_CultureTypeMst);

        else if (activityVal.equalsIgnoreCase("SpeciesMst"))
            returnSoapObj = getXMLResult(request, Url,
                    "" + Utility.SOAP_ACTION_SpeciesMst);

        else if (activityVal.equalsIgnoreCase("WaterbodyMst"))
            returnSoapObj = getXMLResult(request, Url,
                    "" + Utility.SOAP_ACTION_WaterbodyMst);

        else if (activityVal.equalsIgnoreCase("FinYearMST"))
            returnSoapObj = getXMLResult(request, Url,
                    "" + Utility.SOAP_ACTION_FinYearMST);

        else if (activityVal.equalsIgnoreCase("WaterBodyData"))
            returnSoapObj = getXMLResult(request, Url,
                    "" + Utility.SOAP_ACTION_WaterbodyMst);

        else if (activityVal.equalsIgnoreCase("WaterBodySSData"))
            returnSoapObj = getXMLResult(request, Url,
                    "" + Utility.SOAP_ACTION_GetIndentedWaterbody);

        else if (activityVal.equalsIgnoreCase("SeedingHarvesting"))
            returnSoapObj = getXMLResult(request, Url,
                    "" + Utility.SOAP_ACTION_getSeedingHarvesting);

        else if (activityVal.equalsIgnoreCase("WaterbodySupplierlist"))
            returnSoapObj = getXMLResult(request, Url,
                    "" + Utility.SOAP_ACTION_GetWaterbodySupplierlist);

        else if (activityVal.equalsIgnoreCase("SupplierIndentingData"))
            returnSoapObj = getXMLResult(request, Url,
                    "" + Utility.SOAP_ACTION_GetWaterbodySupplierIndenting);

        return returnSoapObj;

    }

    /**
     * get service result and catching exceptions
     *
     * @param request
     * @param url
     * @param soapAction
     * @return
     */
    protected String getXMLResult(SoapObject request, String url, String soapAction) {
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
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        if (response == null) {
            if (activityVal.equalsIgnoreCase("DISTRICT_MST")
                    || activityVal.equalsIgnoreCase("MANDAL_MST")
                    || activityVal.equalsIgnoreCase("VILLAGE_MST")
                    || activityVal.equalsIgnoreCase("SeasonalityMst")
                    || activityVal.equalsIgnoreCase("CultureTypeMst")
                    || activityVal.equalsIgnoreCase("SpeciesMst")
                    || activityVal.equalsIgnoreCase("WaterbodyMst")
                    || activityVal.equalsIgnoreCase("FinYearMST")
                    || activityVal.equalsIgnoreCase("WaterBodyData")
                    || activityVal.equalsIgnoreCase("WaterBodySSData")
                    || activityVal.equalsIgnoreCase("WaterbodySupplierlist")
                    || activityVal.equalsIgnoreCase("SupplierIndentingData")
                    || activityVal.equalsIgnoreCase("SeedingHarvesting")
            )

               /* if (activityCalledVal == 0)
                    activityHarvestingNavigation.onError("Getting Data Error");
                else*/ if (activityCalledVal == 1)
                    activityFishSeedStockingNavigation.onError("Getting Data Error");
                /*else if (activityCalledVal == 11)
                    activityFishSeedStockingPreviousDatesNav.onError("Getting Data Error");
                else if (activityCalledVal == 2)
                    activityPrawnSeedStockingNavigation.onError("Getting Data Error");*/


        } else if (activityVal.equalsIgnoreCase("DISTRICT_MST")
                || activityVal.equalsIgnoreCase("MANDAL_MST")
                || activityVal.equalsIgnoreCase("VILLAGE_MST")
                || activityVal.equalsIgnoreCase("SeasonalityMst")
                || activityVal.equalsIgnoreCase("CultureTypeMst")
                || activityVal.equalsIgnoreCase("SpeciesMst")
                || activityVal.equalsIgnoreCase("WaterbodyMst")
                || activityVal.equalsIgnoreCase("FinYearMST")
        ) {

           /* if (activityCalledVal == 0)
                activityHarvestingNavigation.parsingGetMastersDataJSONResp(response);
            else*/ if (activityCalledVal == 1)
                activityFishSeedStockingNavigation.parsingGetMastersDataJSONResp(response);
            /*else if (activityCalledVal == 11)
                activityFishSeedStockingPreviousDatesNav.parsingGetMastersDataJSONResp(response);
            else if (activityCalledVal == 2)
                activityPrawnSeedStockingNavigation.parsingGetMastersDataJSONResp(response);*/

        } else if (activityVal.equalsIgnoreCase("SeedingHarvesting")) {
            /*if (activityCalledVal == 0)
                activityHarvestingNavigation.parsingGetSeedHarvestingStockDataJSONResp(response);
            else*/ if (activityCalledVal == 1)
                activityFishSeedStockingNavigation.parsingGetSeedHarvestingStockDataJSONResp(response);
           /* else if (activityCalledVal == 11)
                activityFishSeedStockingPreviousDatesNav.parsingGetSeedHarvestingStockDataJSONResp(response);
            else if (activityCalledVal == 2)
                activityPrawnSeedStockingNavigation.parsingGetSeedHarvestingStockDataJSONResp(response);*/
        } else if (activityVal.equalsIgnoreCase("WaterBodyData")) {
            /*if (activityCalledVal == 0)
                activityHarvestingNavigation.parsingGetWaterBodyDataJSONResp(response);
            else*/ if (activityCalledVal == 1)
                activityFishSeedStockingNavigation.parsingGetWaterBodyDataJSONResp(response);
            /*else if (activityCalledVal == 11)
                activityFishSeedStockingPreviousDatesNav.parsingGetWaterBodyDataJSONResp(response);
            else if (activityCalledVal == 2)
                activityPrawnSeedStockingNavigation.parsingGetWaterBodyDataJSONResp(response);*/
        } else if (activityVal.equalsIgnoreCase("WaterBodySSData")) {
            if (activityCalledVal == 1)
                activityFishSeedStockingNavigation.parsingGetWaterBodyDataJSONResp(response);
           /* else if (activityCalledVal == 11)
                activityFishSeedStockingPreviousDatesNav.parsingGetWaterBodyDataJSONResp(response);
            else if (activityCalledVal == 2)
                activityPrawnSeedStockingNavigation.parsingGetWaterBodyDataJSONResp(response);*/
        } else if (activityVal.equalsIgnoreCase("WaterbodySupplierlist")) {
            if (activityCalledVal == 1)
                activityFishSeedStockingNavigation.parsingGetWaterbodySupplierlistDataJSONResp(response);
            /*else if (activityCalledVal == 11)
                activityFishSeedStockingPreviousDatesNav.parsingGetWaterbodySupplierlistDataJSONResp(response);
            else if (activityCalledVal == 2)
                activityPrawnSeedStockingNavigation.parsingGetWaterbodySupplierlistDataJSONResp(response);*/
        } else if (activityVal.equalsIgnoreCase("SupplierIndentingData")) {
            if (activityCalledVal == 1)
                activityFishSeedStockingNavigation.parsingGetWaterbodySupplierIndentingDataJSONResp(response);
            /*else if (activityCalledVal == 11)
                activityFishSeedStockingPreviousDatesNav.parsingGetWaterbodySupplierIndentingDataJSONResp(response);
            else if (activityCalledVal == 2)
                activityPrawnSeedStockingNavigation.parsingGetWaterbodySupplierIndentingDataJSONResp(response);*/
        } else {
            if (activityVal.equalsIgnoreCase("DISTRICT_MST")
                    || activityVal.equalsIgnoreCase("MANDAL_MST")
                    || activityVal.equalsIgnoreCase("VILLAGE_MST")
                    || activityVal.equalsIgnoreCase("SeasonalityMst")
                    || activityVal.equalsIgnoreCase("CultureTypeMst")
                    || activityVal.equalsIgnoreCase("SpeciesMst")
                    || activityVal.equalsIgnoreCase("WaterbodyMst")
                    || activityVal.equalsIgnoreCase("FinYearMST")
                    || activityVal.equalsIgnoreCase("WaterBodyData")
                    || activityVal.equalsIgnoreCase("WaterbodySupplierlist")
                    || activityVal.equalsIgnoreCase("SupplierIndentingData")
                    || activityVal.equalsIgnoreCase("SeedingHarvesting")
            )
               /* if (activityCalledVal == 0)
                    activityHarvestingNavigation.onError("Getting Data Error");
                else*/ if (activityCalledVal == 1)
                    activityFishSeedStockingNavigation.onError("Getting Data Error");
                /*else if (activityCalledVal == 11)
                    activityFishSeedStockingPreviousDatesNav.onError("Getting Data Error");
                else if (activityCalledVal == 2)
                    activityPrawnSeedStockingNavigation.onError("Getting Data Error");*/

        }
    }
}

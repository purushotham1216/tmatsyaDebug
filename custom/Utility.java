package com.org.nic.ts.custom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/*import com.org.nic.ts.tmatsya.R;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;*/

import com.org.nic.ts.R;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    // 0-show, 1-not show
    public static final int showLogs
            = 0;
//            = 1;

    public static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    //chnged on 29May2021 http to https
    public static final String Url = "https://tmatsya.telangana.gov.in/IFDS_Service.asmx?wsdl";
    public static final String Url_Harvesting = "https://tmatsya.telangana.gov.in/HarvestingDetails.asmx?wsdl";

    public static final String OPERATION_NAME_ValidateLogin = "ValidateLogin";
    public static final String SOAP_ACTION_ValidateLogin = WSDL_TARGET_NAMESPACE + OPERATION_NAME_ValidateLogin;

    public static final String OPERATION_NAME_ApplicantTypeMST = "ApplicantTypeMST";
    public static final String SOAP_ACTION_ApplicantTypeMST = WSDL_TARGET_NAMESPACE + OPERATION_NAME_ApplicantTypeMST;

    public static final String OPERATION_NAME_ComponentMST = "ComponentMST";
    public static final String SOAP_ACTION_ComponentMST = WSDL_TARGET_NAMESPACE + OPERATION_NAME_ComponentMST;

    public static final String OPERATION_NAME_SubComponentMST = "SubComponentMST";
    public static final String SOAP_ACTION_SubComponentMST = WSDL_TARGET_NAMESPACE + OPERATION_NAME_SubComponentMST;

    public static final String OPERATION_NAME_FarmerDetailsMST = "FarmerDetailsMST";
    public static final String SOAP_ACTION_FarmerDetailsMST = WSDL_TARGET_NAMESPACE + OPERATION_NAME_FarmerDetailsMST;

    public static final String OPERATION_NAME_Applicant_FarmerPhoto_Insert_new = "Applicant_FarmerPhoto_Insert_new";
    public static final String SOAP_ACTION_Applicant_FarmerPhoto_Insert_new = WSDL_TARGET_NAMESPACE + OPERATION_NAME_Applicant_FarmerPhoto_Insert_new;

    public static final String OPERATION_NAME_Applicant_FarmerPhoto_Insert = "Applicant_FarmerPhoto_Insert";
    public static final String SOAP_ACTION_Applicant_FarmerPhoto_Insert = WSDL_TARGET_NAMESPACE + OPERATION_NAME_Applicant_FarmerPhoto_Insert;

    //harvesting
    public static final String OPERATION_NAME_FinYearMST = "FinYearMST";
    public static final String SOAP_ACTION_FinYearMST = WSDL_TARGET_NAMESPACE + OPERATION_NAME_FinYearMST;

    public static final String OPERATION_NAME_DistrictMaster = "DistrictMaster";
    public static final String SOAP_ACTION_DistrictMaster = WSDL_TARGET_NAMESPACE + OPERATION_NAME_DistrictMaster;

    public static final String OPERATION_NAME_MandalMaster = "MandalMaster";
    public static final String SOAP_ACTION_MandalMaster = WSDL_TARGET_NAMESPACE + OPERATION_NAME_MandalMaster;

    public static final String OPERATION_NAME_VillageMaster = "VillageMaster";
    public static final String SOAP_ACTION_VillageMaster = WSDL_TARGET_NAMESPACE + OPERATION_NAME_VillageMaster;

    public static final String OPERATION_NAME_SeasonalityMst = "SeasonalityMst";
    public static final String SOAP_ACTION_SeasonalityMst = WSDL_TARGET_NAMESPACE + OPERATION_NAME_SeasonalityMst;

    public static final String OPERATION_NAME_SpeciesMst = "SpeciesMst";
    public static final String SOAP_ACTION_SpeciesMst = WSDL_TARGET_NAMESPACE + OPERATION_NAME_SpeciesMst;

    public static final String OPERATION_NAME_WaterbodyMst = "WaterbodyMst";
    public static final String SOAP_ACTION_WaterbodyMst = WSDL_TARGET_NAMESPACE + OPERATION_NAME_WaterbodyMst;

    public static final String OPERATION_NAME_CultureTypeMst = "CultureTypeMst";
    public static final String SOAP_ACTION_CultureTypeMst = WSDL_TARGET_NAMESPACE + OPERATION_NAME_CultureTypeMst;

    public static final String OPERATION_NAME_Fish_Harvesting_IUDR = "Fish_Harvesting_IUDR";
    public static final String SOAP_ACTION_Fish_Harvesting_IUDR = WSDL_TARGET_NAMESPACE + OPERATION_NAME_Fish_Harvesting_IUDR;

    public static final String OPERATION_NAME_getSeedingHarvesting = "getSeedingHarvesting";
    public static final String SOAP_ACTION_getSeedingHarvesting = WSDL_TARGET_NAMESPACE + OPERATION_NAME_getSeedingHarvesting;

    //6Sept2021
    public static final String OPERATION_NAME_GetWaterbodySupplierlist = "GetWaterbodySupplierlist";
    public static final String SOAP_ACTION_GetWaterbodySupplierlist = WSDL_TARGET_NAMESPACE + OPERATION_NAME_GetWaterbodySupplierlist;

    public static final String OPERATION_NAME_GetIndentedWaterbody = "GetIndentedWaterbody";
    public static final String SOAP_ACTION_GetIndentedWaterbody = WSDL_TARGET_NAMESPACE + OPERATION_NAME_GetIndentedWaterbody;

    public static final String OPERATION_NAME_GetWaterbodySupplierIndenting = "GetWaterbodySupplierIndenting";
    public static final String SOAP_ACTION_GetWaterbodySupplierIndenting = WSDL_TARGET_NAMESPACE + OPERATION_NAME_GetWaterbodySupplierIndenting;

    public static final String OPERATION_NAME_InsertSeedstocking = "InsertSeedstocking";
    public static final String SOAP_ACTION_InsertSeedstocking = WSDL_TARGET_NAMESPACE + OPERATION_NAME_InsertSeedstocking;

    public static final String WS_UserName = "IFDS";
    public static final String WS_Password = "IFDS$Admin@123";
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    public static int photoSelection = 0;//0-> applicant, 1-> proof of declaration
    public static Bitmap applntBitmap = null, prfOfDeclBitmap = null;
    public static String imageFilePath;//Made it static as need to override the original image with compressed image.public static String unique_id = "";
    public static int versionDBVal = 1;
    public static String unique_id = "";
//    public static ArrayList<Node> listNote;
    public static int isNetworkAvailableHandlerMsg = 1;
    static String versionNameOrCodeStr = "";

    public static final SharedPreferences getSharedPreferences(Context context) {

        return context.getApplicationContext().getSharedPreferences("prefMI", 0);

    }

    /**
     * Checking network (before start)
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();

    }



    public static void showAlertDialog(final Context context, String title,
                                       String message, Boolean status) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Specify the alert dialog title
//        String titleText = "Say Hello!";

        // Initialize a new foreground color span instance
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.BLUE);

        // Initialize a new spannable string builder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(title);

        // Apply the text color span
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                title.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        // Set the alert dialog title using spannable string builder
        builder.setTitle(ssBuilder);

//        builder.setTitle(title);

        if (status == true) {
            builder.setIcon(R.drawable.success_48x48);
        } else {
            builder.setIcon(R.drawable.fail_48x48);
        }

        builder.setMessage(message).setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
//                        ((Activity) context).startActivity(new Intentent(context,Menulist.class));
//                        ((Activity) context).finish();


                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        final AlertDialog alert = builder.create();

        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {


                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);

            }
        });

        alert.show();


    }

    public static void showAlertDialogToCallPlayStore(final Context context, String title,
                                                      String message, Boolean status) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Specify the alert dialog title
//        String titleText = "Say Hello!";

        // Initialize a new foreground color span instance
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.BLUE);

        // Initialize a new spannable string builder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(title);

        // Apply the text color span
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                title.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        // Set the alert dialog title using spannable string builder
        builder.setTitle(ssBuilder);

//        builder.setTitle(title);

        if (status == true) {
            builder.setIcon(R.drawable.success_48x48);
        } else {
            builder.setIcon(R.drawable.fail_48x48);
        }

        builder.setMessage(message).setCancelable(false)
                .setPositiveButton("Goto Google Play Store", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                        final String my_package_name = context.getPackageName(); // getPackageName() from Context or Activity object
                        String url = "";

                        try {
                            //Check whether Google Play store is installed or not:
                            context.getPackageManager().getPackageInfo("com.android.vending", 0);

                            url = "market://details?id=" + my_package_name;
                        } catch (final Exception e) {
                            url = "https://play.google.com/store/apps/details?id=" + my_package_name;
                        }


//Open the app page in Google Play store:
                        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        ((Activity) context).startActivity(intent);
//                        ((Activity) context).startActivity(new Intentent(context,Menulist.class));
                        ((Activity) context).finish();


                    }
                });

        /*.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        })*/
        final AlertDialog alert = builder.create();

        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);

            }
        });

        alert.show();


    }

    public static void callHideKeyBoard(Context context) {
        // Check if no view has focus:
        View view = ((Activity) context).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) ((Activity) context)
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void callSignOutAlert(final Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Confirmation");
        builder.setIcon(R.drawable.confirm_48x48);

        builder.setMessage("Are you sure you want to quit Application?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                ((Activity) context).finish();

                                dialog.cancel();

                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();

        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);

            }
        });

        alert.show();

    }

    public static void assignArrayAdpListToSpin(Context context, List array,
                                                Spinner spin) {

//        ArrayAdapter<String> arrayAdp = new ArrayAdapter<String>(context,
//                android.R.layout.simple_spinner_dropdown_item, array);
        ArrayAdapter<String> arrayAdp = new ArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line, array);
        arrayAdp.setDropDownViewResource(R.layout.spinner_textview);
        spin.setAdapter(arrayAdp);

    }



    public static String getVersionNameCode(Context _context) {

        try {
            PackageInfo pinfo = _context.getPackageManager().getPackageInfo(
                    _context.getPackageName(), 0);

            if (showLogs == 0) {
                Log.d("pinfoCode", "" + pinfo.versionCode);
                Log.d("pinfoName", pinfo.versionName);
            }


            // versionCodeStr=String.valueOf(pinfo.versionCode);
            versionNameOrCodeStr =
//					"1.9";
                    pinfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return versionNameOrCodeStr;
    }

    public static String GetIMEINO(Context context) {
        String IMEI = null;
        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                IMEI = UUID.randomUUID().toString();
            else
                IMEI = manager.getDeviceId();


            if (showLogs == 0)
                Log.d("Utility", "UUID: " + IMEI);


        } catch (SecurityException se) {

// se.printStackTrace();

        }

       /* if (showLogs == 0) {
            IMEI = manager.getDeviceId();
        } else
            IMEI = manager.getDeviceId();*/

        return IMEI;
    }



    public static void isNetworkAvailable(final Handler handler, final int timeout) {
        // ask fo message '0' (not connected) or '1' (connected) on 'handler'
        // the answer must be send before before within the 'timeout' (in milliseconds)

        new Thread() {
            private boolean responded = false;

            @Override
            public void run() {
                // set 'responded' to TRUE if is able to connect with google mobile (responds fast)
                new Thread() {
                    @Override
                    public void run() {
                        HttpGet requestForTest = new HttpGet("http://m.google.com");
                        try {
                            new DefaultHttpClient().execute(requestForTest); // can last...
                            responded = true;
                        } catch (Exception e) {
                        }
                    }
                }.start();

                try {
                    int waited = 0;
                    while (!responded && (waited < timeout)) {
                        sleep(100);
//                        sleep(100);
                        if (!responded) {
                            waited += 100;
//                            waited += 100;
                        }
                    }
                } catch (InterruptedException e) {
                } // do nothing
                finally {
                    if (!responded) {// code if not connected

                        isNetworkAvailableHandlerMsg = 0;

                        handler.sendEmptyMessage(0);
                    } else {// code if connected
                        isNetworkAvailableHandlerMsg = 1;
                        handler.sendEmptyMessage(1);
                    }
                }
            }
        }.start();
    }

    public static void callAnotherActivityAlert(final Context context, final Class className) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Confirmation");
        builder.setIcon(R.drawable.confirm_48x48);

        builder.setMessage("This form is best viewed in Tablet, Are you sure you want to proceed?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                (context).startActivity(new Intent(context, className));
                                ((Activity) context).finish();

                                dialog.cancel();

                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();

        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);

            }
        });

        alert.show();

    }

    public static boolean validateAadhaarNumber(EditText aaddhar_no_first_edt, EditText aaddhar_no_second_edt, EditText aaddhar_no_third_edt) {

        String lic_farmer_aaddhar_no_first_edtStr = aaddhar_no_first_edt.getText().toString().trim();
        if (TextUtils.isEmpty(lic_farmer_aaddhar_no_first_edtStr)) {
            aaddhar_no_first_edt.setError("Required Aadhaar Number.");
            aaddhar_no_first_edt.requestFocus();
            return true;
        }

        String lic_farmer_aaddhar_no_second_edtStr = aaddhar_no_second_edt.getText().toString().trim();
        if (TextUtils.isEmpty(lic_farmer_aaddhar_no_second_edtStr)) {
            aaddhar_no_second_edt.setError("Required Aadhaar Number.");
            aaddhar_no_second_edt.requestFocus();
            return true;
        }

        String lic_farmer_aaddhar_no_third_edtStr = aaddhar_no_third_edt.getText().toString().trim();
        if (TextUtils.isEmpty(lic_farmer_aaddhar_no_third_edtStr)) {
            aaddhar_no_third_edt.setError("Required Aadhaar Number.");
            aaddhar_no_third_edt.requestFocus();
            return true;
        }

        String lic_farmer_aaddhar_no_edtStr = lic_farmer_aaddhar_no_first_edtStr +
                lic_farmer_aaddhar_no_second_edtStr +
                lic_farmer_aaddhar_no_third_edtStr;


       /* if (TextUtils.isEmpty(lic_farmer_aaddhar_no_edtStr)) {
            lic_farmer_aaddhar_no_first_edt.setError("Required Aadhaar Number(eg. 12 digits)");
            lic_farmer_aaddhar_no_first_edt.requestFocus();
            return;
        }*/

        if (lic_farmer_aaddhar_no_edtStr.length() < 12) {
            aaddhar_no_first_edt.setError("Required Valid Aadhaar Number(eg. 12 digits)");
            aaddhar_no_first_edt.requestFocus();
            aaddhar_no_first_edt.setText("");
            aaddhar_no_second_edt.setText("");
            aaddhar_no_third_edt.setText("");
            return true;
        }

        boolean status = Verhoeff.validateVerhoeff(lic_farmer_aaddhar_no_edtStr);

        if (status == false) {
            aaddhar_no_first_edt.setError("Invalid Aadhaar Number.");

            aaddhar_no_first_edt.requestFocus();
           /* lic_farmer_aaddhar_no_first_edt.setText("");
            lic_farmer_aaddhar_no_second_edt.setText("");
            lic_farmer_aaddhar_no_third_edt.setText("");*/
            return true;
        }

        return false;
    }

    /*
    How can i validating the EditText with Regex by allowing particular characters . My condition is :
    Password Rule:
    One capital letter
    One number
    One symbol (@,$,%,&,#,) whatever normal symbols that are acceptable.

    ^                 # start-of-string
    (?=.*[0-9])       # a digit must occur at least once
    (?=.*[a-z])       # a lower case letter must occur at least once
    (?=.*[A-Z])       # an upper case letter must occur at least once
    (?=.*[@#$%^&+=])  # a special character must occur at least once you can replace with your special characters
    (?=\\S+$)          # no whitespace allowed in the entire string
    .{4,}             # anything, at least six places though
    $                 # end-of-string
    */
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    /*
    * Description
    IFSC Code normally contains 11 characters.
    In that
    first 4 characters are alphabets,
    5th character is 0 and
    next 6 characters are numerics
    * */
    public static boolean isValidIFSC_Code(final String ifsc_code) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "[A-Z|a-z]{4}[0][a-zA-Z0-9]{6}$";//Length of 11 (4+1+6)
        // final String PASSWORD_PATTERN = "[A-Z|a-z]{4}[0][\\d]{6}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(ifsc_code);

        return matcher.matches();

    }

    public static boolean isAllCharactersSame(String str) {
        for (int i = 0; i < str.length() - 1; i++) {
            if (str.charAt(i) != str.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean compareDates(String psDate1, String psDate2) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = dateFormat.parse(psDate1);
        Date date2 = dateFormat.parse(psDate2);
        if (date2.after(date1)) {
            return true;
        } else {
            return false;
        }
    }

}
